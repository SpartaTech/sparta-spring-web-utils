package org.sparta.springwebutils.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @author Carlos Eduardo Endler Genz
 * 
 * History: 
 *    Feb 28, 2014 - cgenz
 *  
 */ 
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExternalEntryPoint {

	/**
	 * Name for external end-point
	 *
	 * @return name
	 */
	String name() default "";

	/**
	 * Blacklist params by name
	 *
	 * @return list of params blacklisted by name
	 */
	String[] nameBlacklist() default {};

	/**
	 * Blacklist params by type
	 *
	 * @return list of types for params that should be blacklisted
	 */
	Class<?>[] typeBlacklist() default {};
}
