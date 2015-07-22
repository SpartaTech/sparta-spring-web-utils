package org.sparta.springwebutils.entity;

/** 
 * 
 * TODO <Class Description>
 * 
 * @author danieldiehl - Sparta Java Team 
 * 
 * History: 
 *    Mar 10, 2014 - danieldiehl
 *  
 */
public class TestEntity {
	private String testStr;
	private TestEntityInt integers;
	private TestEntityLong longs;
	private int vInt;
	/**
	 * Getter method for testStr
	 * @return the testStr
	 */
	public String getTestStr() {
		return testStr;
	}
	/**
	 * Setter method for testStr
	 * @param testStr the testStr to set
	 */
	public void setTestStr(String testStr) {
		this.testStr = testStr;
	}
	/**
	 * Getter method for integers
	 * @return the integers
	 */
	public TestEntityInt getIntegers() {
		return integers;
	}
	/**
	 * Setter method for integers
	 * @param integers the integers to set
	 */
	public void setIntegers(TestEntityInt integers) {
		this.integers = integers;
	}
	
	/**
	 * Getter method for longs
	 * @return the longs
	 */
	public TestEntityLong getLongs() {
		return longs;
	}
	/**
	 * Setter method for longs
	 * @param longs the longs to set
	 */
	public void setLongs(TestEntityLong longs) {
		this.longs = longs;
	}
	/**
	 * Setter method for vInt
	 * @param vInt the vInt to set
	 */
	public void setvInt(int vInt) {
		this.vInt = vInt;
	}
	/**
	 * Getter method for vInt
	 * @return the vInt
	 */
	public int getvInt() {
		return vInt;
	}
}
