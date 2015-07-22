package org.sparta.springwebutils.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;

import org.sparta.springwebutils.RequestUtils;
import org.sparta.springwebutils.entity.TestIgnoreType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/** 
 * 
 * Config Beans for test
 * 
 * @author danieldiehl - Sparta Java Team 
 * 
 * History: 
 *    Mar 6, 2014 - danieldiehl
 *  
 */
@EnableWebMvc
@Configuration
@ComponentScan("org.sparta.springwebutils.controller")
public class MvcConfig {	
	
	@Bean
	public ServletContext servletContext() {
		return new MockServletContext();
	}
	
	@Bean
	public RequestUtils requestUtilsAnnotationsDisabled() {
		RequestUtils ru = new RequestUtils();
		ru.setScanEntryPointAnnotation(false);
		ru.setParamNamesBlacklist(Collections.singletonList("ignoreByName"));
		
		List<Class<?>> ignoreClasses = new ArrayList<Class<?>>();
		ignoreClasses.add(TestIgnoreType.class);
		ru.setExtraParamTypesBlacklist(ignoreClasses);
		
		return ru;
	}
	
	@Bean
	public RequestUtils requestUtilsClearParamTypeBlackList() {
		RequestUtils ru = new RequestUtils();
		ru.setScanEntryPointAnnotation(false);
		ru.setParamTypesBlacklist(new ArrayList<Class<?>>());
		
		return ru;
	}

	@Bean
	public RequestUtils requestUtilsMAxLevelDeep1() {
		RequestUtils ru = new RequestUtils();
		ru.setScanEntryPointAnnotation(false);
		ru.setNameDiscover(new LocalVariableTableParameterNameDiscoverer());
		ru.setMaxDeepLevel(1);
		
		return ru;
	}
	
	@Bean
	public RequestUtils requestUtilsAnnotationsEnabled() {
		return new RequestUtils();
	}
	
}
