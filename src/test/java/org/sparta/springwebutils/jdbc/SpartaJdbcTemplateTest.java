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
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Optional;
import java.util.logging.Logger;

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



//	/* ***** BEGIN: sql, class, params ******* */
//	@Test
//	public void testQueryForOptionalObject3Null () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		new Expectations(tested) {{
//			tested.queryForObject(sql, String.class, params);
//			result = null;
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, String.class, params);
//		assertFalse(actual.isPresent());
//	}
//
//	@Test
//	public void testQueryForOptionalObject3WithValue () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		final String expected = "test";
//		new Expectations(tested) {{
//			tested.queryForObject(sql, String.class, params);
//			result = expected;
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, String.class, params);
//		assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
//	}
//
//	@Test
//	public void testQueryForOptionalObject3MoreThanOneValue () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		new Expectations(tested) {{
//			tested.queryForObject(sql, String.class, params);
//			result = new EmptyResultDataAccessException(1);
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, String.class, params);
//		assertFalse(actual.isPresent());
//	}
//
//	@Test(expected=IncorrectResultSizeDataAccessException.class)
//	public void testQueryForOptionalObject3MultipleRows () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		new Expectations(tested) {{
//			tested.queryForObject(sql, String.class, params);
//			result = new IncorrectResultSizeDataAccessException(1);
//		}};
//
//		tested.queryForOptionalObject(sql, String.class, params);
//	}
//	/* ***** END: sql, class, params ******* */
//
//
//
//
//	/* ***** BEGIN: sql, params, class ******* */
//	@Test
//	public void testQueryForOptionalObject4Null () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, String.class);
//			result = null;
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, String.class);
//		assertFalse(actual.isPresent());
//	}
//
//	@Test
//	public void testQueryForOptionalObject4WithValue () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		final String expected = "test";
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, String.class);
//			result = expected;
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, String.class);
//		assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
//	}
//
//	@Test
//	public void testQueryForOptionalObject4MoreThanOneValue () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, String.class);
//			result = new EmptyResultDataAccessException(1);
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, String.class);
//		assertFalse(actual.isPresent());
//	}
//
//	@Test(expected=IncorrectResultSizeDataAccessException.class)
//	public void testQueryForOptionalObject4MultipleRows () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, String.class);
//			result = new IncorrectResultSizeDataAccessException(1);
//		}};
//
//		tested.queryForOptionalObject(sql, params, String.class);
//	}
//	/* ***** END: sql, params, class ******* */
//
//
//
//
//
//	/* ***** BEGIN: sql, params, rowMapper ******* */
//	@Test
//	public void testQueryForOptionalObject5Null () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		final RowMapper<String> rowMapper = new RowMapper<String>() {
//			@Override
//			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//				return "teste";
//			}
//		};
//
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, rowMapper);
//			result = null;
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, rowMapper);
//		assertFalse(actual.isPresent());
//	}
//
//	@Test
//	public void testQueryForOptionalObject5WithValue () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		final String expected = "test";
//
//		final RowMapper<String> rowMapper = new RowMapper<String>() {
//			@Override
//			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//				return "teste";
//			}
//		};
//
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, rowMapper);
//			result = expected;
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, rowMapper);
//		assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
//	}
//
//	@Test
//	public void testQueryForOptionalObject5MoreThanOneValue () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		final RowMapper<String> rowMapper = new RowMapper<String>() {
//			@Override
//			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//				return "teste";
//			}
//		};
//
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, rowMapper);
//			result = new EmptyResultDataAccessException(1);
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, rowMapper);
//		assertFalse(actual.isPresent());
//	}
//
//	@Test(expected=IncorrectResultSizeDataAccessException.class)
//	public void testQueryForOptionalObject5MultipleRows () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		final RowMapper<String> rowMapper = new RowMapper<String>() {
//			@Override
//			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//				return "teste";
//			}
//		};
//
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, rowMapper);
//			result = new IncorrectResultSizeDataAccessException(1);
//		}};
//
//		tested.queryForOptionalObject(sql, params, rowMapper);
//	}
//	/* ***** END: sql, params, rowMapper ******* */
//
//
//
//
//
//	/* ***** BEGIN: sql, rowMapper, params ******* */
//	@Test
//	public void testQueryForOptionalObject6Null () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		final RowMapper<String> rowMapper = new RowMapper<String>() {
//			@Override
//			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//				return "teste";
//			}
//		};
//
//		new Expectations(tested) {{
//			tested.queryForObject(sql, rowMapper, params);
//			result = null;
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, rowMapper, params);
//		assertFalse(actual.isPresent());
//	}
//
//	@Test
//	public void testQueryForOptionalObject6WithValue () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		final String expected = "test";
//
//		final RowMapper<String> rowMapper = new RowMapper<String>() {
//			@Override
//			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//				return "teste";
//			}
//		};
//
//		new Expectations(tested) {{
//			tested.queryForObject(sql, rowMapper, params);
//			result = expected;
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, rowMapper, params);
//		assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
//	}
//
//	@Test
//	public void testQueryForOptionalObject6MoreThanOneValue () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		final RowMapper<String> rowMapper = new RowMapper<String>() {
//			@Override
//			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//				return "teste";
//			}
//		};
//
//		new Expectations(tested) {{
//			tested.queryForObject(sql, rowMapper, params);
//			result = new EmptyResultDataAccessException(1);
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, rowMapper, params);
//		assertFalse(actual.isPresent());
//	}
//
//	@Test(expected=IncorrectResultSizeDataAccessException.class)
//	public void testQueryForOptionalObject6MultipleRows () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		final RowMapper<String> rowMapper = new RowMapper<String>() {
//			@Override
//			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//				return "teste";
//			}
//		};
//
//		new Expectations(tested) {{
//			tested.queryForObject(sql, rowMapper, params);
//			result = new IncorrectResultSizeDataAccessException(1);
//		}};
//
//		tested.queryForOptionalObject(sql, rowMapper, params);
//	}
//	/* ***** END: sql, rowMapper, params ******* */
//
//
//
//
//
//
//
//
//	/* ***** BEGIN: sql, params, paramTypes, rowMapper ******* */
//	@Test
//	public void testQueryForOptionalObject7Null () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		final int[] paramType = new int[1];
//		final RowMapper<String> rowMapper = new RowMapper<String>() {
//			@Override
//			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//				return "teste";
//			}
//		};
//
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, paramType, rowMapper);
//			result = null;
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, paramType, rowMapper);
//		assertFalse(actual.isPresent());
//	}
//
//	@Test
//	public void testQueryForOptionalObject7WithValue () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		final int[] paramType = new int[1];
//		final String expected = "test";
//
//		final RowMapper<String> rowMapper = new RowMapper<String>() {
//			@Override
//			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//				return "teste";
//			}
//		};
//
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, paramType, rowMapper);
//			result = expected;
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, paramType, rowMapper);
//		assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
//	}
//
//	@Test
//	public void testQueryForOptionalObject7MoreThanOneValue () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		final int[] paramType = new int[1];
//		final RowMapper<String> rowMapper = new RowMapper<String>() {
//			@Override
//			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//				return "teste";
//			}
//		};
//
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, paramType, rowMapper);
//			result = new EmptyResultDataAccessException(1);
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, paramType, rowMapper);
//		assertFalse(actual.isPresent());
//	}
//	/* ***** END: sql, params, paramTypes, rowMapper ******* */
//
//
//
//	/* ***** BEGIN: sql, params, paramTypes, class ******* */
//	@Test
//	public void testQueryForOptionalObject8Null () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		final int[] paramType = new int[1];
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, paramType, String.class);
//			result = null;
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, paramType, String.class);
//		assertFalse(actual.isPresent());
//	}
//
//	@Test
//	public void testQueryForOptionalObject8WithValue () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		final int[] paramType = new int[1];
//		final String expected = "test";
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, paramType, String.class);
//			result = expected;
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, paramType, String.class);
//		assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
//	}
//
//	@Test
//	public void testQueryForOptionalObject8MoreThanOneValue () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		final int[] paramType = new int[1];
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, paramType, String.class);
//			result = new EmptyResultDataAccessException(1);
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, paramType, String.class);
//		assertFalse(actual.isPresent());
//	}
//
//	@Test(expected=IncorrectResultSizeDataAccessException.class)
//	public void testQueryForOptionalObject8MultipleRows () throws Exception {
//		final String sql = "";
//		final Object[] params = new Object[1];
//		final int[] paramType = new int[1];
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, paramType, String.class);
//			result = new IncorrectResultSizeDataAccessException(1);
//		}};
//
//		tested.queryForOptionalObject(sql, params, paramType, String.class);
//	}
    /* ***** END: sql, params, paramTypes, class ******* */

    private class MockDataSource implements DataSource {

        @Override
        public Connection getConnection() throws SQLException {
            return null;
        }

        @Override
        public Connection getConnection(String username, String password) throws SQLException {
            return null;
        }

        @Override
        public PrintWriter getLogWriter() throws SQLException {
            return null;
        }

        @Override
        public void setLogWriter(PrintWriter out) throws SQLException {

        }

        @Override
        public void setLoginTimeout(int seconds) throws SQLException {

        }

        @Override
        public int getLoginTimeout() throws SQLException {
            return 0;
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return null;
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return null;
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return false;
        }
    }
}
