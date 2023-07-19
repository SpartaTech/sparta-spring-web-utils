/*
 * Sparta Software Co.
 * 2017
 */
package org.sparta.springwebutils.property;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Unit Tests for PropertiesLoaderBuilder and PropertiesLoaderBuilderFactory.
 *
 * @author Daniel Conde Diehl - Sparta Technology
 * <p>
 * History:
 * - Mar 22, 2017 - Daniel Conde Diehl
 * - Oct 13, 2021 - Daniel Conde Diehl - Upgrading to Junit Jupiter
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=ContextTest.class)
public class PropertyLoaderBuilderTest {

	@Autowired
	@SuppressWarnings("unused")
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
		assertEquals(6, props.size());
		assertEquals("123", props.getProperty("internal"));
		assertEquals("1", props.getProperty("lib.val1"));
		assertEquals("3", props.getProperty("lib.val2"));
		assertEquals("234", props.getProperty("another-value"));
		assertEquals("non-prefix-1", props.getProperty("removed1"));
		assertEquals("non-prefix-2", props.getProperty("removed2"));
	}
	
	@Test
	public void testPropertiesLoaderAll() {
		Properties props = factory.getPropertiesBuilder().loadAllPropertiesFromSpring().build();

		assertEquals("1", props.getProperty("lib.val1"));
		assertEquals("3", props.getProperty("lib.val2"));
		assertEquals("234", props.getProperty("another-value"));
		assertEquals("loader", props.getProperty("loader.val"));
		assertEquals("non-prefix-1", props.getProperty("prefixed.removed1"));
		assertEquals("non-prefix-2", props.getProperty("prefixed.removed2"));
	}
}
