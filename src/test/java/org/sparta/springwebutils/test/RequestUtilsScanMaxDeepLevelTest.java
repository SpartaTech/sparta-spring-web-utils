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
import org.sparta.springwebutils.controller.NotAnnotatedController;
import org.sparta.springwebutils.entity.EntryPoint;
import org.sparta.springwebutils.entity.EntryPointParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Carlos Eduardo Endler Genz – Sparta Java Team
 * <p>
 * History:
 * - Mar 06, 2014 - Carlos Eduardo Endler Genz
 * - Oct 13, 2021 - Daniel Conde Diehl - Upgrading to Junit Jupiter
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MvcConfig.class)
@WebAppConfiguration
public class RequestUtilsScanMaxDeepLevelTest {
	
	@Autowired
    @SuppressWarnings("unused")
	private RequestUtils requestUtilsMAxLevelDeep1;

	@Test
	public void testMaxDeepLevelPoints() {
		final List<EntryPoint> entryPoints = requestUtilsMAxLevelDeep1.retrieveAllExternalEntryPoints();
		
		assertNotNull(entryPoints);

        final EntryPoint ep = Iterables.find(entryPoints, ep1 -> ep1.getMethodName().equals("testObject"));
        assertTrue(ep.getUrls().contains("/testObject"));
        assertEquals(NotAnnotatedController.class, ep.getType());
        int totalParameters = 0;

        assertEquals(NotAnnotatedController.class, ep.getType());

        for (EntryPointParameter epp : ep.getParameters()) {
            if (epp.getName().equals("testStr")) {
                totalParameters++;
                assertEquals(String.class, epp.getType());
                assertFalse(epp.isRequired());
                assertEquals("", epp.getDefaultValue());
            } else if (epp.getName().equals("vInt")) {
                totalParameters++;
                assertEquals(int.class, epp.getType());
                assertFalse(epp.isRequired());
                assertEquals("", epp.getDefaultValue());
            } else if (epp.getName().equals("$jacocoData")) {
                //Just ignore
            } else {
                fail("Invalid parameter, " + epp.getName());
            }
        }
        assertEquals(2, totalParameters);
    }
}
