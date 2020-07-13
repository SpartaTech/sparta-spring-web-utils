/**
 * Sparta Software Co.
 * 2020
 */
package org.sparta.springwebutils.lambda;

// @formatter:off
 /** 
 * 
 * Exception to be used inside Streams to allow sending it out the stream execution.
 * 
 * @author Daniel Conde Diehl - Sparta Technology
 * 
 * History: 
 *    Jul 10, 2020 - Daniel Conde Diehl
 *  
 */
// @formatter:on
public class CheckedExceptionWrapper extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Exception originalException;
    
    /**
     * Exception receiving the real-cause of the issue
     * 
     * @param ex Original exception
     */
    public CheckedExceptionWrapper(Exception originalException) {
        this.originalException = originalException;
    }

    /**
     * Returns the original Exception.
     * 
     * @return original Exception
     */
    public Exception unwrap() {
        return originalException;
    }
    
}
