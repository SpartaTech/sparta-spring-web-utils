/**
 * Sparta Software Co.
 * 2017
 */
package org.sparta.springwebutils.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
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
@Component
public class PropertiesLoaderBuilderFactory {
    @Autowired
    private AbstractEnvironment env;
    
    /**
     * Generates a new Properties Builder.
     * 
     * @return new generated PropretiesBuilder
     */
    public PropertiesLoaderBuilder getPropertiesBuilder() {
        return new PropertiesLoaderBuilder(env);
    }
}
