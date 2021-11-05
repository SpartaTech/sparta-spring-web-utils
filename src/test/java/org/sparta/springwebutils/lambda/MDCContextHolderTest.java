package org.sparta.springwebutils.lambda;

import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MDCContextHolderTest {
    @Test
    public void testMDCContextHolderOnNewThread() throws InterruptedException {
        final String key = "test-key";
        final String value = "test-value";
        final AtomicReference<String> valueInThread = new AtomicReference<>();

        MDC.put(key, value);
        final MDCContextHolder mdcContextHolder = MDCContextHolder.init();
        assertEquals(value, MDC.get(key));

        final Thread t = new Thread(() -> {
            mdcContextHolder.reloadContext();
            valueInThread.set(MDC.get(key));
        });
        t.start();

        assertEquals(value, MDC.get(key));

        //wait thread to finish
        t.join();
        assertEquals(value, valueInThread.get());
    }
}
