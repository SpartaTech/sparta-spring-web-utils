/**
 * Sparta Software Co.
 * 2020
 */
package org.sparta.springwebutils.lambda;

import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;

import com.github.spartatech.testutils.logback.UnitTestAsserterLogback;

import ch.qos.logback.classic.Level;

// @formatter:off
 /** 
 * 
 * Unit tests for ExceptionUtility.
 * 
 * @author Daniel Conde Diehl - Sparta Technology
 * 
 * History: 
 *    Jul 10, 2020 - Daniel Conde Diehl
 *  
 */
// @formatter:on
public class ExceptionUtilityTest extends ExceptionUtility {

    @Test
    public void testGetAllSqlExceptionsForBatchExecutionNotBatchUpdate() throws Exception {
        DuplicateKeyException dae = new DuplicateKeyException("Mocked");
        
        Set<SQLException> exs = ExceptionUtility.getAllSqlExceptionsForBatchExecution(dae);
        
        Assert.assertTrue(exs.isEmpty());
    }
    
    @Test
    public void testGetAllSqlExceptionsForBatchExecutionWithBatchUpdate() throws Exception {
        final BatchUpdateException bue = new BatchUpdateException();
        final SQLException sqlEx1 = new SQLException("Ex 1");
        final SQLException sqlEx2 = new SQLException("Ex 2");
        final SQLException sqlEx3 = new SQLException("Ex 3");
        bue.setNextException(sqlEx1);
        sqlEx1.setNextException(sqlEx2);
        sqlEx2.setNextException(sqlEx3);
        
        DuplicateKeyException dae = new DuplicateKeyException("Mocked", bue);
        
        Set<SQLException> exs = ExceptionUtility.getAllSqlExceptionsForBatchExecution(dae);
        
        Assert.assertEquals(3, exs.size());
        Iterator<SQLException> iter = exs.iterator();
        Assert.assertEquals(sqlEx1, iter.next());
        Assert.assertEquals(sqlEx2, iter.next());
        Assert.assertEquals(sqlEx3, iter.next());
    }
    
    @Test
    public void testTrapExceptionNoException() {
        ExceptionUtility.trapException(() -> {}, e -> { Assert.fail("Exception not expected");});
    }
    
    @Test
    public void testTrapExceptionExceptionHappened () {
        final AtomicBoolean exceptionTrapped = new AtomicBoolean();
        exceptionTrapped.set(false);
        ExceptionUtility.trapException(() -> {throw new Exception("Mock Exception");}, 
                e -> { 
                    if (e.getMessage().equals("Mock Exception")) {
                        exceptionTrapped.set(true);
                    };
                });
        Assert.assertTrue("Expected exception", exceptionTrapped.get());
    }
    
    @Test
    public void testTrapAndLogExeptionNoException() {
        final Logger logger = LoggerFactory.getLogger("TestLog");
        
        UnitTestAsserterLogback spyAppender = new UnitTestAsserterLogback("TestLog");
        
        ExceptionUtility.trapAndLogException(() -> {}, "Test Message", logger);
        
        spyAppender.assertLogExpectations(false);
    }
    
    @Test
    public void testTrapAndLogExeptionWithException() {
        final Logger logger = LoggerFactory.getLogger("TestLog");
        final Exception ex = new Exception("mock");
        UnitTestAsserterLogback spyAppender = new UnitTestAsserterLogback("TestLog");
        spyAppender.addExpectation(Level.ERROR, "Test Message");
        
        ExceptionUtility.trapAndLogException(() -> { throw ex;}, "Test Message", logger);
        
        spyAppender.assertLogExpectations(false);
    }
    
    @Test
    public void testTrapAndWrapInExceptionNoException () throws Exception {
        ExceptionUtility.trapAndWrapInCheckedException(() -> {System.out.println("No error expected");});
    }
    
    @Test(expected=CheckedExceptionWrapper.class)
    public void testTrapAndWrapInExceptionException () throws Exception {
        ExceptionUtility.trapAndWrapInCheckedException(() -> {throw new Exception ("Xabu happened");});
    }
}
