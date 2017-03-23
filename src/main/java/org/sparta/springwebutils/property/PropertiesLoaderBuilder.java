/**
 * Sparta Software Co.
 * 2017
 */
package org.sparta.springwebutils.property;

import java.util.Properties;

import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

/**
 * Properties Builder class that loads the properties from the Spring context 
 * and creates a Properties object with it.
 * 
 * @author Daniel Conde Diehl - Sparta Technology
 *
 * History:
 *  Mar 22, 2017 - Daniel Conde Diehl
 */

public class PropertiesLoaderBuilder {
    
    private AbstractEnvironment env;
    private Properties props;
    
    /**
     * Default constructor, only to be called by the Factory.
     * 
     * @param env Spring Environment
     */
    PropertiesLoaderBuilder(AbstractEnvironment env) {
        this.env = env;
        this.props = new Properties();
    }
    
    /**
     * Loads a property from Spring Context by the name.
     * @param name of the property to be loaded from Spring Context.
     * @return PropertyLoaderBuilder to continue the builder chain
     */
    public PropertiesLoaderBuilder loadProperty(String name) {
        if (env.containsProperty(name)) {
            props.put(name, env.getProperty(name));
        }
        return this;
    }
    
    /**
     * Adds a new property. Giving both name and value.
     * This methods does not lookup in the Spring Context, it only adds property and value as given.
     * 
     * @param name to be added in the properties
     * @param value to be added in the properties
     * @return PropertyLoaderBuilder to continue the builder chain
     */
    public PropertiesLoaderBuilder addProperty(String name, String value) {
        props.put(name, value);
        return this;
    }

    /**
     * Loads all properties found for a given prefix.
     * 
     * @param prefix prefix to find the properties 
     * @return PropertyLoaderBuilder to continue the builder chain
     */
    public PropertiesLoaderBuilder loadPropertiesByPrefix(String prefix) {
    	return loadPropertiesByPrefix(prefix, false);
    }
    
    /**
     * Loads all properties found for a given prefix.
     * 
     * @param prefix prefix to find the properties 
     * @param removePrefix if true - removes the prefix from the destination prop, false - copy with the same name
     * @return PropertyLoaderBuilder to continue the builder chain
     */
    public PropertiesLoaderBuilder loadPropertiesByPrefix(String prefix, boolean removePrefix) {	
    	for (PropertySource<?> propertySource : env.getPropertySources()) {
            if (propertySource instanceof MapPropertySource) {
                MapPropertySource mapPropSource = (MapPropertySource) propertySource;
                for (String propName : mapPropSource.getPropertyNames()) {
                    if (propName.startsWith(prefix) && !props.containsKey(propName)) {
                    	String adjustedName = propName;
                    	if (removePrefix) {
                    		adjustedName = propName.substring(prefix.length(), propName.length());
                    	}
                        props.put(adjustedName, env.getProperty(propName));
                    }
                }
            }
        }
        return this;
    }
    
    /**
     * Loads all properties found for a given prefix.
     *  
     * @return PropertyLoaderBuilder to continue the builder chain
     */
    public PropertiesLoaderBuilder loadAllPropertiesFromSpring() {
        for (PropertySource<?> propertySource : env.getPropertySources()) {
            if (propertySource instanceof MapPropertySource) {
                MapPropertySource mapPropSource = (MapPropertySource) propertySource;
                for (String propName : mapPropSource.getPropertyNames()) {
                    if (!props.containsKey(propName)) {
                        props.put(propName, env.getProperty(propName));
                    }
                }
            }
        }
        return this;
    }
    
    /**
     * This is a terminal method that will return the generated property.
     * 
     * @return the generated new Property Object.
     */
    public Properties build() {
        return props;
    }
}
