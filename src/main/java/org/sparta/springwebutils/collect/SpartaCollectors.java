package org.sparta.springwebutils.collect;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Helpers collectors.
 */
public final class SpartaCollectors {

    private SpartaCollectors() {
    }

    /**
     * Collects to a UniqueMap.
     * If it finds duplicate values throws an {@code IllegalStateException}
     *
     * @param keyMapper   mapper for the map key
     * @param valueMapper mapper for the map value
     * @param mapCreator  supplier to create the desired map
     * @param <T>         Input type to apply mappers
     * @param <K>         mapped key type
     * @param <V>         mapped value type
     * @param <M>         Map type
     * @return Collector to be used in the collect.
     */
    public static <T, K, V, M extends Map<K, V>>
    Collector<T, ?, M> toMapUnique(Function<? super T, ? extends K> keyMapper,
                                   Function<? super T, ? extends V> valueMapper,
                                   Supplier<M> mapCreator) {
        return Collector.of(mapCreator,
                (map, element) -> {
                    K k = keyMapper.apply(element);
                    V v = Objects.requireNonNull(valueMapper.apply(element));
                    V u = map.putIfAbsent(k, v);
                    if (u != null) throw duplicateKeyException(k, u, v);
                },
                (m1, m2) -> {
                    for (Map.Entry<K, V> e : m2.entrySet()) {
                        K k = e.getKey();
                        V v = Objects.requireNonNull(e.getValue());
                        V u = m1.putIfAbsent(k, v);
                        if (u != null) throw duplicateKeyException(k, u, v);
                    }
                    return m1;
                });
    }

    /**
     * Collects to a LinkedHashMap.
     * If it finds duplicate values throws an {@code IllegalStateException}
     *
     * @param keyMapper   mapper for the map key
     * @param valueMapper mapper for the map value
     * @param <T>         Input type to apply mappers
     * @param <K>         mapped key type
     * @param <V>         mapped value type
     * @return Collector to be used in the collect.
     */
    public static <T, K, V>
    Collector<T, ?, LinkedHashMap<K, V>> toLinkedHashMap(Function<? super T, ? extends K> keyMapper,
                                               Function<? super T, ? extends V> valueMapper) {
        return SpartaCollectors.toMapUnique(keyMapper, valueMapper, LinkedHashMap::new);
    }

    /**
     * Generates a duplicate key exception.
     *
     * @param k Key object
     * @param u left value
     * @param v right value
     * @return IllegalStateException
     */
    private static IllegalStateException duplicateKeyException(
            Object k, Object u, Object v) {
        return new IllegalStateException(String.format(
                "Duplicate key %s (attempted merging values %s and %s)",
                k, u, v));
    }
}
