package org.sparta.springwebutils.collect;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SpartaCollectorsTest {
    @Test
    void testLinkedHashMapSuccess() {
        final List<String> input = List.of("a", "b", "c");
        final LinkedHashMap<String, String> output = input.stream().collect(SpartaCollectors.toLinkedHashMap(
                Function.identity(),
                t -> "Value " + t
        ));

        assertThat(output).containsExactly(
                Map.entry("a", "Value a"),
                Map.entry("b", "Value b"),
                Map.entry("c", "Value c")
        );
    }

    @Test
    void testLinkedHashMapDuplicate() {
        final List<String> input = List.of("a", "b", "b");

        final var ex = assertThrows(IllegalStateException.class, () -> input.stream().collect(SpartaCollectors.toLinkedHashMap(
                Function.identity(),
                t -> "Value " + t
        )));
        assertEquals("Duplicate key b (attempted merging values Value b and Value b)", ex.getMessage());
    }

    @Test
    void testToMapUniqueSuccess() {
        final List<String> input = List.of("b", "a", "c");
        final TreeMap<String, String> output = input.stream().collect(SpartaCollectors.toMapUnique(
                Function.identity(),
                t -> "Value " + t,
                TreeMap::new
        ));

        assertThat(output).containsExactly(
                Map.entry("a", "Value a"),
                Map.entry("b", "Value b"),
                Map.entry("c", "Value c")
        );
    }

    @Test
    void testToMapUniqueDuplicate() {
        final List<String> input = List.of("a", "b", "b");

        final var ex = assertThrows(IllegalStateException.class,
                () -> input.stream().collect(SpartaCollectors.toMapUnique(
                        Function.identity(),
                        t -> "Value " + t,
                        TreeMap::new
                )));
        assertEquals("Duplicate key b (attempted merging values Value b and Value b)", ex.getMessage());
    }
}
