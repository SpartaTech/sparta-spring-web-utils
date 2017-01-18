/**
 * Sparta Software Co.
 * 2016
 */
package org.sparta.springwebutils.jdbc;

import java.sql.Types;

import org.sparta.springwebutils.annotation.QueryWhereClause;
import org.sparta.springwebutils.annotation.QueryWhereClauses;

/**
 * @author Daniel Conde Diehl - Sparta Technology
 *
 *         History: May 31, 2016 - Daniel Conde Diehl
 */

public class TestWhereBuilder {
	@QueryWhereClause("p.first_name = {this}")
	private String firstName;
	@QueryWhereClause("p.last_name = {this}")
	private String lastName;
	
	@QueryWhereClauses({
			@QueryWhereClause(value = "p.age > {this.min}", fieldTypes=Types.DOUBLE),
			@QueryWhereClause(value = "p.age < {this.max}", fieldTypes=Types.DOUBLE)
	})
	private TestRangeObj<Long> age;

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the age
	 */
	public TestRangeObj<Long> getAge() {
		return age;
	}
	
	/**
	 * @param age the age to set
	 */
	public void setAge(TestRangeObj<Long> age) {
		this.age = age;
	}
}
