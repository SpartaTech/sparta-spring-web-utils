/**
 * Sparta Software Co.
 * 2017
 */
package org.sparta.springwebutils.property;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Daniel Conde Diehl - Sparta Technology
 *
 * History:
 *  Mar 22, 2017 - Daniel Conde Diehl
 */
@Configuration
@ComponentScan(basePackages="org.sparta.springwebutils.property")
@PropertySource("classpath:test-properties.properties")
public class ContextTest {

}
