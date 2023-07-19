/**
 * Sparta Software Co.
 * 2020
 */
package org.sparta.springwebutils.lambda;

import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;

// @formatter:off
 /** 
 * 
 * Utility for handling Exceptions.
 * 
 * @author Daniel Conde Diehl - Sparta Technology
 * 
 * History: 
 *    Jul 10, 2020 - Daniel Conde Diehl
 *  
 */
// @formatter:on
public abstract class ExceptionUtility {
    
    /**
     * Retrieves all SQLExceptions in a DataAccessException in case it is a BatchUpdate. 
     * 
     * @param dae DataAcessException with the multiple SQLExceptions
     * @return Set of SQLException all found
     */
    public static Set<SQLException> getAllSqlExceptionsForBatchExecution(DataAccessException dae) {
        Set<SQLException> result = new LinkedHashSet<>();
        
        if (dae.getMostSpecificCause() instanceof BatchUpdateException) {
            BatchUpdateException ex = ((BatchUpdateException)dae.getMostSpecificCause());
            SQLException se = ex.getNextException();
            while (se != null) {
                result.add(se);
                se = se.getNextException();
            }
        }
        
        return result;
    }
    
    /**
     * Executes the method, processing error by the consumer if exception happens.
     * 
     * @param processable Method to be executed
     * @param exceptionConsumer consumer in case exception happens
     */
    public static void trapException(Processable processable, Consumer<Exception> exceptionConsumer) {
        try {
            processable.process();
        } catch (Exception e) {
            exceptionConsumer.accept(e);
        }
    }
    
    /**
     * Executes the method, and log in case exception happens.
     * 
     * @param processable method to be executed
     * @param errorMessage error message to show in the logs followed by stacktrace
     * @param logger logger to append message
     */
    public static void trapAndLogException (Processable processable, String errorMessage, Logger logger) {
        trapException(processable, ex -> logger.error(errorMessage, ex));
    }
    
    /**
     * Process the processable, and in case of exception wrap in a CheckedExceptionWrapper.
     * 
     * @param processable the process to be executed.
     * @throws CheckedExceptionWrapper in Case there was an exception executing the processable
     */
    public static void trapAndWrapInCheckedException (Processable processable) throws CheckedExceptionWrapper {
        trapException(processable, (e) -> {throw new CheckedExceptionWrapper(e);});
    }
    
}
