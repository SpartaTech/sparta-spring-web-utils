package org.sparta.springwebutils.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sparta.springwebutils.RequestUtils;
import org.sparta.springwebutils.config.MvcConfig;
import org.sparta.springwebutils.controller.ClassAnnotatedController;
import org.sparta.springwebutils.controller.MethodAnnotatedController;
import org.sparta.springwebutils.entity.EntryPoint;
import org.sparta.springwebutils.entity.EntryPointParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/** 
 * @author Carlos Eduardo Endler Genz – Sparta Java Team 
 * 
 * History: 
 *    Mar 6, 2014 - Carlos Eduardo Endler Genz
 *  
 */ 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=MvcConfig.class)
@WebAppConfiguration
public class RequestUtilsScanAnnotationsEnabledTest {
	
	@Autowired
	private RequestUtils requestUtilsAnnotationsEnabled;
	
	@Test
	public void testExternalEntryPoints() {
		final List<EntryPoint> entryPoints = requestUtilsAnnotationsEnabled.retrieveAllExternalEntryPoints();
		
		// We expect only 3 entry points (2 per class annotation and 1 per method) to be returned
		Assert.assertNotNull(entryPoints);
		Assert.assertTrue("We should have only 3 entry points", entryPoints.size() == 3);
		
		final EntryPoint testTypeBlacklist = Iterables.find(entryPoints, new Predicate<EntryPoint>() {
			@Override
			public boolean apply(EntryPoint ep) {
				return ep.getMethodName().equals("testTypeBlacklist");
			}
		});
		
		int totalParameters = 0;
		
		Assert.assertTrue(testTypeBlacklist.getUrls().contains("/classAnnotatedController/testTypeBlacklist"));
		Assert.assertTrue(testTypeBlacklist.getRequestMethods().contains(RequestMethod.POST));
		Assert.assertEquals(ClassAnnotatedController.class, testTypeBlacklist.getType());
		
		for (EntryPointParameter epp : testTypeBlacklist.getParameters()) {
			if (epp.getName().equals("inOne")) {
				totalParameters ++;
				Assert.assertEquals(String.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("inTwo")) {
				totalParameters ++;
				Assert.assertEquals(Integer.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else {
				Assert.fail("Invalid parameter, " + epp.getName());
			}
		}
		
		Assert.assertEquals(2, totalParameters);
		
		final EntryPoint testNameBlacklist = Iterables.find(entryPoints, new Predicate<EntryPoint>() {
			@Override
			public boolean apply(EntryPoint ep) {
				return ep.getMethodName().equals("testNameBlacklist");
			}
		});
		
		totalParameters = 0;
		
		Assert.assertTrue(testNameBlacklist.getUrls().contains("/classAnnotatedController/testNameBlacklist"));
		Assert.assertTrue(testNameBlacklist.getRequestMethods().contains(RequestMethod.GET));
		Assert.assertEquals(ClassAnnotatedController.class, testNameBlacklist.getType());
		
		for (EntryPointParameter epp : testNameBlacklist.getParameters()) {
			if (epp.getName().equals("in")) {
				totalParameters ++;
				Assert.assertEquals(Integer.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("inToo")) {
				totalParameters ++;
				Assert.assertEquals(Boolean.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else {
				Assert.fail("Invalid parameter, " + epp.getName());
			}
		}
		
		Assert.assertEquals(2, totalParameters);
		
		final EntryPoint testBlacklists = Iterables.find(entryPoints, new Predicate<EntryPoint>() {
			@Override
			public boolean apply(EntryPoint ep) {
				return ep.getMethodDecorationName().equals("new_name");
			}
		});
		 
		totalParameters = 0;
		
		Assert.assertTrue(testBlacklists.getUrls().contains("/methodAnnotatedController/testBlacklists"));
		Assert.assertTrue(testBlacklists.getRequestMethods().contains(RequestMethod.GET));
		Assert.assertTrue(testBlacklists.getRequestMethods().contains(RequestMethod.POST));
		Assert.assertEquals(MethodAnnotatedController.class, testBlacklists.getType());
		
		for (EntryPointParameter epp : testBlacklists.getParameters()) {
			if (epp.getName().equals("in")) {
				totalParameters ++;
				Assert.assertEquals(Integer.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("inToo")) {
				totalParameters ++;
				Assert.assertEquals(Boolean.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("true", epp.getDefaultValue());
			} else {
				Assert.fail("Invalid parameter, " + epp.getName());
			}
		}
	}

}
