/**
 * Sparta Software Co.
 * 2017
 */
package org.sparta.springwebutils.jdbc;

/**
 * Unit tests for class.
 * 
 * @author Daniel Conde Diehl - Sparta Technology
 *
 * History:
 *  Jan 18, 2017 - Daniel Conde Diehl
 */

public class SpartaNamedParameterJdbcTemplateTest {

//	@Tested
//	private SpartaNamedParameterJdbcTemplate tested;
//
//	@Injectable
//	private DataSource dataSource;
//
//	@Injectable
//	private JdbcOperations jdbcOperations;
//
//	@Test
//	public void testInitWithJdbcDataSource() {
//		SpartaNamedParameterJdbcTemplate t1 = new SpartaNamedParameterJdbcTemplate(dataSource);
//		JdbcTemplate actual = Deencapsulation.getField(t1, "classicJdbcTemplate");
//		Assert.assertEquals(dataSource, actual.getDataSource());
//	}
//
//	@Test
//	public void testInitWithJdbcOperations() {
//		SpartaNamedParameterJdbcTemplate t1 = new SpartaNamedParameterJdbcTemplate(jdbcOperations);
//		JdbcOperations actual = Deencapsulation.getField(t1, "classicJdbcTemplate");
//		Assert.assertEquals(jdbcOperations, actual);
//
//	}
//
//
//	/* ***** BEGIN: sql, params, class ****** */
//	@Test
//	public void testQueryForOptionalObjectNull () throws Exception {
//		final String sql = "";
//		final SqlParameterSource params = new MapSqlParameterSource();
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, String.class);
//			result = null;
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, String.class);
//		Assert.assertFalse(actual.isPresent());
//	}
//
//	@Test
//	public void testQueryForOptionalObjectWithValue () throws Exception {
//		final String sql = "";
//		final SqlParameterSource params = new MapSqlParameterSource();
//		final String expected = "test";
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, String.class);
//			result = expected;
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, String.class);
//		Assert.assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
//	}
//
//	@Test
//	public void testQueryForOptionalObjectMoreThanOneValue () throws Exception {
//		final String sql = "";
//		final SqlParameterSource params = new MapSqlParameterSource();
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, String.class);
//			result = new EmptyResultDataAccessException(1);
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, String.class);
//		Assert.assertFalse(actual.isPresent());
//	}
//
//	@Test(expected=IncorrectResultSizeDataAccessException.class)
//	public void testQueryForOptionalObjectMultipleRows () throws Exception {
//		final String sql = "";
//		final SqlParameterSource params = new MapSqlParameterSource();
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, String.class);
//			result = new IncorrectResultSizeDataAccessException(1);
//		}};
//
//		tested.queryForOptionalObject(sql, params, String.class);
//	}
//	/* ***** END: sql, params, class ****** */
//
//
//
//
//
//
//	/* ***** BEGIN: sql, params, rowMapper ****** */
//	@Test
//	public void testQueryForOptionalObject2Null () throws Exception {
//		final String sql = "";
//		final SqlParameterSource params = new MapSqlParameterSource();
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
//		Assert.assertFalse(actual.isPresent());
//	}
//
//	@Test
//	public void testQueryForOptional2ObjectWithValue () throws Exception {
//		final String sql = "";
//		final SqlParameterSource params = new MapSqlParameterSource();
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
//		Assert.assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
//	}
//
//	@Test
//	public void testQueryForOptional2ObjectMoreThanOneValue () throws Exception {
//		final String sql = "";
//		final SqlParameterSource params = new MapSqlParameterSource();
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
//			result = new EmptyResultDataAccessException(1);
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, rowMapper);
//		Assert.assertFalse(actual.isPresent());
//	}
//	/* ***** END: sql, params, rowMapper ****** */
//
//
//
//
//	/* ***** BEGIN: sql, params, class ****** */
//	@Test
//	public void testQueryForOptionalObject3Null () throws Exception {
//		final String sql = "";
//		final Map<String, Object> params = new HashMap<>();
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, String.class);
//			result = null;
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, String.class);
//		Assert.assertFalse(actual.isPresent());
//	}
//
//	@Test
//	public void testQueryForOptionalObject3WithValue () throws Exception {
//		final String sql = "";
//		final Map<String, Object> params = new HashMap<>();
//		final String expected = "test";
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, String.class);
//			result = expected;
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, String.class);
//		Assert.assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
//	}
//
//	@Test
//	public void testQueryForOptionalObject3MoreThanOneValue () throws Exception {
//		final String sql = "";
//		final Map<String, Object> params = new HashMap<>();
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, String.class);
//			result = new EmptyResultDataAccessException(1);
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, String.class);
//		Assert.assertFalse(actual.isPresent());
//	}
//
//	@Test(expected=IncorrectResultSizeDataAccessException.class)
//	public void testQueryForOptionalObject3MultipleRows () throws Exception {
//		final String sql = "";
//		final Map<String, Object> params = new HashMap<>();
//		new Expectations(tested) {{
//			tested.queryForObject(sql, params, String.class);
//			result = new IncorrectResultSizeDataAccessException(1);
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, String.class);
//		Assert.assertFalse(actual.isPresent());
//	}
//	/* ***** END: sql, params, class ****** */
//
//
//	/* ***** BEGIN: sql, params, rowMapper ****** */
//	@Test
//	public void testQueryForOptionalObject4Null () throws Exception {
//		final String sql = "";
//		final Map<String, Object> params = new HashMap<>();
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
//		Assert.assertFalse(actual.isPresent());
//	}
//
//	@Test
//	public void testQueryForOptional4ObjectWithValue () throws Exception {
//		final String sql = "";
//		final Map<String, Object> params = new HashMap<>();
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
//		Assert.assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
//	}
//
//	@Test
//	public void testQueryForOptional4ObjectMoreThanOneValue () throws Exception {
//		final String sql = "";
//		final Map<String, Object> params = new HashMap<>();
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
//			result = new EmptyResultDataAccessException(1);
//		}};
//
//		Optional<String> actual = tested.queryForOptionalObject(sql, params, rowMapper);
//		Assert.assertFalse(actual.isPresent());
//	}
//
//	@Test(expected=IncorrectResultSizeDataAccessException.class)
//	public void testQueryForOptional4OMultipleRows () throws Exception {
//		final String sql = "";
//		final Map<String, Object> params = new HashMap<>();
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
//			result = new IncorrectResultSizeDataAccessException(1);
//		}};
//
//		tested.queryForOptionalObject(sql, params, rowMapper);
//	}
//	/* ***** END: sql, params, rowMapper ****** */
}
