package org.sparta.springwebutils.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sparta.springwebutils.RequestUtils;
import org.sparta.springwebutils.config.MvcConfig;
import org.sparta.springwebutils.controller.NotAnnotatedController;
import org.sparta.springwebutils.entity.EntryPoint;
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
public class RequestUtilsScanEmptyBlackListTypeTest {
	
	@Autowired
	private RequestUtils requestUtilsClearParamTypeBlackList;
	
	@Test
	public void testExternalEntryPoints() {
		final List<EntryPoint> entryPoints = requestUtilsClearParamTypeBlackList.retrieveAllExternalEntryPoints();
		
		Assert.assertNotNull(entryPoints);
		
		final EntryPoint ep = Iterables.find(entryPoints, new Predicate<EntryPoint>() {
			@Override
			public boolean apply(EntryPoint ep) {
				return ep.getMethodName().equals("testEmptyIgnore");
			}
		});
		
		Assert.assertEquals(NotAnnotatedController.class, ep.getType());
		Assert.assertTrue("Should have more than one parameter", ep.getParameters().size() > 0);
	}
}
