package org.sparta.springwebutils.entity;

/** 
 * @author Carlos Eduardo Endler Genz – Sparta Java Team 
 * 
 * History: 
 *    Mar 4, 2014 - Carlos Eduardo Endler Genz
 *  
 */
public class EntryPointParameter {
	private String name;
	private Class<?> type;
	private boolean required = false;
	private String defaultValue = "";

	/**
	 * Getter method for name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method for name
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method for type
	 * @return the type
	 */
	public Class<?> getType() {
		return type;
	}

	/**
	 * Setter method for type
	 * @param type the type to set
	 */
	public void setType(Class<?> type) {
		this.type = type;
	}

	/**
	 * Getter method for required
	 * @return the required
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * Setter method for required
	 * @param required the required to set
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * Getter method for defaultValue
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Setter method for defaultValue
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
