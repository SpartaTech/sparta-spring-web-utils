/*
 * Sparta Software Co.
 * 2017
 */
package org.sparta.springwebutils.test;

import com.google.common.collect.Iterables;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sparta.springwebutils.RequestUtils;
import org.sparta.springwebutils.config.MvcConfig;
import org.sparta.springwebutils.controller.NotAnnotatedController;
import org.sparta.springwebutils.entity.EntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Carlos Eduardo Endler Genz – Sparta Java Team
 * <p>
 * History:
 * - Mar 06, 2014 - Carlos Eduardo Endler Genz
 * - Oct 13, 2021 - Daniel Conde Diehl - Upgrading to Junit Jupiter
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MvcConfig.class)
@WebAppConfiguration
public class RequestUtilsScanEmptyBlackListTypeTest {

    @Autowired
    private RequestUtils requestUtilsClearParamTypeBlackList;

    @Test
    public void testExternalEntryPoints() {
        final List<EntryPoint> entryPoints = requestUtilsClearParamTypeBlackList.retrieveAllExternalEntryPoints();

        Assertions.assertNotNull(entryPoints);

        final EntryPoint ep = Iterables.find(entryPoints, ep1 -> ep1.getMethodName().equals("testEmptyIgnore"));

        assertEquals(NotAnnotatedController.class, ep.getType());
        Assertions.assertTrue(ep.getParameters().size() > 0, "Should have more than one parameter");
    }
}
