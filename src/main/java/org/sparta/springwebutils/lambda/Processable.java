/**
 * Sparta Software Co.
 * 2020
 */
package org.sparta.springwebutils.lambda;

/** 
 * 
 * Functional interface similar to Runnable, but allows to throw Exception
 * 
 * @author Daniel Conde Diehl - Sparta Technology
 * 
 * History: 
 *    Jul 10, 2020 - Daniel Conde Diehl
 *  
 */
@FunctionalInterface
public interface Processable {

    /**
     * Executes the process and throws Exception if necessary.
     * 
     * @throws Exception The exception that happened
     */
    public abstract void process() throws Exception;
    
}
