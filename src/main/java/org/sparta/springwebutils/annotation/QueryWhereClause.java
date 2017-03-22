/**
 * Sparta Software Co.
 * 2016
 */
package org.sparta.springwebutils.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used for Query Builder.
 * 
 * @author Daniel Conde Diehl - Sparta Technology
 *
 * History:
 *  May 31, 2016 - Daniel Conde Diehl
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Repeatable(QueryWhereClauses.class)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryWhereClause {
	
	/**
	 * @return the where clause
	 */
	String value();
	
	/**
	 * Field types from {@link java.sql.Types}
	 * 
	 * @return all types from the query
	 */
	int[] fieldTypes() default {};
}
