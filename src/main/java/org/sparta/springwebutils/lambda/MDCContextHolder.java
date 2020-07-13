/**
 * Sparta Software Co.
 * 2020
 */
package org.sparta.springwebutils.lambda;

import java.util.Map;

import org.slf4j.MDC;

// @formatter:off
 /** 
 * 
 * Utility class to be used in parallelStream to keep context.
 * 
 * @author Daniel Conde Diehl - Sparta Technology
 * 
 * History: 
 *    Jul 10, 2020 - Daniel Conde Diehl
 *  
 */
// @formatter:on
public class MDCContextHolder {

    /**
     * Variable that is going to hold the MDC context.
     */
    private Map<String, String> context;
    
    /**
     * Private constructor that copies the current MDC context.
     */
    private MDCContextHolder () {
        context = MDC.getCopyOfContextMap();
    }
    
    /**
     * Static initializer to create the holder instance.
     * 
     * @return MDCContextHolder new instance
     */
    public static MDCContextHolder init() {
        return new MDCContextHolder();
    }
    
    /**
     * Reloads the previous context on hold to the MDC.
     * <b>This should be used as first line into a parallelStream.</b>
     */
    public void reloadContext() {
        MDC.clear();
        if (context != null) {
            MDC.setContextMap(context);
        }
    }
}
