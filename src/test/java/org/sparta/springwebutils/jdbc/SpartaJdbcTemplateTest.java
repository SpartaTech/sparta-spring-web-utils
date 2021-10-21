/*
 * Sparta Software Co.
 * 2021
 */
package org.sparta.springwebutils.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for class.
 *
 * @author Daniel Conde Diehl - Sparta Technology
 * <p>
 * History:
 * - Jan 18, 2017 - Daniel Conde Diehl
 * - Feb 02, 2017 - Daniel Conde Diehl - Adding tests for multiple rows issue.EmptyResultDataAccessException
 * - Oct 13, 2021 - Daniel Conde Diehl - Removing Jmockit as it is not compliant with latest JDKs
 */
@ExtendWith(MockitoExtension.class)
public class SpartaJdbcTemplateTest {

    private static final RowMapper<String> DEFAULT_ROW_MAPPER  = (rs, rowNum) -> rs.getString(1);

    private static final DataSource dataSource;
    private static final SpartaJdbcTemplate tested;
    static {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:");
        config.setUsername("sa");
        config.setPassword("");
        dataSource = new HikariDataSource(config);
        tested = new SpartaJdbcTemplate(dataSource);

        //Init database
        tested.execute("create table test ( id int, val varchar(2000))");
        tested.execute("insert into test values (1, 'test')");
        tested.execute("insert into test values (2, 'test 2')");
        tested.execute("insert into test values (3, null)");
    }

    @Test
    public void testInitWithJdbcDataSource() {
        final SpartaJdbcTemplate t1 = new SpartaJdbcTemplate(dataSource);
        final DataSource actual = (DataSource) ReflectionTestUtils.getField(t1, "dataSource");
        assertEquals(dataSource, actual);
    }

    @Test
    public void testInitWithJdbcDataSourceAndLazy() {
        final SpartaJdbcTemplate t1 = new SpartaJdbcTemplate(dataSource, true);
        final DataSource actual = (DataSource) ReflectionTestUtils.getField(t1, "dataSource");
        assertEquals(dataSource, actual);
        final Boolean lazy = (Boolean) ReflectionTestUtils.getField(t1, "lazyInit");
        assertEquals(Boolean.TRUE, lazy);
    }

    @Test
    public void testInitNoParameters() {
        new SpartaJdbcTemplate();
    }

    /* ***** BEGIN: TEST FOR SQL, CLASS *********** */
    @Test
    public void testQueryForOptionalObjectNull() {
        final String sql = "select val from test where id = 3";

        final Optional<String> actual = tested.queryForOptionalObject(sql, String.class);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalObjectWithValue() {
        final String sql = "select val from test where id = 1";
        final String expected = "test";

        final Optional<String> actual = tested.queryForOptionalObject(sql, String.class);
        assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
    }

    @Test
    public void testQueryForOptionalObjectEmptyResultSet() {
        final String sql = "select val from test where id = -1";

        final Optional<String> actual = tested.queryForOptionalObject(sql, String.class);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalMultipleRows() {
        final String sql = "select val from test";

        assertThrows(IncorrectResultSizeDataAccessException.class, () -> tested.queryForOptionalObject(sql, String.class));
    }
    /* ***** END: TEST FOR SQL, CLASS ******* */

    /* ***** BEGIN: TEST FOR SQL, CLASS, Args ******* */
    @Test
    public void testQueryForOptionalObjectArgsNull() {
        final String sql = "select val from test where id = ?";

        final Optional<String> actual = tested.queryForOptionalObject(sql, String.class, 3);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalObjectArgsWithValue() {
        final String sql = "select val from test where id = ? or id = ?";
        final String expected = "test";

        final Optional<String> actual = tested.queryForOptionalObject(sql, String.class, 1, -1);
        assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
    }

    @Test
    public void testQueryForOptionalObjectArgsEmptyResultSet() {
        final String sql = "select val from test where id = ?";

        final Optional<String> actual = tested.queryForOptionalObject(sql, String.class, -1);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalArgsMultipleRows() {
        final String sql = "select val from test where id between ? and ? ";

        assertThrows(IncorrectResultSizeDataAccessException.class, () -> tested.queryForOptionalObject(sql, String.class, 1, 10));
    }
    /* ***** END: TEST FOR SQL, CLASS, Args ******* */

    /* ***** BEGIN: TEST FOR SQL, Args, CLASS ******* */
    @Test
    public void testQueryForOptionalObjectArgsArrayNull() {
        final String sql = "select val from test where id = ?";

        final Optional<String> actual = tested.queryForOptionalObject(sql, new Object[]{3}, String.class);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalObjectArgsArrayWithValue() {
        final String sql = "select val from test where id = ? or id = ?";
        final String expected = "test";

        final Optional<String> actual = tested.queryForOptionalObject(sql, new Object[]{-1, 1}, String.class);
        assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
    }

    @Test
    public void testQueryForOptionalObjectArgsArrayEmptyResultSet() {
        final String sql = "select val from test where id = ?";

        final Optional<String> actual = tested.queryForOptionalObject(sql, new Object[]{-1}, String.class);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalArgsArrayMultipleRows() {
        final String sql = "select val from test where id between ? and ? ";

        assertThrows(IncorrectResultSizeDataAccessException.class, () -> tested.queryForOptionalObject(sql, new Object[]{1, 10}, String.class));
    }
    /* ***** END: TEST FOR SQL, Args, CLASS ******* */

    /* ***** BEGIN: TEST FOR SQL, Args, Types, CLASS ******* */
    @Test
    public void testQueryForOptionalObjectArgsArrayTypesNull() {
        final String sql = "select val from test where id = ?";
        final Object[] params = new Object[]{3};
        final int[] types = new int[]{Types.INTEGER};

        final Optional<String> actual = tested.queryForOptionalObject(sql, params, types, String.class);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalObjectArgsArrayTypesWithValue() {
        final String sql = "select val from test where id = ? or id = ?";
        final String expected = "test";
        final Object[] params = new Object[]{-1, 1};
        final int[] types = new int[]{Types.INTEGER, Types.INTEGER};

        final Optional<String> actual = tested.queryForOptionalObject(sql, params, types, String.class);
        assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
    }

    @Test
    public void testQueryForOptionalObjectArgsArrayTypesEmptyResultSet() {
        final String sql = "select val from test where id = ?";
        final Object[] params = new Object[]{-1};
        final int[] types = new int[]{Types.INTEGER};

        final Optional<String> actual = tested.queryForOptionalObject(sql, params, types, String.class);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalArgsArrayTypesMultipleRows() {
        final String sql = "select val from test where id between ? and ? ";
        final Object[] params = new Object[]{1, 10};
        final int[] types = new int[]{Types.INTEGER, Types.INTEGER};

        assertThrows(IncorrectResultSizeDataAccessException.class, () -> tested.queryForOptionalObject(sql, params, types, String.class));
    }
    /* ***** END: TEST FOR SQL, Args, Types, CLASS ******* */

	/* ***** BEGIN: sql, rowMapper ***** */
    @Test
    public void testQueryForOptionalObject2Null() {
        final String sql = "select val from test where id = 3";

        final Optional<String> actual = tested.queryForOptionalObject(sql, DEFAULT_ROW_MAPPER);
        assertFalse(actual.isPresent());
    }

	@Test
	public void testQueryForOptional2ObjectWithValue() {
		final String sql = "select val from test where id = 1";
		final String expected = "test";

		final Optional<String> actual = tested.queryForOptionalObject(sql, DEFAULT_ROW_MAPPER);
		assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
	}

	@Test
	public void testQueryForOptional2ObjectEmptyResultSet() {
		final String sql = "select val from test where id = -1";

		final Optional<String> actual = tested.queryForOptionalObject(sql, DEFAULT_ROW_MAPPER);
		assertFalse(actual.isPresent());
	}

	@Test
	public void testQueryForOptional2ObjectMultipleRows() {
		final String sql = "select val from test";

		assertThrows(IncorrectResultSizeDataAccessException.class, () -> tested.queryForOptionalObject(sql, DEFAULT_ROW_MAPPER));
	}
	/* ***** END: sql, rowMapper ***** */

    /* ***** BEGIN: sql, params, types, rowMapper ***** */
    @Test
    public void testQueryForOptionalObjectRowMapperParamsTypesNull() {
        final String sql = "select val from test where id = ?";
        final Object[] params = new Object[]{-1};
        final int[] types = new int[]{Types.INTEGER};

        final Optional<String> actual = tested.queryForOptionalObject(sql, params, types, DEFAULT_ROW_MAPPER);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalRowMapperParamsTypesObjectWithValue() {
        final String sql = "select val from test where id = ?";
        final String expected = "test";
        final Object[] params = new Object[]{1};
        final int[] types = new int[]{Types.INTEGER};

        final Optional<String> actual = tested.queryForOptionalObject(sql, params, types, DEFAULT_ROW_MAPPER);
        assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
    }

    @Test
    public void testQueryForOptionalRowMapperParamsTypesObjectEmptyResultSet() {
        final String sql = "select val from test where id = ?";
        final Object[] params = new Object[]{-1};
        final int[] types = new int[]{Types.INTEGER};

        final Optional<String> actual = tested.queryForOptionalObject(sql, params, types, DEFAULT_ROW_MAPPER);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalRowMapperParamsTypesObjectMultipleRows() {
        final String sql = "select val from test where id between ? and ?";
        final Object[] params = new Object[]{1, 10};
        final int[] types = new int[]{Types.INTEGER, Types.INTEGER};

        assertThrows(IncorrectResultSizeDataAccessException.class, () -> tested.queryForOptionalObject(sql, params, types, DEFAULT_ROW_MAPPER));
    }
    /* ***** END: sql, params, types, rowMapper ***** */

    /* ***** BEGIN: sql, params, rowMapper ***** */
    @Test
    public void testQueryForOptionalObjectRowMapperParamsNull() {
        final String sql = "select val from test where id = ?";
        final Object[] params = new Object[]{-1};

        final Optional<String> actual = tested.queryForOptionalObject(sql, params, DEFAULT_ROW_MAPPER);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalRowMapperParamsObjectWithValue() {
        final String sql = "select val from test where id = ?";
        final String expected = "test";
        final Object[] params = new Object[]{1};

        final Optional<String> actual = tested.queryForOptionalObject(sql, params, DEFAULT_ROW_MAPPER);
        assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
    }

    @Test
    public void testQueryForOptionalRowMapperParamsObjectEmptyResultSet() {
        final String sql = "select val from test where id = ?";
        final Object[] params = new Object[]{-1};

        final Optional<String> actual = tested.queryForOptionalObject(sql, params, DEFAULT_ROW_MAPPER);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalRowMapperParamsObjectMultipleRows() {
        final String sql = "select val from test where id between ? and ?";
        final Object[] params = new Object[]{1, 10};

        assertThrows(IncorrectResultSizeDataAccessException.class, () -> tested.queryForOptionalObject(sql, params, DEFAULT_ROW_MAPPER));
    }
    /* ***** END: sql, rowMapper, params ***** */

    /* ***** BEGIN: sql, params, rowMapper ***** */
    @Test
    public void testQueryForOptionalObjectRowMapperParamsLastNull() {
        final String sql = "select val from test where id = ?";
        final Object[] params = new Object[]{-1};

        final Optional<String> actual = tested.queryForOptionalObject(sql, DEFAULT_ROW_MAPPER, params);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalRowMapperParamsLastObjectWithValue() {
        final String sql = "select val from test where id = ?";
        final String expected = "test";

        final Optional<String> actual = tested.queryForOptionalObject(sql, DEFAULT_ROW_MAPPER, 1);
        assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
    }

    @Test
    public void testQueryForOptionalRowMapperParamsLastObjectEmptyResultSet() {
        final String sql = "select val from test where id = ?";

        final Optional<String> actual = tested.queryForOptionalObject(sql, DEFAULT_ROW_MAPPER, -1);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalRowMapperParamsLastObjectMultipleRows() {
        final String sql = "select val from test where id between ? and ?";

        assertThrows(IncorrectResultSizeDataAccessException.class, () -> tested.queryForOptionalObject(sql, DEFAULT_ROW_MAPPER, 1, 10));
    }
    /* ***** END: sql, rowMapper, params ***** */
}
