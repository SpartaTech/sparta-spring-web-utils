package org.sparta.springwebutils.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparta.springwebutils.annotation.ExternalEntryPoint;
import org.sparta.springwebutils.entity.EntryPointParameter;

/** 
 * @author Carlos Eduardo Endler Genz – Sparta Java Team 
 * 
 * History: 
 *    Mar 5, 2014 - Carlos Eduardo Endler Genz
 *    Mar 14, 2014 - Daniel Conde Diehl - Not ignoring list on object scan
 *  
 */
public final class ExternalEntryPointHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExternalEntryPointHelper.class);
	
	/**
	 * Returns whether the supplied method is an Entry Point or not.
	 * It might be annotated by @ExternalEntryPoint
	 * 
	 * @param method Method to be scanned
	 * @param scanEntryPointAnnotation Does it has annotation
	 * @return boolean
	 */
	public static boolean isAnEntryPoint(Method method, boolean scanEntryPointAnnotation) {
		return !scanEntryPointAnnotation
			|| scanEntryPointAnnotation && method.isAnnotationPresent(ExternalEntryPoint.class)
			|| scanEntryPointAnnotation && method.getDeclaringClass().isAnnotationPresent(ExternalEntryPoint.class);
	}

	/**
	 * Deeply finds all the attributes of the supplied class
	 *
	 * @param parameterType Type of parameter
	 * @param typeBlacklist blackList by type
	 * @param nameBlacklist blackList by name
	 * @param maxDeepLevel How deep should algorithm go in the object
	 * @return List<EntryPointParameter>
	 */
	public static List<EntryPointParameter> getInternalEntryPointParametersRecursively(Class<?> parameterType, Set<Class<?>> typeBlacklist, Set<String> nameBlacklist, int maxDeepLevel) {
		return getInternalEntryPointParametersRecursively(parameterType, typeBlacklist, nameBlacklist, null, 1, maxDeepLevel);
	}

	/**
	 * Deeply finds all the attributes of the supplied class
	 *
	 * @param parameterType Type of parameter
	 * @param typeBlacklist blackList by type
	 * @param nameBlacklist blackList by name
	 * @param parent
	 * @param level what the level of immersion now
	 * @param maxDeepLevel How deep should algorithm go in the object
	 * @return List<EntryPointParameter>
	 */
	private static List<EntryPointParameter> getInternalEntryPointParametersRecursively(Class<?> parameterType, Set<Class<?>> typeBlacklist, Set<String> nameBlacklist, Field parent, int level, int maxDeepLevel) {
		final List<EntryPointParameter> list = new ArrayList<EntryPointParameter>();
		for (Field field : parameterType.getDeclaredFields()) {
			field.setAccessible(true);

			if (typeBlacklist.contains(field.getType()) || nameBlacklist.contains(field.getName())) {
				continue;
			}
			
			if (isSimpleRequestParameter(field.getType())) {
				final EntryPointParameter param = new EntryPointParameter();
				param.setName(parent != null ? parent.getName() + "." + field.getName() : field.getName());
				param.setType(field.getType());

				list.add(param);
			} else if (shouldWeGoDeep(field.getType()) && level < maxDeepLevel) {
				// Object arrays are not supported and lists are not supported
				list.addAll(getInternalEntryPointParametersRecursively(field.getType(), typeBlacklist, nameBlacklist, field, ++level, maxDeepLevel));
			} else {
				LOGGER.warn("We might have reached a cycle, ignoring parameter {}", field.getName());
			}
		}
		return list;
	}

	/**
	 * Decide whether to go deep or not base on type
	 *
	 * @param type parameter type for the decision
	 * @return boolean
	 */
	private static boolean shouldWeGoDeep(Class<?> type) {
		return !type.isArray() 
			&& !ClassUtils.isAssignable(type, Collection.class);
	}
	
	/**
	 * Finds out if the supplied type is a simple element (primitive/wrapper, an primitive/wrapper array or a Collection instance)
	 *
	 * @param parameterType
	 * @return boolean
	 */
	public static boolean isSimpleRequestParameter(Class<?> parameterType) {
		return ClassUtils.isPrimitiveOrWrapper(parameterType) 
			|| (parameterType.isArray() && ClassUtils.isPrimitiveOrWrapper(parameterType.getComponentType())) 
			|| (ClassUtils.isAssignable(parameterType, Collection.class))
			|| ClassUtils.isAssignable(parameterType, String.class)
			|| ClassUtils.isAssignable(parameterType, String[].class);
	}

	/**
	 * Based on the input for scanning annotations, look for @ExternalEntryPoint and get the decorated name from it, if any.
	 *
	 * @param method
	 * @param scanEntryPointAnnotation
	 * @return String
	 */
	public static String getEntryPointDecoratedName(Method method, boolean scanEntryPointAnnotation) {
		String decoratedName = method.getName();

		if (scanEntryPointAnnotation) {
			// we look at the method level
			if (method.isAnnotationPresent(ExternalEntryPoint.class)) {
				final ExternalEntryPoint externalEntryPoint = method.getAnnotation(ExternalEntryPoint.class);
	
				if (StringUtils.isNotBlank(externalEntryPoint.name())) {
					decoratedName = externalEntryPoint.name();
				}
			}
		}

		return decoratedName;
	}

	/**
	 * Based on the input for scanning annotations, look for @ExternalEntryPoint and get the specific type black list elements.
	 *
	 * @param predefinedTypeBlacklist
	 * @param method
	 * @param scanEntryPointAnnotation
	 * @return Set<Class<?>
	 */
	public static Set<Class<?>> getConsolidatedTypeBlacklist(List<Class<?>> predefinedTypeBlacklist, Method method, boolean scanEntryPointAnnotation) {
		final Set<Class<?>> consolidatedBlacklist = new HashSet<Class<?>>(predefinedTypeBlacklist);

		if (scanEntryPointAnnotation) {
			// first we look into the class level
			if (method.getDeclaringClass().isAnnotationPresent(ExternalEntryPoint.class)) {
				final ExternalEntryPoint externalEntryPoint = method.getDeclaringClass().getAnnotation(ExternalEntryPoint.class);

				if (externalEntryPoint.typeBlacklist() != null) {
					consolidatedBlacklist.addAll(Arrays.asList(externalEntryPoint.typeBlacklist()));
				}
			}

			// then we look at the method level
			if (method.isAnnotationPresent(ExternalEntryPoint.class)) {
				final ExternalEntryPoint externalEntryPoint = method.getAnnotation(ExternalEntryPoint.class);

				if (externalEntryPoint.typeBlacklist() != null) {
					consolidatedBlacklist.addAll(Arrays.asList(externalEntryPoint.typeBlacklist()));
				}
			}
		}

		return consolidatedBlacklist;
	}

	/**
	 * Based on the input for scanning annotations, look for @ExternalEntryPoint and get the specific name black list elements.
	 *
	 * @param predefinedNameBlacklist
	 * @param method
	 * @param scanEntryPointAnnotation
	 * @return Set<String>
	 */
	public static Set<String> getConsolidatedNameBlacklist(List<String> predefinedNameBlacklist, Method method, boolean scanEntryPointAnnotation) {
		final Set<String> consolidatedBlacklist = new HashSet<String>(predefinedNameBlacklist);

		if (scanEntryPointAnnotation) {
			// first we look into the class level
			if (method.getDeclaringClass().isAnnotationPresent(ExternalEntryPoint.class)) {
				final ExternalEntryPoint externalEntryPoint = method.getDeclaringClass().getAnnotation(ExternalEntryPoint.class);

				if (externalEntryPoint.nameBlacklist() != null) {
					consolidatedBlacklist.addAll(Arrays.asList(externalEntryPoint.nameBlacklist()));
				}
			}

			// then we look at the method level
			if (method.isAnnotationPresent(ExternalEntryPoint.class)) {
				final ExternalEntryPoint externalEntryPoint = method.getAnnotation(ExternalEntryPoint.class);

				if (externalEntryPoint.nameBlacklist() != null) {
					consolidatedBlacklist.addAll(Arrays.asList(externalEntryPoint.nameBlacklist()));
				}
			}
		}

		return consolidatedBlacklist;
	}
}
