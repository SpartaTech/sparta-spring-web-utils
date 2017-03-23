/**
 * Sparta Software Co.
 * 2017
 */
package org.sparta.springwebutils.property;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * Unit Tests for PropertiesLoaderBuilder and PropertiesLoaderBuilderFactory.
 * 
 * @author Daniel Conde Diehl - Sparta Technology
 *
 * History:
 *  Mar 22, 2017 - Daniel Conde Diehl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ContextTest.class)
public class PropertyLoaderBuilderTest {

	@Autowired
	private PropertiesLoaderBuilderFactory factory;
	
	@Test
	public void testPropertiesLoader() {
		Properties props = factory.getPropertiesBuilder()
								.addProperty("internal", "123")
								.loadPropertiesByPrefix("lib")
								.loadProperty("another-value")
								.loadProperty("non-existent")
								.loadPropertiesByPrefix("prefixed.", true)
								.build();
		Assert.assertEquals(6, props.size());
		Assert.assertEquals("123", props.getProperty("internal"));
		Assert.assertEquals("1", props.getProperty("lib.val1"));
		Assert.assertEquals("3", props.getProperty("lib.val2"));
		Assert.assertEquals("234", props.getProperty("another-value"));
		Assert.assertEquals("non-prefix-1", props.getProperty("removed1"));
		Assert.assertEquals("non-prefix-2", props.getProperty("removed2"));
	}
	
	@Test
	public void testPropertiesLoaderAll() {
		Properties props = factory.getPropertiesBuilder().loadAllPropertiesFromSpring().build();

		Assert.assertEquals("1", props.getProperty("lib.val1"));
		Assert.assertEquals("3", props.getProperty("lib.val2"));
		Assert.assertEquals("234", props.getProperty("another-value"));
		Assert.assertEquals("loader", props.getProperty("loader.val"));
		Assert.assertEquals("non-prefix-1", props.getProperty("prefixed.removed1"));
		Assert.assertEquals("non-prefix-2", props.getProperty("prefixed.removed2"));
	}
}
