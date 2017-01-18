/**
 * Sparta Software Co.
 * 2016
 */
package org.sparta.springwebutils.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Container for multiple annotations.
 * 
 * @author Daniel Conde Diehl - Sparta Technology
 *
 * History:
 *  Jun 1, 2016 - Daniel Conde Diehl
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryWhereClauses {
	
	/**
	 * List of annotations
	 * 
	 * @return 
	 */
	QueryWhereClause[] value();
}
