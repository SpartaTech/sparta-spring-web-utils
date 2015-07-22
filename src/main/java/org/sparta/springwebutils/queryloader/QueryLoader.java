package org.sparta.springwebutils.queryloader;


public interface QueryLoader {

	String load(String queryName);
	
	String[] loadMultiple(String queryName, char separator);

}