package org.sparta.springwebutils;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.Manifest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ConstantConditions")
public class ManifestUtilsTest {

    @Test
    public void testGetManifestAttributeExplodedWarFound() {
        final ServletContext servletContext = mock(ServletContext.class);
        final ManifestUtils tested = new ManifestUtils();
        ReflectionTestUtils.setField(tested, "servletContext", servletContext);

        final URL mockManifest = getClass().getResource("/manifest-utils/META-INF/MANIFEST.MF");
        final String attributeName = "Name";
        when(servletContext.getRealPath("")).thenReturn(mockManifest.getPath().replaceFirst("META-INF/MANIFEST.MF", ""));
        final String value = tested.getManifestAttribute(attributeName);
        assertEquals("Mock-name", value);
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void testGetManifestAttributeNotFound() {
        final ServletContext servletContext = mock(ServletContext.class);
        final ManifestUtils tested = new ManifestUtils();
        ReflectionTestUtils.setField(tested, "servletContext", servletContext);

        final URL mockManifest = getClass().getResource("/META-INF/MANIFEST.MF");
        final String attributeName = "Other";
        when(servletContext.getRealPath("")).thenReturn(mockManifest.getPath().replaceFirst("META-INF/MANIFEST.MF", ""));
        final String value = tested.getManifestAttribute(attributeName);
        assertNull(value);
    }

    @Test
    public void testGetManifestAttributePackagedWarFound() throws MalformedURLException {
        final ServletContext servletContext = mock(ServletContext.class);
        final ManifestUtils tested = new ManifestUtils();
        ReflectionTestUtils.setField(tested, "servletContext", servletContext);

        final InputStream mockManifest = getClass().getResourceAsStream("/manifest-utils/META-INF/MANIFEST.MF");
        final URL mockManifestPath = getClass().getResource("/manifest-utils");
        final String attributeName = "Name";
        when(servletContext.getRealPath("")).thenThrow(new RuntimeException());
        when(servletContext.getResource("META-INF/MANIFEST.MF")).thenReturn(mockManifestPath);
        when(servletContext.getResourceAsStream("META-INF/MANIFEST.MF")).thenReturn(mockManifest);
        final String value = tested.getManifestAttribute(attributeName);
        assertEquals("Mock-name", value);
    }

    @Test
    public void testGetManifestAttributeClasspathFound() throws IOException {
        final ServletContext servletContext = mock(ServletContext.class);
        final ManifestUtils tested = new ManifestUtils();
        ReflectionTestUtils.setField(tested, "servletContext", servletContext);

        //Discover a value from the MANIFEST in the classloader, likely junit and assert it .
        final Manifest mf = new Manifest(getClass().getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF"));
        final String key = mf.getMainAttributes().keySet().iterator().next().toString();
        final String expectedValue = mf.getMainAttributes().getValue(key);

        final String value = tested.getManifestAttribute(key);
        assertEquals(expectedValue, value);
    }
}
