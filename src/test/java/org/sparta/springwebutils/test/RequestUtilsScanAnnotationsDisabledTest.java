/*
 * Sparta Software Co.
 * 2021
 */
package org.sparta.springwebutils.test;

import com.google.common.collect.Iterables;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sparta.springwebutils.RequestUtils;
import org.sparta.springwebutils.config.MvcConfig;
import org.sparta.springwebutils.controller.NotAnnotatedController;
import org.sparta.springwebutils.entity.EntryPoint;
import org.sparta.springwebutils.entity.EntryPointParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite without annotation scan for methods exclusion
 *
 * @author Daniel Conde Diehl - Sparta Java Team
 * <p>
 * History:
 * - Mar 06, 2014 - Daniel Conde Diehl
 * - Oct 13, 2021 - Daniel Conde Diehl - Upgrading to Junit Jupiter
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=MvcConfig.class)
@WebAppConfiguration
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
public class RequestUtilsScanAnnotationsDisabledTest {
	
	@Autowired
	private RequestUtils requestUtilsAnnotationsDisabled;
	
	@Test
	public void testArraysString() {
		List<EntryPoint> entryPoints = requestUtilsAnnotationsDisabled.retrieveAllExternalEntryPoints();
		EntryPoint ep = Iterables.find(entryPoints, ep1 -> ep1.getMethodName().equals("testArraysString"));
		
		int totalParameters = 0;
		
		assertTrue(ep.getUrls().contains("/testArraysStringRP"));
		assertTrue(ep.getUrls().contains("/testArraysStringRP2"));
		assertEquals(NotAnnotatedController.class, ep.getType());
		
		for (EntryPointParameter epp : ep.getParameters()) {
			if (epp.getName().equals("requestParamStrArray")) {
				totalParameters ++;
				assertEquals(String[].class, epp.getType());
				assertTrue(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("strArrayNotAnnotated")) {
				totalParameters ++;
				assertEquals(String[].class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("requestParamStrList")) {
				totalParameters ++;
				assertEquals(List.class, epp.getType());
				assertTrue(epp.isRequired());
				assertEquals("default", epp.getDefaultValue());
			} else if (epp.getName().equals("strListNotAnnotated")) {
				totalParameters ++;
				assertEquals(List.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("requestParamStrSet")) {
				totalParameters ++;
				assertEquals(Set.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("strSetNotAnnotated")) {
				totalParameters ++;
				assertEquals(Set.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("requestParamStrCollection")) {
				totalParameters ++;
				assertEquals(Collection.class, epp.getType());
				assertTrue(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("strCollectionNotAnnotated")) {
				totalParameters ++;
				assertEquals(Collection.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else {
				fail("Invalid parameter, " + epp.getName());
			}
		}
		assertEquals(8, totalParameters);
	}

	
	@Test
	public void testArraysInt() {
		List<EntryPoint> entryPoints = requestUtilsAnnotationsDisabled.retrieveAllExternalEntryPoints();
		EntryPoint ep = Iterables.find(entryPoints, ep1 -> ep1.getMethodName().equals("testArraysInt"));
		
		int totalParameters = 0;
		
		assertTrue(ep.getUrls().contains("/testArraysIntRP"));
		assertTrue(ep.getUrls().contains("/testArraysIntRP2"));
		assertEquals(NotAnnotatedController.class, ep.getType());
		
		for (EntryPointParameter epp : ep.getParameters()) {
			if (epp.getName().equals("requestParamIntArray")) {
				totalParameters ++;
				assertEquals(int[].class, epp.getType());
				assertTrue(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("intArrayNotAnnotated")) {
				totalParameters ++;
				assertEquals(int[].class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("requestParamIntegerArray")) {
				totalParameters ++;
				assertEquals(Integer[].class, epp.getType());
				assertTrue(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("integerArrayNotAnnotated")) {
				totalParameters ++;
				assertEquals(Integer[].class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else {
				fail("Invalid parameter, " + epp.getName());
			}
		}
		assertEquals(4, totalParameters);
	}
	
	@Test
	public void testArraysLong() {
		List<EntryPoint> entryPoints = requestUtilsAnnotationsDisabled.retrieveAllExternalEntryPoints();
		EntryPoint ep = Iterables.find(entryPoints, ep1 -> ep1.getMethodName().equals("testArraysLong"));
		
		int totalParameters = 0;
		
		assertTrue(ep.getUrls().contains("/testArraysLongRP"));
		assertEquals(NotAnnotatedController.class, ep.getType());
		
		for (EntryPointParameter epp : ep.getParameters()) {
			if (epp.getName().equals("requestParamLongArray")) {
				totalParameters ++;
				assertEquals(long[].class, epp.getType());
				assertTrue(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("longArrayNotAnnotated")) {
				totalParameters ++;
				assertEquals(long[].class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			}else if (epp.getName().equals("requestParamLongObjArray")) {
				totalParameters ++;
				assertEquals(Long[].class, epp.getType());
				assertTrue(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("longObjArrayNotAnnotated")) {
				totalParameters ++;
				assertEquals(Long[].class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("$jacocoData")) {
				//Just ignore
			} else {
				fail("Invalid parameter, " + epp.getName());
			}
		}
		assertEquals(4, totalParameters);
	}
	
	@Test
	public void testArraysDouble() {
		List<EntryPoint> entryPoints = requestUtilsAnnotationsDisabled.retrieveAllExternalEntryPoints();
		EntryPoint ep = Iterables.find(entryPoints, ep1 -> ep1.getMethodName().equals("testArraysDouble"));
		
		int totalParameters = 0;
		
		assertTrue(ep.getUrls().contains("/testArraysDoubleRP"));
		assertEquals(NotAnnotatedController.class, ep.getType());
		
		for (EntryPointParameter epp : ep.getParameters()) {
			if (epp.getName().equals("requestParamDoubleArray")) {
				totalParameters ++;
				assertEquals(double[].class, epp.getType());
				assertTrue(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("doubleArrayNotAnnotated")) {
				totalParameters ++;
				assertEquals(double[].class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("requestParamDoubleObjArray")) {
				totalParameters ++;
				assertEquals(Double[].class, epp.getType());
				assertTrue(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("doubleObjArrayNotAnnotated")) {
				totalParameters ++;
				assertEquals(Double[].class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else {
				fail("Invalid parameter, " + epp.getName());
			}
		}
		assertEquals(4, totalParameters);
	}

	
	@Test
	public void testPrimitives() {
		List<EntryPoint> entryPoints = requestUtilsAnnotationsDisabled.retrieveAllExternalEntryPoints();
		EntryPoint ep = Iterables.find(entryPoints, ep1 -> ep1.getMethodName().equals("testPrimitives"));
		
		int totalParameters = 0;
		
		assertTrue(ep.getUrls().contains("/primitives"));
		assertEquals(NotAnnotatedController.class, ep.getType());
		
		for (EntryPointParameter epp : ep.getParameters()) {
			if (epp.getName().equals("str")) {
				totalParameters ++;
				assertEquals(String.class, epp.getType());
				assertEquals(false, epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("pInt")) {
				totalParameters ++;
				assertEquals(int.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("pLong")) {
				totalParameters ++;
				assertEquals(long.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("pDouble")) {
				totalParameters ++;
				assertEquals(double.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("pIntObj")) {
				totalParameters ++;
				assertEquals(Integer.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("pLongObj")) {
				totalParameters ++;
				assertEquals(Long.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("pDoubleObj")) {
				totalParameters ++;
				assertEquals(Double.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else {
				fail("Invalid parameter, " + epp.getName());
			}
		}
		assertEquals(7, totalParameters);
	}
	
	@Test
	public void testEntity() {
		List<EntryPoint> entryPoints = requestUtilsAnnotationsDisabled.retrieveAllExternalEntryPoints();
		EntryPoint ep = Iterables.find(entryPoints, ep1 -> ep1.getMethodName().equals("testObject"));
		
		int totalParameters = 0;
		
		assertTrue(ep.getUrls().contains("/testObject"));
		assertEquals(NotAnnotatedController.class, ep.getType());
		
		for (EntryPointParameter epp : ep.getParameters()) {
			if (epp.getName().equals("vInt")) {
				totalParameters ++;
				assertEquals(int.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("integers.vInt")) {
				totalParameters ++;
				assertEquals(int.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("integers.vIntObj")) {
				totalParameters ++;
				assertEquals(Integer.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("integers.vIntArray")) {
				totalParameters ++;
				assertEquals(int[].class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("integers.vIntArrayObj")) {
				totalParameters ++;
				assertEquals(Integer[].class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("integers.vIntListObj")) {
				totalParameters ++;
				assertEquals(List.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("longs.vLong")) {
				totalParameters ++;
				assertEquals(long.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("longs.vLongObj")) {
				totalParameters ++;
				assertEquals(Long.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("longs.vLongArray")) {
				totalParameters ++;
				assertEquals(long[].class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("longs.vLongArrayObj")) {
				totalParameters ++;
				assertEquals(Long[].class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("longs.vLongListObj")) {
				totalParameters ++;
				assertEquals(List.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("testStr")) {
				totalParameters ++;
				assertEquals(String.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().contains("$jacocoData")
					|| epp.getName().contains("__$lineHits$__")) {
				//Just ignore, these are coverage parameters
			} else {
				fail("Invalid parameter, " + epp.getName());
			}
		}
		
		assertEquals(12, totalParameters);
	}
	
	@Test
	public void testCyclic() {
		final List<EntryPoint> entryPoints = requestUtilsAnnotationsDisabled.retrieveAllExternalEntryPoints();
		final EntryPoint ep = Iterables.find(entryPoints, ep1 -> ep1.getMethodName().equals("testCyclicReference"));
		
		assertTrue(ep.getUrls().contains("/testCyclicReference"));
		assertEquals(NotAnnotatedController.class, ep.getType());

		assertEquals(0, cleanupCoverageParameters(ep.getParameters()).size(), "Not expecting any parameters");
	}
	
	@Test
	public void testIgnore() {
		List<EntryPoint> entryPoints = requestUtilsAnnotationsDisabled.retrieveAllExternalEntryPoints();
		EntryPoint ep = Iterables.find(entryPoints, ep1 -> ep1.getMethodName().equals("testIgnoreObj"));
		
		assertTrue(ep.getUrls().contains("/testIgnore"));
		assertEquals(NotAnnotatedController.class, ep.getType());

		assertEquals(1, cleanupCoverageParameters(ep.getParameters()).size(), "Expecting one parameter");

		EntryPointParameter epp = ep.getParameters().get(0);
		assertEquals("str", epp.getName());
		assertEquals(String.class, epp.getType());
		assertFalse(epp.isRequired());
		assertEquals("", epp.getDefaultValue());
	}
	
	@Test
	public void testIgnoreInside() {
		List<EntryPoint> entryPoints = requestUtilsAnnotationsDisabled.retrieveAllExternalEntryPoints();
		EntryPoint ep = Iterables.find(entryPoints, ep1 -> ep1.getMethodName().equals("testIgnoreInsideObj"));
		
		assertTrue(ep.getUrls().contains("/testIgnoreInside"));
		assertEquals(NotAnnotatedController.class, ep.getType());


		assertEquals(1, cleanupCoverageParameters(ep.getParameters()).size(), "Expecting one parameter");

		EntryPointParameter epp = ep.getParameters().get(0);
		assertEquals("str", epp.getName());
		assertEquals(String.class, epp.getType());
		assertFalse(epp.isRequired());
		assertEquals("", epp.getDefaultValue());
	}

	/**
	 * Returns a list without coverage parameters. When doing code coverage some hidden parameters are added,
	 * which disturbs the tests.
	 *
	 * @param originalParameters list with all parameters
	 * @return filtered list without the coverage-related parameters
	 */
	private List<EntryPointParameter> cleanupCoverageParameters(List<EntryPointParameter> originalParameters) {
		return originalParameters.stream()
				.filter(it -> !it.getName().contains("$jacocoData")
						&& !it.getName().contains("__$lineHits$__"))
				.collect(Collectors.toList());
	}
}
