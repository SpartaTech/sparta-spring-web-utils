/*
 * Sparta Software Co.
 * 2020
 */
package org.sparta.springwebutils.lambda;

import ch.qos.logback.classic.Level;
import com.github.spartatech.testutils.logback.UnitTestAsserterLogback;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;

import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

// @formatter:off

/**
 * Unit tests for ExceptionUtility.
 *
 * @author Daniel Conde Diehl - Sparta Technology
 * <p>
 * History:
 *  - Jul 10, 2020 - Daniel Conde Diehl
 *  - Oct 13, 2021 - Daniel Conde Diehl - Upgrading to Junit Jupiter
 */
// @formatter:on
public class ExceptionUtilityTest extends ExceptionUtility {

    @Test
    public void testGetAllSqlExceptionsForBatchExecutionNotBatchUpdate() {
        final DuplicateKeyException dae = new DuplicateKeyException("Mocked");
        final Set<SQLException> exs = ExceptionUtility.getAllSqlExceptionsForBatchExecution(dae);
        assertTrue(exs.isEmpty());
    }
    
    @Test
    public void testGetAllSqlExceptionsForBatchExecutionWithBatchUpdate() {
        final BatchUpdateException bue = new BatchUpdateException();
        final SQLException sqlEx1 = new SQLException("Ex 1");
        final SQLException sqlEx2 = new SQLException("Ex 2");
        final SQLException sqlEx3 = new SQLException("Ex 3");
        bue.setNextException(sqlEx1);
        sqlEx1.setNextException(sqlEx2);
        sqlEx2.setNextException(sqlEx3);
        
        final DuplicateKeyException dae = new DuplicateKeyException("Mocked", bue);
        
        final Set<SQLException> exs = ExceptionUtility.getAllSqlExceptionsForBatchExecution(dae);
        
        assertEquals(3, exs.size());
        final Iterator<SQLException> iter = exs.iterator();
        assertEquals(sqlEx1, iter.next());
        assertEquals(sqlEx2, iter.next());
        assertEquals(sqlEx3, iter.next());
    }

    @Test
    public void testTrapExceptionNoException() {
        ExceptionUtility.trapException(() -> {}, e -> fail("Exception not expected"));
    }
    
    @Test
    public void testTrapExceptionExceptionHappened () {
        final AtomicBoolean exceptionTrapped = new AtomicBoolean();
        exceptionTrapped.set(false);
        ExceptionUtility.trapException(() -> {throw new Exception("Mock Exception");}, 
                e -> { 
                    if (e.getMessage().equals("Mock Exception")) {
                        exceptionTrapped.set(true);
                    }
                });
        assertTrue(exceptionTrapped.get(), "Expected exception");
    }
    
    @Test
    public void testTrapAndLogExceptionNoException() {
        final Logger logger = LoggerFactory.getLogger("TestLog");
        final UnitTestAsserterLogback spyAppender = new UnitTestAsserterLogback("TestLog");
        
        ExceptionUtility.trapAndLogException(() -> {}, "Test Message", logger);
        
        spyAppender.assertLogExpectations(false);
    }
    
    @Test
    public void testTrapAndLogExceptionWithException() {
        final Logger logger = LoggerFactory.getLogger("TestLog");
        final Exception ex = new Exception("mock");
        final UnitTestAsserterLogback spyAppender = new UnitTestAsserterLogback("TestLog");
        spyAppender.addExpectation(Level.ERROR, "Test Message");
        
        ExceptionUtility.trapAndLogException(() -> { throw ex;}, "Test Message", logger);
        
        spyAppender.assertLogExpectations(false);
    }
    
    @Test
    public void testTrapAndWrapInExceptionNoException() {
        ExceptionUtility.trapAndWrapInCheckedException(() -> System.out.println("No error expected"));
    }
    
    @Test
    public void testTrapAndWrapInExceptionException() {
        assertThrows(CheckedExceptionWrapper.class, () ->
            ExceptionUtility.trapAndWrapInCheckedException(() -> {throw new Exception ("Xabu happened");})
        );
    }
}
