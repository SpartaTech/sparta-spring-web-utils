/**
 * Sparta Software Co.
 * 2017
 */
package org.sparta.springwebutils.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;

/**
 * Unit tests for class.
 * 
 * @author Daniel Conde Diehl - Sparta Technology
 *
 * History:
 *  Jan 18, 2017 - Daniel Conde Diehl
 *  Fev 02, 2017 - Daniel Conde Diehl - Adding tests for multiple rows issue.EmptyResultDataAccessException
 */

public class SpartaJdbcTemplateTest {

	@Tested
	private SpartaJdbcTemplate tested;
	
	@Injectable
	private DataSource dataSource;
	

	@Test
	public void testInitWithJdbcDataSource() {
		SpartaJdbcTemplate t1 = new SpartaJdbcTemplate(dataSource);
		DataSource actual = Deencapsulation.getField(t1, "dataSource");
		Assert.assertEquals(dataSource, actual);
	}

	@Test
	public void testInitWithJdbcDataSourceAndLazy() {
		SpartaJdbcTemplate t1 = new SpartaJdbcTemplate(dataSource, true);
		DataSource actual = Deencapsulation.getField(t1, "dataSource");
		Assert.assertEquals(dataSource, actual);
		boolean lazy = Deencapsulation.getField(t1, "lazyInit");
		Assert.assertTrue(lazy);
	}
	
	@Test
	public void testInitNoParameters() {		
		new SpartaJdbcTemplate();
	}
	
	/* ***** BEGIN: TEST FOR SQL, CLASS *********** */
	
	@Test
	public void testQueryForOptionalObjectNull () throws Exception {
		final String sql = "";
		new Expectations(tested) {{
			tested.queryForObject(sql, String.class);
			result = null;
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, String.class);
		Assert.assertFalse(actual.isPresent());
	}
	
	@Test
	public void testQueryForOptionalObjectWithValue () throws Exception {
		final String sql = "";
		final String expected = "test"; 
		new Expectations(tested) {{
			tested.queryForObject(sql, String.class);
			result = expected;
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, String.class);
		Assert.assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
	}
	
	@Test
	public void testQueryForOptionalObjectMoreThanOneValue () throws Exception {
		final String sql = "";
		new Expectations(tested) {{
			tested.queryForObject(sql, String.class);
			result = new EmptyResultDataAccessException(1);
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, String.class);
		Assert.assertFalse(actual.isPresent());
	}
	
	@Test(expected=IncorrectResultSizeDataAccessException.class)
	public void testQueryForOptionalMultipleRows () throws Exception {
		final String sql = "";
		new Expectations(tested) {{
			tested.queryForObject(sql, String.class);
			result = new IncorrectResultSizeDataAccessException(1);
		}};

		tested.queryForOptionalObject(sql, String.class);
	}
	/* ***** END: TEST FOR SQL, CLASS ******* */
	
	
	
	/* ***** BEGIN: sql, rowMapper ***** */
	@Test
	public void testQueryForOptionalObject2Null () throws Exception {
		final String sql = "";
		final RowMapper<String> rowMapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return "teste"; 
			}
		};
		
		new Expectations(tested) {{
			tested.queryForObject(sql, rowMapper);
			result = null;
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, rowMapper);
		Assert.assertFalse(actual.isPresent());
	}
	
	@Test
	public void testQueryForOptional2ObjectWithValue () throws Exception {
		final String sql = "";
		final String expected = "test";
		
		final RowMapper<String> rowMapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return "teste"; 
			}
		};
		
		new Expectations(tested) {{
			tested.queryForObject(sql, rowMapper);
			result = expected;
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, rowMapper);
		Assert.assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
	}
	
	@Test
	public void testQueryForOptional2ObjectMoreThanOneValue () throws Exception {
		final String sql = "";
		
		final RowMapper<String> rowMapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return "teste"; 
			}
		};
		
		new Expectations(tested) {{
			tested.queryForObject(sql, rowMapper);
			result = new EmptyResultDataAccessException(1);
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, rowMapper);
		Assert.assertFalse(actual.isPresent());
	}
	
	@Test(expected=IncorrectResultSizeDataAccessException.class)
	public void testQueryForOptional2ObjectMultipleRows () throws Exception {
		final String sql = "";
		
		final RowMapper<String> rowMapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return "teste"; 
			}
		};
		
		new Expectations(tested) {{
			tested.queryForObject(sql, rowMapper);
			result = new IncorrectResultSizeDataAccessException(1);
		}};

		tested.queryForOptionalObject(sql, rowMapper);
	}
	/* ***** END: sql, rowMapper ***** */
	
	
	
	/* ***** BEGIN: sql, class, params ******* */
	@Test
	public void testQueryForOptionalObject3Null () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		new Expectations(tested) {{
			tested.queryForObject(sql, String.class, params);
			result = null;
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, String.class, params);
		Assert.assertFalse(actual.isPresent());
	}
	
	@Test
	public void testQueryForOptionalObject3WithValue () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		final String expected = "test"; 
		new Expectations(tested) {{
			tested.queryForObject(sql, String.class, params);
			result = expected;
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, String.class, params);
		Assert.assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
	}
	
	@Test
	public void testQueryForOptionalObject3MoreThanOneValue () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		new Expectations(tested) {{
			tested.queryForObject(sql, String.class, params);
			result = new EmptyResultDataAccessException(1);
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, String.class, params);
		Assert.assertFalse(actual.isPresent());
	}
	
	@Test(expected=IncorrectResultSizeDataAccessException.class)
	public void testQueryForOptionalObject3MultipleRows () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		new Expectations(tested) {{
			tested.queryForObject(sql, String.class, params);
			result = new IncorrectResultSizeDataAccessException(1);
		}};

		tested.queryForOptionalObject(sql, String.class, params);
	}
	/* ***** END: sql, class, params ******* */
	
	

	
	/* ***** BEGIN: sql, params, class ******* */
	@Test
	public void testQueryForOptionalObject4Null () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		new Expectations(tested) {{
			tested.queryForObject(sql, params, String.class);
			result = null;
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, params, String.class);
		Assert.assertFalse(actual.isPresent());
	}
	
	@Test
	public void testQueryForOptionalObject4WithValue () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		final String expected = "test"; 
		new Expectations(tested) {{
			tested.queryForObject(sql, params, String.class);
			result = expected;
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, params, String.class);
		Assert.assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
	}
	
	@Test
	public void testQueryForOptionalObject4MoreThanOneValue () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		new Expectations(tested) {{
			tested.queryForObject(sql, params, String.class);
			result = new EmptyResultDataAccessException(1);
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, params, String.class);
		Assert.assertFalse(actual.isPresent());
	}
	
	@Test(expected=IncorrectResultSizeDataAccessException.class)
	public void testQueryForOptionalObject4MultipleRows () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		new Expectations(tested) {{
			tested.queryForObject(sql, params, String.class);
			result = new IncorrectResultSizeDataAccessException(1);
		}};

		tested.queryForOptionalObject(sql, params, String.class);
	}
	/* ***** END: sql, params, class ******* */
	
	

	
	
	/* ***** BEGIN: sql, params, rowMapper ******* */
	@Test
	public void testQueryForOptionalObject5Null () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		final RowMapper<String> rowMapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return "teste"; 
			}
		};
		
		new Expectations(tested) {{
			tested.queryForObject(sql, params, rowMapper);
			result = null;
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, params, rowMapper);
		Assert.assertFalse(actual.isPresent());
	}
	
	@Test
	public void testQueryForOptionalObject5WithValue () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		final String expected = "test";
		
		final RowMapper<String> rowMapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return "teste"; 
			}
		};
		
		new Expectations(tested) {{
			tested.queryForObject(sql, params, rowMapper);
			result = expected;
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, params, rowMapper);
		Assert.assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
	}
	
	@Test
	public void testQueryForOptionalObject5MoreThanOneValue () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		final RowMapper<String> rowMapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return "teste"; 
			}
		};
		
		new Expectations(tested) {{
			tested.queryForObject(sql, params, rowMapper);
			result = new EmptyResultDataAccessException(1);
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, params, rowMapper);
		Assert.assertFalse(actual.isPresent());
	}
	
	@Test(expected=IncorrectResultSizeDataAccessException.class)
	public void testQueryForOptionalObject5MultipleRows () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		final RowMapper<String> rowMapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return "teste"; 
			}
		};
		
		new Expectations(tested) {{
			tested.queryForObject(sql, params, rowMapper);
			result = new IncorrectResultSizeDataAccessException(1);
		}};

		tested.queryForOptionalObject(sql, params, rowMapper);
	}
	/* ***** END: sql, params, rowMapper ******* */
	
	

	
	
	/* ***** BEGIN: sql, rowMapper, params ******* */
	@Test
	public void testQueryForOptionalObject6Null () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		final RowMapper<String> rowMapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return "teste"; 
			}
		};
		
		new Expectations(tested) {{
			tested.queryForObject(sql, rowMapper, params);
			result = null;
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, rowMapper, params);
		Assert.assertFalse(actual.isPresent());
	}
	
	@Test
	public void testQueryForOptionalObject6WithValue () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		final String expected = "test";
		
		final RowMapper<String> rowMapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return "teste"; 
			}
		};
		
		new Expectations(tested) {{
			tested.queryForObject(sql, rowMapper, params);
			result = expected;
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, rowMapper, params);
		Assert.assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
	}
	
	@Test
	public void testQueryForOptionalObject6MoreThanOneValue () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		final RowMapper<String> rowMapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return "teste"; 
			}
		};
		
		new Expectations(tested) {{
			tested.queryForObject(sql, rowMapper, params);
			result = new EmptyResultDataAccessException(1);
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, rowMapper, params);
		Assert.assertFalse(actual.isPresent());
	}
	
	@Test(expected=IncorrectResultSizeDataAccessException.class)
	public void testQueryForOptionalObject6MultipleRows () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		final RowMapper<String> rowMapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return "teste"; 
			}
		};
		
		new Expectations(tested) {{
			tested.queryForObject(sql, rowMapper, params);
			result = new IncorrectResultSizeDataAccessException(1);
		}};

		tested.queryForOptionalObject(sql, rowMapper, params);
	}
	/* ***** END: sql, rowMapper, params ******* */
	
	
	
	
	
	

	
	/* ***** BEGIN: sql, params, paramTypes, rowMapper ******* */
	@Test
	public void testQueryForOptionalObject7Null () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		final int[] paramType = new int[1];
		final RowMapper<String> rowMapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return "teste"; 
			}
		};
		
		new Expectations(tested) {{
			tested.queryForObject(sql, params, paramType, rowMapper);
			result = null;
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, params, paramType, rowMapper);
		Assert.assertFalse(actual.isPresent());
	}
	
	@Test
	public void testQueryForOptionalObject7WithValue () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		final int[] paramType = new int[1];
		final String expected = "test";
		
		final RowMapper<String> rowMapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return "teste"; 
			}
		};
		
		new Expectations(tested) {{
			tested.queryForObject(sql, params, paramType, rowMapper);
			result = expected;
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, params, paramType, rowMapper);
		Assert.assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
	}
	
	@Test
	public void testQueryForOptionalObject7MoreThanOneValue () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		final int[] paramType = new int[1];
		final RowMapper<String> rowMapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return "teste"; 
			}
		};
		
		new Expectations(tested) {{
			tested.queryForObject(sql, params, paramType, rowMapper);
			result = new EmptyResultDataAccessException(1);
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, params, paramType, rowMapper);
		Assert.assertFalse(actual.isPresent());
	}
	/* ***** END: sql, params, paramTypes, rowMapper ******* */
	
	
	
	/* ***** BEGIN: sql, params, paramTypes, class ******* */
	@Test
	public void testQueryForOptionalObject8Null () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		final int[] paramType = new int[1];
		new Expectations(tested) {{
			tested.queryForObject(sql, params, paramType, String.class);
			result = null;
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, params, paramType, String.class);
		Assert.assertFalse(actual.isPresent());
	}
	
	@Test
	public void testQueryForOptionalObject8WithValue () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		final int[] paramType = new int[1];
		final String expected = "test"; 
		new Expectations(tested) {{
			tested.queryForObject(sql, params, paramType, String.class);
			result = expected;
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, params, paramType, String.class);
		Assert.assertEquals(expected, actual.orElseThrow(() -> new AssertionError("Expected value but came empty")));
	}
	
	@Test
	public void testQueryForOptionalObject8MoreThanOneValue () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		final int[] paramType = new int[1];
		new Expectations(tested) {{
			tested.queryForObject(sql, params, paramType, String.class);
			result = new EmptyResultDataAccessException(1);
		}};

		Optional<String> actual = tested.queryForOptionalObject(sql, params, paramType, String.class);
		Assert.assertFalse(actual.isPresent());
	}
	
	@Test(expected=IncorrectResultSizeDataAccessException.class)
	public void testQueryForOptionalObject8MultipleRows () throws Exception {
		final String sql = "";
		final Object[] params = new Object[1];
		final int[] paramType = new int[1];
		new Expectations(tested) {{
			tested.queryForObject(sql, params, paramType, String.class);
			result = new IncorrectResultSizeDataAccessException(1);
		}};

		tested.queryForOptionalObject(sql, params, paramType, String.class);
	}
	/* ***** END: sql, params, paramTypes, class ******* */
}
