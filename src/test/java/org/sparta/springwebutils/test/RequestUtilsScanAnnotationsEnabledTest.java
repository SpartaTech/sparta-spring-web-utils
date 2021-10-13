/*
 * Sparta Software Co.
 * 2017
 */
package org.sparta.springwebutils.test;

import com.google.common.collect.Iterables;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sparta.springwebutils.RequestUtils;
import org.sparta.springwebutils.config.MvcConfig;
import org.sparta.springwebutils.controller.ClassAnnotatedController;
import org.sparta.springwebutils.controller.MethodAnnotatedController;
import org.sparta.springwebutils.entity.EntryPoint;
import org.sparta.springwebutils.entity.EntryPointParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Carlos Eduardo Endler Genz – Sparta Java Team
 * <p>
 * History:
 * - Mar 06, 2014 - Carlos Eduardo Endler Genz
 * - Oct 13, 2021 - Daniel Conde Diehl - Upgrading to Junit Jupiter
 */ 
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=MvcConfig.class)
@WebAppConfiguration
public class RequestUtilsScanAnnotationsEnabledTest {
	
	@Autowired
	private RequestUtils requestUtilsAnnotationsEnabled;
	
	@Test
	public void testExternalEntryPoints() {
		final List<EntryPoint> entryPoints = requestUtilsAnnotationsEnabled.retrieveAllExternalEntryPoints();
		
		// We expect only 3 entry points (2 per class annotation and 1 per method) to be returned
		assertNotNull(entryPoints);
		assertEquals(3, entryPoints.size(), "We should have only 3 entry points");
		
		final EntryPoint testTypeBlacklist = Iterables.find(entryPoints, ep -> ep.getMethodName().equals("testTypeBlacklist"));
		
		int totalParameters = 0;
		
		assertTrue(testTypeBlacklist.getUrls().contains("/classAnnotatedController/testTypeBlacklist"));
		assertTrue(testTypeBlacklist.getRequestMethods().contains(RequestMethod.POST));
		assertEquals(ClassAnnotatedController.class, testTypeBlacklist.getType());
		
		for (EntryPointParameter epp : testTypeBlacklist.getParameters()) {
			if (epp.getName().equals("inOne")) {
				totalParameters ++;
				assertEquals(String.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("inTwo")) {
				totalParameters ++;
				assertEquals(Integer.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else {
				fail("Invalid parameter, " + epp.getName());
			}
		}
		
		assertEquals(2, totalParameters);
		
		final EntryPoint testNameBlacklist = Iterables.find(entryPoints, ep -> ep.getMethodName().equals("testNameBlacklist"));
		
		totalParameters = 0;
		
		assertTrue(testNameBlacklist.getUrls().contains("/classAnnotatedController/testNameBlacklist"));
		assertTrue(testNameBlacklist.getRequestMethods().contains(RequestMethod.GET));
		assertEquals(ClassAnnotatedController.class, testNameBlacklist.getType());
		
		for (EntryPointParameter epp : testNameBlacklist.getParameters()) {
			if (epp.getName().equals("in")) {
				totalParameters ++;
				assertEquals(Integer.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("inToo")) {
				totalParameters ++;
				assertEquals(Boolean.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else {
				fail("Invalid parameter, " + epp.getName());
			}
		}
		
		assertEquals(2, totalParameters);
		
		final EntryPoint testBlacklists = Iterables.find(entryPoints, ep -> ep.getMethodDecorationName().equals("new_name"));
		 
		totalParameters = 0;
		
		assertTrue(testBlacklists.getUrls().contains("/methodAnnotatedController/testBlacklists"));
		assertTrue(testBlacklists.getRequestMethods().contains(RequestMethod.GET));
		assertTrue(testBlacklists.getRequestMethods().contains(RequestMethod.POST));
		assertEquals(MethodAnnotatedController.class, testBlacklists.getType());
		
		for (EntryPointParameter epp : testBlacklists.getParameters()) {
			if (epp.getName().equals("in")) {
				totalParameters ++;
				assertEquals(Integer.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("inToo")) {
				totalParameters ++;
				assertEquals(Boolean.class, epp.getType());
				assertFalse(epp.isRequired());
				assertEquals("true", epp.getDefaultValue());
			} else {
				fail("Invalid parameter, " + epp.getName());
			}
		}
	}

}
