package org.sparta.springwebutils.queryloader;

/**
 * Interface for Query Loader implementations.
 *
 * @author Daniel Conde Diehl - Sparta Technology
 * <p>
 * History:
 * - Mar 22, 2017 - Daniel Conde Diehl
 */
public interface QueryLoader {

	/**
	 * Loads the query by name.
	 *
	 * @param queryName query name to be loaded
	 * @return query retrieved
	 */
	String load(String queryName);

	/**
	 * Loads multiple queries. Reads from the query name(source) and break into multiple queries by separator.
	 *
	 * @param queryName query name to be loaded
	 * @param separator separator to split the queries read from the source
	 * @return array with all retrieved queries
	 */
	String[] loadMultiple(String queryName, char separator);
}