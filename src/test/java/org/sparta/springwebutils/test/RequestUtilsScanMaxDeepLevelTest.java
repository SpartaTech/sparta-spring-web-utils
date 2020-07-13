package org.sparta.springwebutils.test;

import java.util.List;

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
 * @author Carlos Eduardo Endler Genz – Sparta Java Team 
 * 
 * History: 
 *    Mar 6, 2014 - Carlos Eduardo Endler Genz
 *  
 */ 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=MvcConfig.class)
@WebAppConfiguration
public class RequestUtilsScanMaxDeepLevelTest {
	
	@Autowired
	private RequestUtils requestUtilsMAxLevelDeep1;
	
	@Test
	public void testMaxDeepLevelPoints() {
		final List<EntryPoint> entryPoints = requestUtilsMAxLevelDeep1.retrieveAllExternalEntryPoints();
		
		Assert.assertNotNull(entryPoints);

		final EntryPoint ep = Iterables.find(entryPoints, new Predicate<EntryPoint>() {
			@Override
			public boolean apply(EntryPoint ep) {
				return ep.getMethodName().equals("testObject");
			}
		});
		Assert.assertTrue(ep.getUrls().contains("/testObject"));
		Assert.assertEquals(NotAnnotatedController.class, ep.getType());
		int totalParameters = 0;
		
		Assert.assertEquals(NotAnnotatedController.class, ep.getType());
		
		for (EntryPointParameter epp : ep.getParameters()) {
			if (epp.getName().equals("testStr")) {
				totalParameters ++;
				Assert.assertEquals(String.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("vInt")) {
				totalParameters ++;
				Assert.assertEquals(int.class, epp.getType());
				Assert.assertEquals(false, epp.isRequired());
				Assert.assertEquals("", epp.getDefaultValue());
			} else if (epp.getName().equals("$jacocoData")) {
				//Just ignore
			} else {
				Assert.fail("Invalid parameter, " + epp.getName());
			}
		}
		Assert.assertEquals(2, totalParameters);
	}
}
