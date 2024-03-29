package org.sparta.springwebutils.queryloader.impl;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.sparta.springwebutils.queryloader.QueryLoader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Loads Queries from files. 
 * 
 * It's necessary to inform scriptsFolder at the creation time 
 * 
 * HOW TO USE: Declare the bean into applicationContext.xml (or beans.xml)
 * 	
 * {@literal 
 *      <bean id="loader" class="net.sf.sparta.springwebutils.queryloader.impl.FileQueryLoader">
 *          <constructor-arg name="scriptsFolder" value="<<SCRIPTS_FOLDER_NAME>>" />
 *      </bean>
 * }
 * 
 * OR create a bean on your @Configuration class like:
 * <code>@Bean public QueryLoader queryLoader() { return new FileQueryLoader("/sql/"); }</code>
 *
 * @author Daniel Conde Diehl - Sparta Technology
 * <p>
 * History:
 * - Mar 22, 2017 - Daniel Conde Diehl
 * - Oct 13, 2021 - Daniel Conde Diehl - upgrading dependency libraries
 *
 */
public class FileQueryLoader implements QueryLoader, InitializingBean {

	private static final ConcurrentMap<String, String> QUERY_CACHE = new ConcurrentHashMap<>(50);

	private final String scriptsFolder;

	/**
	 * Constructor, receives the path for the folder containing all the queries
	 * 
	 * @param scriptsFolder folder with the scripts
	 */
	public FileQueryLoader(String scriptsFolder) {
		this.scriptsFolder = StringUtils.appendIfMissing(scriptsFolder, "/");
	}
	
	@Override
	public String load(String queryName) {
	    if (!QUERY_CACHE.containsKey(queryName)) {
	        QUERY_CACHE.put(queryName, loadFromFromFile(queryName));
	    }
	    
		return QUERY_CACHE.get(queryName); 
		
	}
	
    /*
     * (non-Javadoc)
     * @see net.sf.sparta.springwebutils.queryloader.QueryLoader#loadMultiple(java.lang.String, char)
     */
    @Override
    public String[] loadMultiple(String queryName, char separator) {
        final String fullSql = load(queryName);
        
        return StringUtils.split(fullSql, separator);
    }
	
	/**
	 * Loads the query from the informed path and adds to cache
	 * 
	 * @param queryName File name for the query to be loaded 
	 * @return requested Query
	 * @throws IllegalStateException In case query was not found
	 */
	private String loadFromFromFile(String queryName) throws IllegalStateException {
		try (final InputStream is = getClass().getResourceAsStream(scriptsFolder + queryName + ".sql")) {
		    String sql = StringUtils.join(IOUtils.readLines(is, StandardCharsets.UTF_8), System.lineSeparator());
		    
		    // Look for tokens
		    final String[] tokens = StringUtils.substringsBetween(sql, "${", "}");
		    if (tokens != null && tokens.length > 0) {
    		    final Map<String, String> values = Arrays.stream(tokens).collect(Collectors.toMap(
    	            Function.identity(),
    	            this::load,
    	            (o, n) -> o
    	        ));
		    
		        sql = StringSubstitutor.replace(sql, values);
		    }
		    
			return sql;
		} catch (Exception e) {
			throw new IllegalStateException("Could not load query " + scriptsFolder + queryName, e);
		}
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(scriptsFolder, "scriptsFolder cannot be null");
	}
}
