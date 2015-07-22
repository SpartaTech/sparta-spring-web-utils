package org.sparta.springwebutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/** 
 * @author Carlos Eduardo Endler Genz – Sparta Java Team 
 * 
 * History: 
 *    Apr 9, 2014 - Carlos Eduardo Endler Genz
 *    Jul 21, 2015 - Daniel Conde Diehl - Adding getManifestAttribute7
 *  
 */ 
public class ManifestUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(ManifestUtils.class);
	
	private static final String MANIFEST = "META-INF/MANIFEST.MF";
	
	@Autowired
	private ServletContext servletContext;
	
	/**
	 * Retrieves a value for a specific manifest attribute.
	 * Or null if not found
	 * 
	 * @param attributeName to locate in the manifest
	 * @return String value for or null case not found
	 */
	public String getManifestAttribute(String attributeName) {
	    if (attributeName != null) {
    	    Map<Object,Object> mf = getManifestAttributes();
            for (Object att : mf.keySet()) {
                if (attributeName.equals(att.toString())) {
                    return mf.get(att).toString(); 
                }
            }
	    }
	    
	    //In case not found return null;
	    return null;
	}
	
	/**
	 * Reads the manifest entries for this application. Returns empty if anything fails.
	 *
	 * @return
	 */
	public Map<Object, Object> getManifestAttributes() {
		Map<Object, Object> manifestAttributes = new HashMap<Object, Object>();
		
		FileInputStream fis = null;
		try {
			if (servletContext != null) {
				final String appServerHome = servletContext.getRealPath("/");
				final File manifestFile = new File(appServerHome, MANIFEST);
				
				fis = new FileInputStream(manifestFile);
				Manifest mf = new Manifest(fis);
				manifestAttributes = mf.getMainAttributes();
			} else {
			    manifestAttributes = getClassPathManifestAttributes();
			}
		} catch (Exception e) {
			LOGGER.warn("Unable to read the manifest file from the servlet context. Trying to load from classpath.");
			LOGGER.debug("Unable to read the manifest file", e);
			
			manifestAttributes = getClassPathManifestAttributes();
		} finally {
			IOUtils.closeQuietly(fis);
		}
		return manifestAttributes;
	}
	
	/**
	 * Reads the manifest entries for this application (classpath). Returns empty if anything fails.
	 *
	 * @return
	 */
	private Map<Object, Object> getClassPathManifestAttributes() {
		Map<Object, Object> manifestAttributes = new HashMap<Object, Object>();
		try {
		  Manifest manifest = new Manifest(getClass().getClassLoader().getResourceAsStream(MANIFEST));
		  manifestAttributes = manifest.getMainAttributes();
		} catch (IOException e) {
			LOGGER.warn("Unable to read the manifest from the classpath");
			LOGGER.debug("Unable to read the manifest from the classpath", e);
		}
		return manifestAttributes;
	}
}
