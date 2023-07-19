/*
 * Sparta Software Co.
 * 2017
 */
package org.sparta.springwebutils.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for class.
 *
 * @author Daniel Conde Diehl - Sparta Technology
 * <p>
 * History:
 * - Jan 18, 2017 - Daniel Conde Diehl
 * - Oct, 21,2021 - Daniel Conde Diehl - Adjusting tests to not user Jmockit
 */
@ExtendWith(MockitoExtension.class)
public class SpartaNamedParameterJdbcTemplateTest {

    private static final RowMapper<String> DEFAULT_ROW_MAPPER  = (rs, rowNum) -> rs.getString(1);

    private static final DataSource dataSource;
    private static final SpartaNamedParameterJdbcTemplate tested;
    static {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:");
        config.setUsername("sa");
        config.setPassword("");
        dataSource = new HikariDataSource(config);
        tested = new SpartaNamedParameterJdbcTemplate(dataSource);

        //Init database
        final SpartaJdbcTemplate spartaJdbcTemplate = new SpartaJdbcTemplate(dataSource);
        spartaJdbcTemplate.execute("create table test ( id int, val varchar(2000))");
        spartaJdbcTemplate.execute("insert into test values (1, 'test')");
        spartaJdbcTemplate.execute("insert into test values (2, 'test 2')");
        spartaJdbcTemplate.execute("insert into test values (3, null)");
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

    @Test
    public void testInitWithJdbcOperations() {
        final SpartaNamedParameterJdbcTemplate t2 = new SpartaNamedParameterJdbcTemplate(tested.getJdbcOperations());

        assertEquals(tested.getJdbcOperations(), t2.getJdbcOperations());
    }

    /* ***** BEGIN: TEST FOR SQL, CLASS, paramMap ******* */
    @Test
    public void testQueryForOptionalObjectParamMapNull() {
        final String sql = "select val from test where id = :vId";
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("vId", 3);

        final Optional<String> actual = tested.queryForOptionalObject(sql, paramMap, String.class);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalObjectParamMapWithValue() {
        final String sql = "select val from test where id = :vId1 or id = :vId2";
        final String expected = "test";
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("vId1", 1);
        paramMap.put("vId2", -1);

        final Optional<String> actual = tested.queryForOptionalObject(sql, paramMap, String.class);
        assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
    }

    @Test
    public void testQueryForOptionalObjectArgsEmptyResultSet() {
        final String sql = "select val from test where id = :vId";
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("vId", -1);

        final Optional<String> actual = tested.queryForOptionalObject(sql, paramMap, String.class);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalArgsMultipleRows() {
        final String sql = "select val from test where id between :vId1 and :vId2 ";
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("vId1", 1);
        paramMap.put("vId2", 10);

        assertThrows(IncorrectResultSizeDataAccessException.class, () -> tested.queryForOptionalObject(sql, paramMap, String.class));
    }
    /* ***** END: TEST FOR SQL, CLASS, Args ******* */

    /* ***** BEGIN: TEST FOR SQL, Args, CLASS ******* */
    @Test
    public void testQueryForOptionalObjectParamSourceNull() {
        final String sql = "select val from test where id = :vId";
        final MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("vId", 3);

        final Optional<String> actual = tested.queryForOptionalObject(sql, paramSource, String.class);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalObjectArgsArrayWithValue() {
        final String sql = "select val from test where id = :vId1 or id = :vId2";
        final String expected = "test";
        final MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("vId1", 1);
        paramSource.addValue("vId2", -1);

        final Optional<String> actual = tested.queryForOptionalObject(sql, paramSource, String.class);
        assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
    }

    @Test
    public void testQueryForOptionalObjectArgsArrayEmptyResultSet() {
        final String sql = "select val from test where id = :vId";
        final MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("vId", -1);

        final Optional<String> actual = tested.queryForOptionalObject(sql, paramSource, String.class);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalArgsArrayMultipleRows() {
        final String sql = "select val from test where id between :vId1 and :vId2 ";
        final MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("vId1", 1);
        paramSource.addValue("vId2", 10);

        assertThrows(IncorrectResultSizeDataAccessException.class, () -> tested.queryForOptionalObject(sql, paramSource, String.class));
    }
    /* ***** END: TEST FOR SQL, Args, CLASS ******* */

    /* ***** BEGIN: TEST FOR SQL, Args, Types, CLASS ******* */
    @Test
    public void testQueryForOptionalObjectArgsArrayTypesNull() {
        final String sql = "select val from test where id = :vId";
        final Map<String, Object> params = new HashMap<>();
        params.put("vId", 3);

        final Optional<String> actual = tested.queryForOptionalObject(sql, params, String.class);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalObjectArgsArrayTypesWithValue() {
        final String sql = "select val from test where id = :vId1 or id = :vId2";
        final String expected = "test";
        final Map<String, Object> params = new HashMap<>();
        params.put("vId1", 1);
        params.put("vId2", -1);

        final Optional<String> actual = tested.queryForOptionalObject(sql, params, String.class);
        assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
    }

    @Test
    public void testQueryForOptionalObjectArgsArrayTypesEmptyResultSet() {
        final String sql = "select val from test where id = :vId";
        final Map<String, Object> params = new HashMap<>();
        params.put("vId", -1);

        final Optional<String> actual = tested.queryForOptionalObject(sql, params, String.class);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalArgsArrayTypesMultipleRows() {
        final String sql = "select val from test where id between :vId1 and :vId2 ";
        final Map<String, Object> params = new HashMap<>();
        params.put("vId1", 1);
        params.put("vId2", 10);

        assertThrows(IncorrectResultSizeDataAccessException.class, () -> tested.queryForOptionalObject(sql, params, String.class));
    }
    /* ***** END: TEST FOR SQL, Args, Types, CLASS ******* */

    /* ***** BEGIN: sql, params, types, rowMapper ***** */
    @Test
    public void testQueryForOptionalObjectRowMapperParamsTypesNull() {
        final String sql = "select val from test where id = :vId";
        final Map<String, Object> params = new HashMap<>();
        params.put("vId", 3);

        final Optional<String> actual = tested.queryForOptionalObject(sql, params, DEFAULT_ROW_MAPPER);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalRowMapperParamsTypesObjectWithValue() {
        final String sql = "select val from test where id = :vId";
        final String expected = "test";
        final Map<String, Object> params = new HashMap<>();
        params.put("vId", 1);

        final Optional<String> actual = tested.queryForOptionalObject(sql, params, DEFAULT_ROW_MAPPER);
        assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
    }

    @Test
    public void testQueryForOptionalRowMapperParamsTypesObjectEmptyResultSet() {
        final String sql = "select val from test where id = :vId";
        final Map<String, Object> params = new HashMap<>();
        params.put("vId", -1);

        final Optional<String> actual = tested.queryForOptionalObject(sql, params, DEFAULT_ROW_MAPPER);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalRowMapperParamsTypesObjectMultipleRows() {
        final String sql = "select val from test where id between :vId1 and :vId2";
        final Map<String, Object> params = new HashMap<>();
        params.put("vId1", 1);
        params.put("vId2", 10);

        assertThrows(IncorrectResultSizeDataAccessException.class, () -> tested.queryForOptionalObject(sql, params, DEFAULT_ROW_MAPPER));
    }
    /* ***** END: sql, params, types, rowMapper ***** */

    /* ***** BEGIN: sql, params, rowMapper ***** */
    @Test
    public void testQueryForOptionalObjectRowMapperParamsNull() {
        final String sql = "select val from test where id = :vId";
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("vId", 3);

        final Optional<String> actual = tested.queryForOptionalObject(sql, params, DEFAULT_ROW_MAPPER);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalRowMapperParamsObjectWithValue() {
        final String sql = "select val from test where id = :vId";
        final String expected = "test";
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("vId", 1);

        final Optional<String> actual = tested.queryForOptionalObject(sql, params, DEFAULT_ROW_MAPPER);
        assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
    }

    @Test
    public void testQueryForOptionalRowMapperParamsObjectEmptyResultSet() {
        final String sql = "select val from test where id = :vId";
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("vId", -1);

        final Optional<String> actual = tested.queryForOptionalObject(sql, params, DEFAULT_ROW_MAPPER);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testQueryForOptionalRowMapperParamsObjectMultipleRows() {
        final String sql = "select val from test where id between :vId1 and :vId2";
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("vId1", 1);
        params.addValue("vId2", 10);

        assertThrows(IncorrectResultSizeDataAccessException.class, () -> tested.queryForOptionalObject(sql, params, DEFAULT_ROW_MAPPER));
    }
    /* ***** END: sql, rowMapper, params ***** */
}
