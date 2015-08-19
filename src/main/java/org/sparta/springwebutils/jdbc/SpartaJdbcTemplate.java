
package org.sparta.springwebutils.jdbc;

import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;

/** 
 * @author Carlos Eduardo Endler Genz – Sparta Java Team 
 * 
 * History: 
 *    Mar 11, 2015 - Carlos Eduardo Endler Genz
 *  
 */ 
public class SpartaJdbcTemplate extends JdbcTemplate {
    
    /**
     * Construct a new SpartaJdbcTemplate for bean usage.
     * <p>Note: The DataSource has to be set before using the instance.
     * @see #setDataSource
     */
    public SpartaJdbcTemplate() {
        super();
    }

    /**
     * Construct a new SpartaJdbcTemplate, given a DataSource to obtain connections from.
     * <p>Note: This will not trigger initialization of the exception translator.
     * @param dataSource the JDBC DataSource to obtain connections from
     */
    public SpartaJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * Construct a new SpartaJdbcTemplate, given a DataSource to obtain connections from.
     * <p>Note: Depending on the "lazyInit" flag, initialization of the exception translator
     * will be triggered.
     * @param dataSource the JDBC DataSource to obtain connections from
     * @param lazyInit whether to lazily initialize the SQLExceptionTranslator
     */
    public SpartaJdbcTemplate(DataSource dataSource, boolean lazyInit) {
        super(dataSource, lazyInit);
    }
    
    /**
     * Execute a query for a result object (optional), given static SQL.
     * <p>Uses a JDBC Statement, not a PreparedStatement. If you want to
     * execute a static query with a PreparedStatement, use the overloaded
     * {@link #queryForOptionalObject(String, Class, Object...)} method with
     * {@code null} as argument array.
     * <p>This method is useful for running static SQL with a known outcome.
     * The query is expected to be a single row/single column query; the returned
     * result will be directly mapped to the corresponding object type.
     * @param sql SQL query to execute
     * @param requiredType the type that the result object is expected to match
     * @return the result object of the required type (optionally)
     * @throws DataAccessException if there is any problem executing the query
     * @see #queryForOptionalObject(String, Object[], Class)
     */
    public <T> Optional<T> queryForOptionalObject(String sql, Class<T> requiredType) throws DataAccessException {
        Optional<T> result;
        
        try {
            final T obj = super.queryForObject(sql, requiredType);
            result = Optional.of(obj);
        } catch (IncorrectResultSizeDataAccessException e) {
            result = Optional.empty();
        }
        
        return result;
    }
    
    /**
     * Query given SQL to create a prepared statement from SQL and a
     * list of arguments to bind to the query, expecting a result object (optional).
     * <p>The query is expected to be a single row/single column query; the returned
     * result will be directly mapped to the corresponding object type.
     * @param sql SQL query to execute
     * @param requiredType the type that the result object is expected to match
     * @param args arguments to bind to the query
     * (leaving it to the PreparedStatement to guess the corresponding SQL type);
     * may also contain {@link SqlParameterValue} objects which indicate not
     * only the argument value but also the SQL type and optionally the scale
     * @return the result object of the required type (optionally)
     * @throws DataAccessException if the query fails
     * @see #queryForOptionalObject(String, Class)
     */
    public <T> Optional<T> queryForOptionalObject(String sql, Class<T> requiredType, Object... args) throws DataAccessException {
        Optional<T> result;
        
        try {
            final T obj = super.queryForObject(sql, requiredType, args);
            result = Optional.of(obj);
        } catch (IncorrectResultSizeDataAccessException e) {
            result = Optional.empty();
        }
        
        return result;
    }
    
    /**
     * Query given SQL to create a prepared statement from SQL and a
     * list of arguments to bind to the query, expecting a result object (optional).
     * <p>The query is expected to be a single row/single column query; the returned
     * result will be directly mapped to the corresponding object type.
     * @param sql SQL query to execute
     * @param args arguments to bind to the query
     * (leaving it to the PreparedStatement to guess the corresponding SQL type);
     * may also contain {@link SqlParameterValue} objects which indicate not
     * only the argument value but also the SQL type and optionally the scale
     * @param requiredType the type that the result object is expected to match
     * @return the result object of the required type (optionally)
     * @throws DataAccessException if the query fails
     * @see #queryForOptionalObject(String, Class)
     */
    public <T> Optional<T> queryForOptionalObject(String sql, Object[] args, Class<T> requiredType) throws DataAccessException {
        Optional<T> result;
        
        try {
            final T obj = super.queryForObject(sql, args, requiredType);
            result = Optional.of(obj);
        } catch (IncorrectResultSizeDataAccessException e) {
            result = Optional.empty();
        }
        
        return result;
    }
    
    /**
     * Query given SQL to create a prepared statement from SQL and a
     * list of arguments to bind to the query, expecting a result object (optional).
     * <p>The query is expected to be a single row/single column query; the returned
     * result will be directly mapped to the corresponding object type.
     * @param sql SQL query to execute
     * @param args arguments to bind to the query
     * @param argTypes SQL types of the arguments
     * (constants from {@code java.sql.Types})
     * @param requiredType the type that the result object is expected to match
     * @return the result object of the required type (optionally)
     * @throws DataAccessException if the query fails
     * @see #queryForOptionalObject(String, Class)
     * @see java.sql.Types
     */
    public <T> Optional<T> queryForOptionalObject(String sql, Object[] args, int[] argTypes, Class<T> requiredType) throws DataAccessException {
        Optional<T> result;
        
        try {
            final T obj = super.queryForObject(sql, args, argTypes, requiredType);
            result = Optional.of(obj);
        } catch (IncorrectResultSizeDataAccessException e) {
            result = Optional.empty();
        }
        
        return result;
    }
    
    /**
     * Query given SQL to create a prepared statement from SQL and a list
     * of arguments to bind to the query, mapping a single result row to a
     * Java object via a RowMapper.
     * @param sql SQL query to execute
     * @param args arguments to bind to the query
     * (leaving it to the PreparedStatement to guess the corresponding SQL type)
     * @param argTypes SQL types of the arguments
     * (constants from {@code java.sql.Types})
     * @param rowMapper object that will map one object per row
     * @return the single mapped object (optional)
     * @throws DataAccessException if the query fails
     */
    public <T> Optional<T> queryForOptionalObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) throws DataAccessException {
        Optional<T> result;
        
        try {
            final T obj = super.queryForObject(sql, args, argTypes, rowMapper);
            result = Optional.of(obj);
        } catch (IncorrectResultSizeDataAccessException e) {
            result = Optional.empty();
        }
        
        return result;
    }
    
    /**
     * Query given SQL to create a prepared statement from SQL and a list
     * of arguments to bind to the query, mapping a single result row to a
     * Java object via a RowMapper.
     * @param sql SQL query to execute
     * @param args arguments to bind to the query
     * (leaving it to the PreparedStatement to guess the corresponding SQL type);
     * may also contain {@link SqlParameterValue} objects which indicate not
     * only the argument value but also the SQL type and optionally the scale
     * @param rowMapper object that will map one object per row
     * @return the single mapped object (optional)
     * @throws DataAccessException if the query fails
     */
    public <T> Optional<T> queryForOptionalObject(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
        Optional<T> result;
        
        try {
            final T obj = super.queryForObject(sql, args, rowMapper);
            result = Optional.of(obj);
        } catch (IncorrectResultSizeDataAccessException e) {
            result = Optional.empty();
        }
        
        return result;
    }

    /**
     * Execute a query given static SQL, mapping a single result row to a Java
     * object via a RowMapper.
     * <p>Uses a JDBC Statement, not a PreparedStatement. If you want to
     * execute a static query with a PreparedStatement, use the overloaded
     * {@link #queryForOptionalObject(String, RowMapper, Object...)} method with
     * {@code null} as argument array.
     * @param sql SQL query to execute
     * @param rowMapper object that will map one object per row
     * @return the single mapped object (optional)
     * @throws DataAccessException if there is any problem executing the query
     * @see #queryForOptionalObject(String, Object[], RowMapper)
     */
    public <T> Optional<T> queryForOptionalObject(String sql, RowMapper<T> rowMapper) throws DataAccessException {
        Optional<T> result;
        
        try {
            final T obj = super.queryForObject(sql, rowMapper);
            result = Optional.of(obj);
        } catch (IncorrectResultSizeDataAccessException e) {
            result = Optional.empty();
        }
        
        return result;
    }
    
    /**
     * Query given SQL to create a prepared statement from SQL and a list
     * of arguments to bind to the query, mapping a single result row to a
     * Java object via a RowMapper.
     * @param sql SQL query to execute
     * @param rowMapper object that will map one object per row
     * @param args arguments to bind to the query
     * (leaving it to the PreparedStatement to guess the corresponding SQL type);
     * may also contain {@link SqlParameterValue} objects which indicate not
     * only the argument value but also the SQL type and optionally the scale
     * @return the single mapped object (optional)
     * @throws DataAccessException if the query fails
     */
    public <T> Optional<T> queryForOptionalObject(String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException {
        Optional<T> result;
        
        try {
            final T obj = super.queryForObject(sql, rowMapper, args);
            result = Optional.of(obj);
        } catch (IncorrectResultSizeDataAccessException e) {
            result = Optional.empty();
        }
        
        return result;
    }
}
