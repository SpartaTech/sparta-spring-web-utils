/**
 * Sparta Software Co.
 * 2016
 */
package org.sparta.springwebutils.jdbc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparta.springwebutils.annotation.QueryWhereClause;
import org.sparta.springwebutils.annotation.QueryWhereClauses;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 * @author Daniel Conde Diehl - Sparta Technology
 *
 * History:
 *  May 31, 2016 - Daniel Conde Diehl
 */

public class QueryWhereClauseBuilder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryWhereClauseBuilder.class);
	
	/**
	 * Regular expression to find parameters
	 */
	final static Pattern PARAM_PATTERN = Pattern.compile("\\{(.*?)\\}", Pattern.MULTILINE|Pattern.DOTALL); 
	
	
	/**
	 * Builds a where clause based upon the object values
	 * 
	 * @param object object to read annotation and values
	 * @param params map of parameters to set values for execution
	 * @return String with the where clause
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static String buildWhereClause(Object object, MapSqlParameterSource params) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		LOGGER.debug("Building query");
		
		final StringBuilder query = new StringBuilder();
		boolean first = true;
		for (Field field : object.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(QueryWhereClause.class) 
					|| field.isAnnotationPresent(QueryWhereClauses.class)) {
				final String fieldName = field.getName();
				LOGGER.trace("New annotated field found: {}", fieldName);
				
				QueryWhereClause[] annotations = field.getAnnotationsByType(QueryWhereClause.class);
				for (QueryWhereClause annotation : annotations) {
					String whereValue = annotation.value();
					int[] types = annotation.fieldTypes();
					int index = 0;
					LOGGER.trace("Unprocessed whereClause: {}", whereValue);
					
					Matcher matcher = PARAM_PATTERN.matcher(whereValue);
					boolean hasValue = false;
					while (matcher.find()) {
						String originalParam = matcher.group(1);
						LOGGER.debug("New parameter found in the query: {}", originalParam);
						
						String convertedParam = originalParam.replaceAll("this", fieldName);
						Object value = null;
						try {
							value = BeanUtils.getNestedProperty(object, convertedParam);
						} catch (NestedNullException e) {
							LOGGER.info("Bean not accessible= {}", e.getMessage());
						}
						
						if (value == null) { 
							LOGGER.debug("Param {} was null, ignoring in the query", convertedParam);
						} else {
							hasValue = true;
							whereValue = StringUtils.replace(whereValue, "{"+originalParam+ "}", ":" + convertedParam);
							
							if (params != null) {
								if (index <= types.length-1) {
									params.addValue(convertedParam, value, types[index]);
								} else {
									params.addValue(convertedParam, value);
								}
							}
						}
						index ++;
					}
					if (hasValue) {
						if (!first) {
							query.append(" AND ");
						} else { 
							first = false;
						}
						query.append(whereValue);
					}
				}
			}
		}
		LOGGER.debug("built query={}", query);
		return query.toString();
	}
}
