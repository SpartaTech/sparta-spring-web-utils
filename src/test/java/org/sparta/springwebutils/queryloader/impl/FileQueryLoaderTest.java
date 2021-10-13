package org.sparta.springwebutils.queryloader.impl;

import org.junit.jupiter.api.Test;
import org.sparta.springwebutils.queryloader.QueryLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for FileQueryLoader.
 *
 * @author Daniel Conde Diehl - Sparta Technology
 * <p>
 * History:
 * - Oct 13, 2021 - Daniel Conde Diehl
 */
public class FileQueryLoaderTest {

    private static final QueryLoader queryLoader = new FileQueryLoader("/file-query-loader/");

    @Test
    void testAfterPropertiesSet() {
        ((FileQueryLoader)queryLoader).afterPropertiesSet();
        //Expect no assertion exception to have happened.
    }

    @Test
    void testLoadQuerySuccess() {
        final String query = queryLoader.load("single-query");
        assertEquals("select 1 from dual", query);
    }

    @Test
    void testLoadQueryFileNotFound() {
        final IllegalStateException ex = assertThrows(IllegalStateException.class, () -> queryLoader.load("single-query-invalid"));
        assertEquals("Could not load query /file-query-loader/single-query-invalid", ex.getMessage());
    }

    @Test
    void testLoadQueryFileComposed() {
        final String query = queryLoader.load("composed-query");
        assertEquals("select (select 1 from dual)", query);
    }

    @Test
    void testLoadMultiple() {
        final String[] queries = queryLoader.loadMultiple("multiple-queries", '\n');
        assertEquals("select 1 from dual;", queries[0]);
        assertEquals("select 2 from dual;", queries[1]);
    }

    @Test
    void testLoadMultipleComposed() {
        final String[] queries = queryLoader.loadMultiple("multiple-composed", ';');
        assertEquals("select 1 from dual", queries[0]);
        assertEquals("select other from dual", queries[1]);
    }
}
