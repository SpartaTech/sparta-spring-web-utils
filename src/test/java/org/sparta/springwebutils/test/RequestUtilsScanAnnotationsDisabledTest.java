package org.sparta.springwebutils.test;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sparta.springwebutils.RequestUtils;
import org.sparta.springwebutils.config.MvcConfig;
import org.sparta.springwebutils.controller.NotAnnotatedController;
import org.sparta.springwebutils.entity.EntryPoint;
import org.sparta.springwebutils.entity.EntryPointParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/** 
 * 
 * Test suite without annotation scan for methods exclusion
 * 
 * @author danieldiehl - Sparta Java Team 
 * 
 * History: 
 *    Mar 6, 2014 - danieldiehl
 *  
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=MvcConfig.class)
@WebAppConfiguration
public class RequestUtilsScanAnnotationsDisabledTest {
	
	@Autowired
	private RequestUtils requestUtilsAnnotationsDisabled;
	
	@Test
	public void testArraysString() {
		List<EntryPoint> entryPoints = requestUtilsAnnotationsDisabled.retrieveAllExternalEntryPoints();
		EntryPoint ep = Iterables.find(entryPoints, new Predicate<EntryPoint>() {
			@Override
			public boolean apply(EntryPoint ep) {
				return ep.getMethodName().equals("testArraysString");
			}
		});
		
		int totalParameters = 0;
		
		Assert.assertTrue(ep.getUrls().contains("/testArraysStringRP"));
		Assert.assertTrue(ep.getUrls().contains("/testArraysStringRP2"));
		Assert.assertEquals(NotAnnotatedController.class, ep.getType());
		
		for (EntryPointParameter epp : ep.getParameters()) {
			if (epp.getName().equals("requestParamStrArray")) {
				totalParameters ++;
				Assert.assertEquals(String[].class, epp.getType());
				Assert.assertEquals(true, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("strArrayNotAnnotated")) {
				totalParameters ++;
				Assert.assertEquals(String[].class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("requestParamStrList")) {
				totalParameters ++;
				Assert.assertEquals(List.class, epp.getType());
				Assert.assertEquals(true, epp.isRequired());
				Assert.assertEquals("default", epp.getDefaultValue());
			} else if (epp.getName().equals("strListNotAnnotated")) {
				totalParameters ++;
				Assert.assertEquals(List.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("requestParamStrSet")) {
				totalParameters ++;
				Assert.assertEquals(Set.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("strSetNotAnnotated")) {
				totalParameters ++;
				Assert.assertEquals(Set.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("requestParamStrCollection")) {
				totalParameters ++;
				Assert.assertEquals(Collection.class, epp.getType());
				Assert.assertEquals(true, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("strCollectionNotAnnotated")) {
				totalParameters ++;
				Assert.assertEquals(Collection.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else {
				Assert.fail("Invalid parameter, " + epp.getName());
			}
		}
		Assert.assertEquals(8, totalParameters);
	}

	
	@Test
	public void testArraysInt() {
		List<EntryPoint> entryPoints = requestUtilsAnnotationsDisabled.retrieveAllExternalEntryPoints();
		EntryPoint ep = Iterables.find(entryPoints, new Predicate<EntryPoint>() {
			@Override
			public boolean apply(EntryPoint ep) {
				return ep.getMethodName().equals("testArraysInt");
			}
		});
		
		int totalParameters = 0;
		
		Assert.assertTrue(ep.getUrls().contains("/testArraysIntRP"));
		Assert.assertTrue(ep.getUrls().contains("/testArraysIntRP2"));
		Assert.assertEquals(NotAnnotatedController.class, ep.getType());
		
		for (EntryPointParameter epp : ep.getParameters()) {
			if (epp.getName().equals("requestParamIntArray")) {
				totalParameters ++;
				Assert.assertEquals(int[].class, epp.getType());
				Assert.assertEquals(true, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("intArrayNotAnnotated")) {
				totalParameters ++;
				Assert.assertEquals(int[].class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			}else if (epp.getName().equals("requestParamIntegerArray")) {
				totalParameters ++;
				Assert.assertEquals(Integer[].class, epp.getType());
				Assert.assertEquals(true, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("integerArrayNotAnnotated")) {
				totalParameters ++;
				Assert.assertEquals(Integer[].class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else {
				Assert.fail("Invalid parameter, " + epp.getName());
			}
		}
		Assert.assertEquals(4, totalParameters);
	}
	
	@Test
	public void testArraysLong() {
		List<EntryPoint> entryPoints = requestUtilsAnnotationsDisabled.retrieveAllExternalEntryPoints();
		EntryPoint ep = Iterables.find(entryPoints, new Predicate<EntryPoint>() {
			@Override
			public boolean apply(EntryPoint ep) {
				return ep.getMethodName().equals("testArraysLong");
			}
		});
		
		int totalParameters = 0;
		
		Assert.assertTrue(ep.getUrls().contains("/testArraysLongRP"));
		Assert.assertEquals(NotAnnotatedController.class, ep.getType());
		
		for (EntryPointParameter epp : ep.getParameters()) {
			if (epp.getName().equals("requestParamLongArray")) {
				totalParameters ++;
				Assert.assertEquals(long[].class, epp.getType());
				Assert.assertEquals(true, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("longArrayNotAnnotated")) {
				totalParameters ++;
				Assert.assertEquals(long[].class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			}else if (epp.getName().equals("requestParamLongObjArray")) {
				totalParameters ++;
				Assert.assertEquals(Long[].class, epp.getType());
				Assert.assertEquals(true, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("longObjArrayNotAnnotated")) {
				totalParameters ++;
				Assert.assertEquals(Long[].class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else {
				Assert.fail("Invalid parameter, " + epp.getName());
			}
		}
		Assert.assertEquals(4, totalParameters);
	}
	
	@Test
	public void testArraysDouble() {
		List<EntryPoint> entryPoints = requestUtilsAnnotationsDisabled.retrieveAllExternalEntryPoints();
		EntryPoint ep = Iterables.find(entryPoints, new Predicate<EntryPoint>() {
			@Override
			public boolean apply(EntryPoint ep) {
				return ep.getMethodName().equals("testArraysDouble");
			}
		});
		
		int totalParameters = 0;
		
		Assert.assertTrue(ep.getUrls().contains("/testArraysDoubleRP"));
		Assert.assertEquals(NotAnnotatedController.class, ep.getType());
		
		for (EntryPointParameter epp : ep.getParameters()) {
			if (epp.getName().equals("requestParamDoubleArray")) {
				totalParameters ++;
				Assert.assertEquals(double[].class, epp.getType());
				Assert.assertEquals(true, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("doubleArrayNotAnnotated")) {
				totalParameters ++;
				Assert.assertEquals(double[].class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			}else if (epp.getName().equals("requestParamDoubleObjArray")) {
				totalParameters ++;
				Assert.assertEquals(Double[].class, epp.getType());
				Assert.assertEquals(true, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("doubleObjArrayNotAnnotated")) {
				totalParameters ++;
				Assert.assertEquals(Double[].class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else {
				Assert.fail("Invalid parameter, " + epp.getName());
			}
		}
		Assert.assertEquals(4, totalParameters);
	}

	
	@Test
	public void testPrimitives() {
		List<EntryPoint> entryPoints = requestUtilsAnnotationsDisabled.retrieveAllExternalEntryPoints();
		EntryPoint ep = Iterables.find(entryPoints, new Predicate<EntryPoint>() {
			@Override
			public boolean apply(EntryPoint ep) {
				return ep.getMethodName().equals("testPrimitives");
			}
		});
		
		int totalParameters = 0;
		
		Assert.assertTrue(ep.getUrls().contains("/primitives"));
		Assert.assertEquals(NotAnnotatedController.class, ep.getType());
		
		for (EntryPointParameter epp : ep.getParameters()) {
			if (epp.getName().equals("str")) {
				totalParameters ++;
				Assert.assertEquals(String.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("pInt")) {
				totalParameters ++;
				Assert.assertEquals(int.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			}else if (epp.getName().equals("pLong")) {
				totalParameters ++;
				Assert.assertEquals(long.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("pDouble")) {
				totalParameters ++;
				Assert.assertEquals(double.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("pIntObj")) {
				totalParameters ++;
				Assert.assertEquals(Integer.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			}else if (epp.getName().equals("pLongObj")) {
				totalParameters ++;
				Assert.assertEquals(Long.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("pDoubleObj")) {
				totalParameters ++;
				Assert.assertEquals(Double.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else {
				Assert.fail("Invalid parameter, " + epp.getName());
			}
		}
		Assert.assertEquals(7, totalParameters);
	}
	
	@Test
	public void testEntity() throws Exception {
		List<EntryPoint> entryPoints = requestUtilsAnnotationsDisabled.retrieveAllExternalEntryPoints();
		EntryPoint ep = Iterables.find(entryPoints, new Predicate<EntryPoint>() {
			@Override
			public boolean apply(EntryPoint ep) {
				return ep.getMethodName().equals("testObject");
			}
		});
		
		int totalParameters = 0;
		
		Assert.assertTrue(ep.getUrls().contains("/testObject"));
		Assert.assertEquals(NotAnnotatedController.class, ep.getType());
		
		for (EntryPointParameter epp : ep.getParameters()) {
			if (epp.getName().equals("vInt")) {
				totalParameters ++;
				Assert.assertEquals(int.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("integers.vInt")) {
				totalParameters ++;
				Assert.assertEquals(int.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("integers.vIntObj")) {
				totalParameters ++;
				Assert.assertEquals(Integer.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			}else if (epp.getName().equals("integers.vIntArray")) {
				totalParameters ++;
				Assert.assertEquals(int[].class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("integers.vIntArrayObj")) {
				totalParameters ++;
				Assert.assertEquals(Integer[].class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("integers.vIntListObj")) {
				totalParameters ++;
				Assert.assertEquals(List.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			}else if (epp.getName().equals("longs.vLong")) {
				totalParameters ++;
				Assert.assertEquals(long.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("longs.vLongObj")) {
				totalParameters ++;
				Assert.assertEquals(Long.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("longs.vLongArray")) {
				totalParameters ++;
				Assert.assertEquals(long[].class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("longs.vLongArrayObj")) {
				totalParameters ++;
				Assert.assertEquals(Long[].class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("longs.vLongListObj")) {
				totalParameters ++;
				Assert.assertEquals(List.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("testStr")) {
				totalParameters ++;
				Assert.assertEquals(String.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else {
				Assert.fail("Invalid parameter, " + epp.getName());
			}
		}
		
		Assert.assertEquals(12, totalParameters);
	}
	
	@Test
	public void testCyclic() throws Exception {
		List<EntryPoint> entryPoints = requestUtilsAnnotationsDisabled.retrieveAllExternalEntryPoints();
		EntryPoint ep = Iterables.find(entryPoints, new Predicate<EntryPoint>() {
			@Override
			public boolean apply(EntryPoint ep) {
				return ep.getMethodName().equals("testCyclicReference");
			}
		});
		
		Assert.assertTrue(ep.getUrls().contains("/testCyclicReference"));
		Assert.assertEquals(NotAnnotatedController.class, ep.getType());
		Assert.assertEquals("Not expecting any parameters", 0, ep.getParameters().size());
	}
	
	@Test
	public void testIgnore() throws Exception {
		List<EntryPoint> entryPoints = requestUtilsAnnotationsDisabled.retrieveAllExternalEntryPoints();
		EntryPoint ep = Iterables.find(entryPoints, new Predicate<EntryPoint>() {
			@Override
			public boolean apply(EntryPoint ep) {
				return ep.getMethodName().equals("testIgnoreObj");
			}
		});
		
		Assert.assertTrue(ep.getUrls().contains("/testIgnore"));
		Assert.assertEquals(NotAnnotatedController.class, ep.getType());
		Assert.assertEquals("Expecting one parameter", 1, ep.getParameters().size());

		EntryPointParameter epp = ep.getParameters().get(0);
		Assert.assertEquals("str", epp.getName());
		Assert.assertEquals(String.class, epp.getType());
		Assert.assertEquals(false, epp.isRequired());
		Assert.assertEquals("", epp.getDefaultValue());
	}
	
	@Test
	public void testIgnoreInside() throws Exception {
		List<EntryPoint> entryPoints = requestUtilsAnnotationsDisabled.retrieveAllExternalEntryPoints();
		EntryPoint ep = Iterables.find(entryPoints, new Predicate<EntryPoint>() {
			@Override
			public boolean apply(EntryPoint ep) {
				return ep.getMethodName().equals("testIgnoreInsideObj");
			}
		});
		
		Assert.assertTrue(ep.getUrls().contains("/testIgnoreInside"));
		Assert.assertEquals(NotAnnotatedController.class, ep.getType());
		Assert.assertEquals("Expecting one parameter", 1, ep.getParameters().size());

		EntryPointParameter epp = ep.getParameters().get(0);
		Assert.assertEquals("str", epp.getName());
		Assert.assertEquals(String.class, epp.getType());
		Assert.assertEquals(false, epp.isRequired());
		Assert.assertEquals("", epp.getDefaultValue());
	}
}
