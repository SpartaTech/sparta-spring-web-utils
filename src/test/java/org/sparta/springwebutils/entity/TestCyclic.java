package org.sparta.springwebutils.entity;

/** 
 * 
 * Cyclic reference object for testing
 * 
 * @author danieldiehl - Sparta Java Team 
 * 
 * History: 
 *    Mar 14, 2014 - danieldiehl
 *  
 */
public class TestCyclic {
	private TestCyclic innerCyclic;

	/**
	 * Getter method for innerCyclic
	 * @return the innerCyclic
	 */
	public TestCyclic getInnerCyclic() {
		return innerCyclic;
	}

	/**
	 * Setter method for innerCyclic
	 * @param innerCyclic the innerCyclic to set
	 */
	public void setInnerCyclic(TestCyclic innerCyclic) {
		this.innerCyclic = innerCyclic;
	}
	
	
}
