/**
 * Sparta Software Co.
 * 2017
 */
package org.sparta.springwebutils.property;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Daniel Conde Diehl - Sparta Technology
 *
 * History:
 *  Mar 22, 2017 - Daniel Conde Diehl
 */
@Configuration
@PropertySource("classpath:test-properties.properties")
public class ContextTest {

	@Bean
	public PropertiesLoaderBuilderFactory factory() {
		return new PropertiesLoaderBuilderFactory();
	}
}
