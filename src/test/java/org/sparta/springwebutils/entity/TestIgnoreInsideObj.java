package org.sparta.springwebutils.entity;

/** 
 * 
 * TODO <Class Description>
 * 
 * @author danieldiehl - Sparta Java Team 
 * 
 * History: 
 *    Mar 14, 2014 - danieldiehl
 *  
 */
public class TestIgnoreInsideObj {
	private String str;
	private TestIgnoreType ignoreType;
	private Integer ignoreByName;
	/**
	 * Getter method for str
	 * @return the str
	 */
	public String getStr() {
		return str;
	}
	/**
	 * Setter method for str
	 * @param str the str to set
	 */
	public void setStr(String str) {
		this.str = str;
	}
	/**
	 * Getter method for ignoreType
	 * @return the ignoreType
	 */
	public TestIgnoreType getIgnoreType() {
		return ignoreType;
	}
	/**
	 * Setter method for ignoreType
	 * @param ignoreType the ignoreType to set
	 */
	public void setIgnoreType(TestIgnoreType ignoreType) {
		this.ignoreType = ignoreType;
	}
	/**
	 * Getter method for ignoreByName
	 * @return the ignoreByName
	 */
	public Integer getIgnoreByName() {
		return ignoreByName;
	}
	/**
	 * Setter method for ignoreByName
	 * @param ignoreByName the ignoreByName to set
	 */
	public void setIgnoreByName(Integer ignoreByName) {
		this.ignoreByName = ignoreByName;
	}
}
