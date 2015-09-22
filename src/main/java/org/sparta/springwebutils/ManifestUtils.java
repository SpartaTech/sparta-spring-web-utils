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
	 * First tries from Exploded WAR, second Packaged WAR, third from classpath. 
	 * if not found returns an empty map
	 *
	 * @return Map manifest entries if found otherwise empty map
	 */
	public Map<Object, Object> getManifestAttributes() {
		Map<Object, Object> manifestAttributes = null;
		
		manifestAttributes = getExplodedWarManifestAttributes();
		if (manifestAttributes == null) {
		    manifestAttributes = getPackagedWarManifestAttributes();
		}
		if (manifestAttributes == null) {
		    manifestAttributes = getClassPathManifestAttributes();
		}
		
		if (manifestAttributes == null) {
		    manifestAttributes = new HashMap<>();
		}
		return manifestAttributes;
	}
	
	
	/**
	 * reads the manifest from a exploded WAR
	 * 
	 * @return Map<Object, Object> manifest entries if found otherwise empty map
	 */
	private Map<Object, Object> getExplodedWarManifestAttributes() {
	    Map<Object, Object> manifestAttributes = null;
	    
	    FileInputStream fis = null;
	    try {
            if (servletContext != null) {
                final String appServerHome = servletContext.getRealPath("");
                final File manifestFile = new File(appServerHome, MANIFEST);
                
                fis = new FileInputStream(manifestFile);
                Manifest mf = new Manifest(fis);
                manifestAttributes = mf.getMainAttributes();
            }
        } catch (Exception e) {
            LOGGER.warn("Unable to read the manifest file from the servlet context.");
            LOGGER.debug("Unable to read the manifest file", e);            
        } finally {
            IOUtils.closeQuietly(fis);
        }
        return manifestAttributes;
	}
	
	/**
	 * Reads the manifest entries for this application (classpath). Returns empty if anything fails.
	 *
	 * @return Map manifest entries if found otherwise empty map
	 */
	private Map<Object, Object> getClassPathManifestAttributes() {
		Map<Object, Object> manifestAttributes = null;
		try {
		  Manifest manifest = new Manifest(getClass().getClassLoader().getResourceAsStream(MANIFEST));
		  manifestAttributes = manifest.getMainAttributes();
		} catch (IOException e) {
			LOGGER.warn("Unable to read the manifest from the classpath");
			LOGGER.debug("Unable to read the manifest from the classpath", e);
		}
		return manifestAttributes;
	}
	
    /**
     * Retrieve the Manifest from a packaged war
     * 
     * @return Map manifest entries if found otherwise empty map
     */
    private Map<Object, Object> getPackagedWarManifestAttributes() {
        Map<Object, Object> manifestAttributes = null;
        try {
          Manifest manifest = new Manifest(servletContext.getResourceAsStream(MANIFEST));
          manifestAttributes = manifest.getMainAttributes();
        } catch (Exception e) {
            LOGGER.warn("Unable to read the manifest from the packaged war");
            LOGGER.debug("Unable to read the manifest from the packaged", e);
        }
        return manifestAttributes;
    }
}
