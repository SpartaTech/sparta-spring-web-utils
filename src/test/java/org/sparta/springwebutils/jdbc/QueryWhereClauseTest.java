/*
 * Sparta Software Co.
 * 2016
 */
package org.sparta.springwebutils.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.Types;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author Daniel Conde Diehl - Sparta Technology
 * <p>
 * History:
 * - May 31, 2016 - Daniel Conde Diehl
 * - Oct 13, 2021 - Daniel Conde Diehl - Upgrading to Junit Jupiter
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
		assertEquals("p.first_name = :firstName AND p.last_name = :lastName AND p.age > :age.min AND p.age < :age.max", actualQuery);
		assertEquals(testObj.getFirstName(), params.getValue("firstName"));
		assertEquals(testObj.getAge().getMin().toString(), params.getValue("age.min"));
		assertEquals(Types.DOUBLE, params.getSqlType("age.min"));
		assertEquals(testObj.getAge().getMax().toString(), params.getValue("age.max"));
		assertEquals(Types.DOUBLE, params.getSqlType("age.max"));
		
	}
	
	@Test
	public void testAllNull () throws Exception {
		TestWhereBuilder testObj = new TestWhereBuilder();
		final String actualQuery = QueryWhereClauseBuilder.buildWhereClause(testObj, null);
		assertEquals("", actualQuery);
	}
	
	@Test
	public void testFirstNameNoParameters () throws Exception {
		TestWhereBuilder testObj = new TestWhereBuilder();
		testObj.setFirstName("Daniel");
		final String actualQuery = QueryWhereClauseBuilder.buildWhereClause(testObj, null);
		assertEquals("p.first_name = :firstName", actualQuery);
	}
	
	@Test
	public void testOnlyFirstName () throws Exception {
		TestWhereBuilder testObj = new TestWhereBuilder();
		testObj.setFirstName("Daniel");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		final String actualQuery = QueryWhereClauseBuilder.buildWhereClause(testObj, params);
		assertEquals("p.first_name = :firstName", actualQuery);
		assertEquals(testObj.getFirstName(), params.getValue("firstName"));
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
		assertEquals("p.first_name = :firstName AND p.age > :age.min", actualQuery);
		assertEquals(testObj.getFirstName(), params.getValue("firstName"));
		assertEquals(testObj.getAge().getMin().toString(), params.getValue("age.min"));
	}
}
