/**
 * Sparta Software Co.
 * 2017
 */
package org.sparta.springwebutils.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Factory class for Properties Builder. 
 * Creates a new instance of Properties Builder using information from Spring Context.
 * 
 * @author Daniel Conde Diehl - Sparta Technology
 *
 * History:
 *  Mar 22, 2017 - Daniel Conde Diehl
 */

public class PropertiesLoaderBuilderFactory implements EnvironmentAware{
    private AbstractEnvironment env;
   
    /**
     * Generates a new Properties Builder.
     * 
     * @return new generated PropretiesBuilder
     */
    public PropertiesLoaderBuilder getPropertiesBuilder() {
        return new PropertiesLoaderBuilder(env);
    }
    
    /* (non-Javadoc)
     * @see org.springframework.context.EnvironmentAware#setEnvironment(org.springframework.core.env.Environment)
     */
    @Override
    public void setEnvironment(Environment env) {
    	if (env instanceof AbstractEnvironment) {
    		this.env = (AbstractEnvironment)env;
    	} else {
    		throw new RuntimeException("env is not an AbstractEnvironment. " + env.getClass().getCanonicalName());
    	}
    	
    }
}
