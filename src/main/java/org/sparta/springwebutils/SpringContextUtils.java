package org.sparta.springwebutils;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;

/** 
 * Utility class for handling with Spring context loading
 * 
 * @author Daniel Conde Diehl – Sparta Java Team 
 * 
 * History: 
 *    May 21, 2014 - Daniel Conde Diehl - Creation
 *  
 */ 
public class SpringContextUtils {

	/**
	 * Loads a context from a XML and inject all objects in the Map
	 * 
	 * @param xmlPath Path for the xml applicationContext
	 * @param extraBeans Extra beans for being injected
	 * @return ApplicationContext generated
	 */
	public static ApplicationContext contextMergedBeans(String xmlPath, Map<String, ?> extraBeans) {
	    final DefaultListableBeanFactory parentBeanFactory = buildListableBeanFactory(extraBeans);
		
		//loads the xml and add definitions in the context
		GenericApplicationContext parentContext = new GenericApplicationContext(parentBeanFactory);
		XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(parentContext);
		xmlReader.loadBeanDefinitions(xmlPath);
		
		//refreshed the context to create class and make autowires
		parentContext.refresh();
		
		//return the created context
		return parentContext;
	}
	
	/**
     * Loads a context from the annotations config and inject all objects in the Map
     * 
     * @param extraBeans Extra beans for being injected
     * @param config Configuration class
     * @return ApplicationContext generated
     */
    public static ApplicationContext contextMergedBeans(Map<String, ?> extraBeans, Class<?> config) {
        final DefaultListableBeanFactory parentBeanFactory = buildListableBeanFactory(extraBeans);
        
        //loads the annotation classes and add definitions in the context
        GenericApplicationContext parentContext = new GenericApplicationContext(parentBeanFactory);
        AnnotatedBeanDefinitionReader annotationReader = new AnnotatedBeanDefinitionReader(parentContext);
        annotationReader.registerBean(config);
        
        //refreshed the context to create class and make autowires
        parentContext.refresh();
        
        //return the created context
        return parentContext;
    }

    /**
     * Loads a context from the annotations config and inject all objects in the Map. 
     * Loads all the properties to be used as Value annotation.
     * 
     * @param extraBeans Extra beans for being injected
     * @param config Configuration class
     * @param properties Properties to be loaded onto the context
     * @return ApplicationContext generated
     */
    public static ApplicationContext contextMergedBeans(Map<String, ?> extraBeans, Class<?> config, Properties properties) {
        final DefaultListableBeanFactory parentBeanFactory = buildListableBeanFactory(extraBeans);
        
        //loads the annotation classes and add definitions in the context
        GenericApplicationContext parentContext = new GenericApplicationContext(parentBeanFactory);
        setProperties(parentContext, properties);
        
        AnnotatedBeanDefinitionReader annotationReader = new AnnotatedBeanDefinitionReader(parentContext);
        annotationReader.registerBean(config);
        
        //refreshed the context to create class and make autowires
        parentContext.refresh();
        
        //return the created context
        return parentContext;
    }

    /**
     * Set properties into the context.
     * 
     * @param newContext new context to add properties
     * @param properties properties to add to the context
     */
    private static void setProperties(GenericApplicationContext newContext, Properties properties) {
        PropertiesPropertySource pps = new PropertiesPropertySource("external-props", properties);
        newContext.getEnvironment().getPropertySources().addFirst(pps);
    }
    
    
    /**
     * Builds a listable bean factory with the given beans.
     * 
     * @param extraBeans
     * @return new Created BeanFactory
     */
    private static DefaultListableBeanFactory buildListableBeanFactory(Map<String, ?> extraBeans) {
        //new empty context
        final DefaultListableBeanFactory parentBeanFactory = new DefaultListableBeanFactory();  
        
        //Injection of the new beans in the context
        for (String key : extraBeans.keySet()) {
            parentBeanFactory.registerSingleton(key, extraBeans.get(key));
        }
        return parentBeanFactory;
    }
}
