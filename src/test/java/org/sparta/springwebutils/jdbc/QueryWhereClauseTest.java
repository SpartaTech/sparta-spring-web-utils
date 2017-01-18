/**
 * Sparta Software Co.
 * 2016
 */
package org.sparta.springwebutils.jdbc;

import java.sql.Types;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;



/**
 * @author Daniel Conde Diehl - Sparta Technology
 *
 * History:
 *  May 31, 2016 - Daniel Conde Diehl
 */

public class QueryWhereClauseTest {

	@Test
	public void testAllParameters() throws Exception {
		TestWhereBuilder testObj = new TestWhereBuilder();
		testObj.setFirstName("Daniel");
		testObj.setLastName("Diehl");
		TestRangeObj<Long> range = new TestRangeObj<>();
		range.setMin(21L);
		range.setMax(60L);
		testObj.setAge(range);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		final String actualQuery = QueryWhereClauseBuilder.buildWhereClause(testObj, params);
		Assert.assertEquals("p.first_name = :firstName AND p.last_name = :lastName AND p.age > :age.min AND p.age < :age.max", actualQuery);
		Assert.assertEquals(testObj.getFirstName(), params.getValue("firstName"));
		Assert.assertEquals(testObj.getAge().getMin().toString(), params.getValue("age.min"));
		Assert.assertEquals(Types.DOUBLE, params.getSqlType("age.min"));
		Assert.assertEquals(testObj.getAge().getMax().toString(), params.getValue("age.max"));
		Assert.assertEquals(Types.DOUBLE, params.getSqlType("age.max"));
		
	}
	
	@Test
	public void testAllNull () throws Exception {
		TestWhereBuilder testObj = new TestWhereBuilder();
		final String actualQuery = QueryWhereClauseBuilder.buildWhereClause(testObj, null);
		Assert.assertEquals("", actualQuery);
	}
	
	@Test
	public void testFirstNameNoParameters () throws Exception {
		TestWhereBuilder testObj = new TestWhereBuilder();
		testObj.setFirstName("Daniel");
		final String actualQuery = QueryWhereClauseBuilder.buildWhereClause(testObj, null);
		Assert.assertEquals("p.first_name = :firstName", actualQuery);
	}
	
	@Test
	public void testOnlyFirstName () throws Exception {
		TestWhereBuilder testObj = new TestWhereBuilder();
		testObj.setFirstName("Daniel");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		final String actualQuery = QueryWhereClauseBuilder.buildWhereClause(testObj, params);
		Assert.assertEquals("p.first_name = :firstName", actualQuery);
		Assert.assertEquals(testObj.getFirstName(), params.getValue("firstName"));
	}
	
	@Test
	public void testOnlyOneAge () throws Exception {
		TestWhereBuilder testObj = new TestWhereBuilder();
		testObj.setFirstName("Daniel");
		TestRangeObj<Long> range = new TestRangeObj<>();
		range.setMin(21L);
		testObj.setAge(range);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		final String actualQuery = QueryWhereClauseBuilder.buildWhereClause(testObj, params);
		Assert.assertEquals("p.first_name = :firstName AND p.age > :age.min", actualQuery);
		Assert.assertEquals(testObj.getFirstName(), params.getValue("firstName"));
		Assert.assertEquals(testObj.getAge().getMin().toString(), params.getValue("age.min"));
	}
}
