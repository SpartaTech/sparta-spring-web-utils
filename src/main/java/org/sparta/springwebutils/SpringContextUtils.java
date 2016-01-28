package org.sparta.springwebutils;

import java.util.Map;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

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
     * @param packagesToScan Packages to scan the configuration
     * @return ApplicationContext generated
     */
    public static ApplicationContext contextMergedBeans(Map<String, ?> extraBeans, String... packagesToScan) {
        final DefaultListableBeanFactory parentBeanFactory = buildListableBeanFactory(extraBeans);
        
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(parentBeanFactory);
        context.scan(packagesToScan);
        
        //refreshed the context to create class and make autowires
        context.refresh();
        
        //return the created context
        return context;
    }

    /**
     * Builds a listable bean factory with the given beans
     * 
     * @param extraBeans
     * @return
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
