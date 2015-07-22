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
	
	String name() default "";
	
	String[] nameBlacklist() default {};
	
	Class<?>[] typeBlacklist() default {};
}
