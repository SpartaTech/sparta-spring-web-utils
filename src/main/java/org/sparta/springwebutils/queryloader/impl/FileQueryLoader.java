package org.sparta.springwebutils.queryloader.impl;

import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.sparta.springwebutils.queryloader.QueryLoader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

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
 * @author Daniel Conde Diehl
 *
 */
public class FileQueryLoader implements QueryLoader, InitializingBean {

	private static final ConcurrentMap<String, String> QUERY_CACHE = new ConcurrentHashMap<>(50);

	private String scriptsFolder;
	
	/**
	 * Constructor, receives the path for the folder containing all the queries
	 * 
	 * @param scriptsFolder folder with the scripts
	 */
	public FileQueryLoader(String scriptsFolder) {
		this.scriptsFolder = scriptsFolder;
	}
	
	@Override
	public String load(String queryName) {
	    if (!QUERY_CACHE.containsKey(queryName)) {
	        QUERY_CACHE.put(queryName, loadfromFromFile(queryName));
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
	private String loadfromFromFile(String queryName) throws IllegalStateException {
		try (final InputStream is = getClass().getResourceAsStream(scriptsFolder + queryName + ".sql");) {
			return StringUtils.join(IOUtils.readLines(is), ' ');
		} catch (Exception e) {
			throw new IllegalStateException("Could not load query " + scriptsFolder + queryName, e);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(scriptsFolder, "scriptsFolder cannot be null");
	}

}
