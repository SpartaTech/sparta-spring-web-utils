package org.sparta.springwebutils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparta.springwebutils.entity.EntryPoint;
import org.sparta.springwebutils.entity.EntryPointParameter;
import org.sparta.springwebutils.util.ExternalEntryPointHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/** 
 * @author Carlos Eduardo Endler Genz – Sparta Java Team 
 * 
 * History: 
 *    Mar 4, 2014 - Carlos Eduardo Endler Genz
 *    Mar 14, 2014 - Daniel Conde Diehl - Not ignoring list on object scan
 *  
 */ 
public class RequestUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestUtils.class);
	
	@Autowired
	private RequestMappingHandlerMapping handlerMapping;
	
	private List<String> paramNamesBlacklist;
	
	private List<Class<?>> paramTypesBlacklist;
	
	private boolean scanEntryPointAnnotation;
	
	private ParameterNameDiscoverer nameDiscover;
	
	private Integer maxDeepLevel;
	
	/**
	 * Class constructor, no parameters
	 */
	public RequestUtils() {
		// No default naming blacklist
		paramNamesBlacklist = new ArrayList<String>();
		
		// Default types blacklist
		paramTypesBlacklist = new ArrayList<Class<?>>();
		paramTypesBlacklist.add(BindingResult.class);
		paramTypesBlacklist.add(HttpServletRequest.class);
		paramTypesBlacklist.add(HttpRequest.class);
		paramTypesBlacklist.add(HttpServletResponse.class);
		paramTypesBlacklist.add(HttpSession.class);
		paramTypesBlacklist.add(Map.class);
		paramTypesBlacklist.add(Model.class);
		paramTypesBlacklist.add(Class.class);
		
		// Set scanning to true as default
		scanEntryPointAnnotation = true;
		
		// Default parameter name discoverer
		nameDiscover = new LocalVariableTableParameterNameDiscoverer();
		
		// Default is 3
		maxDeepLevel = 3;
	}
	
	/**
	 * Goes through @RequestMapping annotations from Spring and return a list of its elements.
	 *
	 * @return List<EntryPoint> List with all entry points and parameters
	 */
	public List<EntryPoint> retrieveAllExternalEntryPoints() {
		
		final List<EntryPoint> entryPoints = new ArrayList<EntryPoint>();
		
		final Map<RequestMappingInfo, HandlerMethod> allUrls = handlerMapping.getHandlerMethods();
		
		for (RequestMappingInfo mappingInfo : allUrls.keySet()) {
			final HandlerMethod handlerMethod = allUrls.get(mappingInfo);
			
			// If no pattern is defined, we do not add to the list
			if (mappingInfo.getPatternsCondition() != null && ExternalEntryPointHelper.isAnEntryPoint(handlerMethod.getMethod(), scanEntryPointAnnotation)) {
				final EntryPoint entryPoint = new EntryPoint();
				entryPoint.setParameters(new ArrayList<EntryPointParameter>());
				
				// All the url's this method can handle
				entryPoint.setUrls(mappingInfo.getPatternsCondition().getPatterns());
				
				// If there specified methods, we add them to the returned object
				if (mappingInfo.getMethodsCondition() != null) { 
					entryPoint.setRequestMethods(mappingInfo.getMethodsCondition().getMethods());
				}
				
				final Method method = handlerMethod.getMethod();
				entryPoint.setMethodName(method.getName());
				entryPoint.setMethodDecorationName(ExternalEntryPointHelper.getEntryPointDecoratedName(method, scanEntryPointAnnotation));
				entryPoint.setType(method.getDeclaringClass());
				entryPoint.setResponseType(method.getReturnType());
				
				// Get the defined parameter names, they might be overwritten by a @RequestParam
				final String[] parameterNames = nameDiscover.getParameterNames(method);
				
				// Consolidated blacklist, if might have details on @ExternalEntryPoint
				final Set<Class<?>> consolidatedTypeBlacklist = ExternalEntryPointHelper.getConsolidatedTypeBlacklist(paramTypesBlacklist, method, scanEntryPointAnnotation);
				final Set<String> consolidatedNameBlacklist = ExternalEntryPointHelper.getConsolidatedNameBlacklist(paramNamesBlacklist, method, scanEntryPointAnnotation);
				
				// Time to retrieve all the parameters
				for (MethodParameter methodParameter : handlerMethod.getMethodParameters()) {
					computeInputParatemeter(entryPoint, parameterNames, consolidatedTypeBlacklist, consolidatedNameBlacklist, methodParameter);
					
				}
				
				entryPoints.add(entryPoint);
			}
			
		}
		
		return entryPoints;
	}

	/**
	 * Checks whether or not the supplied parameter should be part of the response or not. It adds to the entry point, if necessary.
	 *
	 * @param entryPoint
	 * @param parameterNames
	 * @param consolidatedTypeBlacklist
	 * @param consolidatedNameBlacklist
	 * @param methodParameter
	 */
	private void computeInputParatemeter(final EntryPoint entryPoint, final String[] parameterNames, final Set<Class<?>> consolidatedTypeBlacklist,
		final Set<String> consolidatedNameBlacklist, MethodParameter methodParameter) {
		// If the type is part of the blacklist, we discard it
		if (consolidatedTypeBlacklist.contains(methodParameter.getParameterType())) {
			LOGGER.debug("Ignoring parameter type [{}]. It is on the blacklist.", methodParameter.getParameterType());
			return;
		}
		
		// If we have a simple parameter type (primitives, wrappers, collections, arrays of primitives), we just get the name and we are done
		if (ExternalEntryPointHelper.isSimpleRequestParameter(methodParameter.getParameterType())) {
			// We need to look for @RequestParam in order to define its name
			final String parameterRealName = parameterNames[methodParameter.getParameterIndex()];
			LOGGER.debug("Parameter Real Name [{}]", parameterRealName);
			
			// If the real name is part of the blacklist, we don't need to go any further
			if (consolidatedNameBlacklist.contains(parameterRealName)) {
				LOGGER.debug("Ignoring parameter name [{}]. It is on the blacklist.", parameterRealName);
				return;
			}
			
			final EntryPointParameter entryPointParameter = new EntryPointParameter();
			entryPointParameter.setName(parameterRealName);
			entryPointParameter.setType(methodParameter.getParameterType());
			
			// Look for a change of names and the required attribute, true by default
			if (methodParameter.hasParameterAnnotation(RequestParam.class)) {
				final RequestParam requestParam = methodParameter.getParameterAnnotation(RequestParam.class);
				
				final String definedName = StringUtils.trimToEmpty(requestParam.value());
				
				// If the defined name is part of the blacklist, we don't need to go any further
				if (consolidatedNameBlacklist.contains(definedName)) {
					LOGGER.debug("Ignoring parameter @RequestParam defined name [{}]. It is on the blacklist.", definedName);
					return;
				}
				
				entryPointParameter.setName(StringUtils.isNotBlank(definedName) ? definedName : entryPointParameter.getName());
				entryPointParameter.setRequired(requestParam.required());
				entryPointParameter.setDefaultValue(StringUtils.equals(ValueConstants.DEFAULT_NONE, requestParam.defaultValue()) ? "" : requestParam.defaultValue());
			}
			
			entryPoint.getParameters().add(entryPointParameter);
		} else if (!methodParameter.getParameterType().isArray()) {
			// Here we have an object, that we need to deep dive and get all its attributes, object arrays are not supported
			entryPoint.getParameters().addAll(ExternalEntryPointHelper.getInternalEntryPointParametersRecursively(methodParameter.getParameterType(), consolidatedTypeBlacklist, consolidatedNameBlacklist, maxDeepLevel));
		}
	}

	/**
	 * Setter method for paramNamesBlacklist
	 * @param paramNamesBlacklist the paramNamesBlacklist to set
	 */
	public void setParamNamesBlacklist(List<String> paramNamesBlacklist) {
		this.paramNamesBlacklist = paramNamesBlacklist;
	}

	/**
	 * Setter method for paramTypesBlacklist
	 * @param paramTypesBlacklist the paramTypesBlacklist to set
	 */
	public void setParamTypesBlacklist(List<Class<?>> paramTypesBlacklist) {
		this.paramTypesBlacklist = paramTypesBlacklist;
	}
	
	/**
	 * Append more param types to blacklist
	 * this method keeps the default blacklist and add these are extra ones
	 *
	 * @param paramTypesBlacklist extra list
	 */
	public void setExtraParamTypesBlacklist(List<Class<?>> extraParamTypesBlacklist) {
		this.paramTypesBlacklist.addAll(extraParamTypesBlacklist);
	}
	
	/**
	 * Setter method for scanEntryPointAnnotation
	 * @param scanEntryPointAnnotation the scanEntryPointAnnotation to set
	 */
	public void setScanEntryPointAnnotation(boolean scanEntryPointAnnotation) {
		this.scanEntryPointAnnotation = scanEntryPointAnnotation;
	}

	/**
	 * Setter method for nameDiscover
	 * @param nameDiscover the nameDiscover to set
	 */
	public void setNameDiscover(ParameterNameDiscoverer nameDiscover) {
		this.nameDiscover = nameDiscover;
	}

	/**
	 * Setter method for maxDeepLevel
	 * @param maxDeepLevel the maxDeepLevel to set
	 */
	public void setMaxDeepLevel(Integer maxDeepLevel) {
		this.maxDeepLevel = maxDeepLevel;
	}
}
