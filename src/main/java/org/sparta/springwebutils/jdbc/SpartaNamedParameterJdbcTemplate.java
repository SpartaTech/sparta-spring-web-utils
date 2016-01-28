
package org.sparta.springwebutils.jdbc;

import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * @author Carlos Eduardo Endler Genz – Sparta Java Team
 * 
 *         History: Mar 11, 2015 - Carlos Eduardo Endler Genz
 * 
 */
public class SpartaNamedParameterJdbcTemplate extends NamedParameterJdbcTemplate {

    /**
     * constructor by datasource
     * 
     * @param dataSource data source to initialize 
     */
    public SpartaNamedParameterJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * Constructor by jdbcOperations
     * 
     * @param jdbcOperations jdbc operations to initialize
     */
    public SpartaNamedParameterJdbcTemplate(JdbcOperations jdbcOperations) {
        super(jdbcOperations);
    }
    
    /**
     * Query given SQL to create a prepared statement from SQL and a list
     * of arguments to bind to the query, mapping a single result row to a
     * Java object via a RowMapper.
     * @param sql SQL query to execute
     * @param paramSource container of arguments to bind to the query
     * @param rowMapper object that will map one object per row
     * @return the single mapped object (optionally)
     * @throws org.springframework.dao.IncorrectResultSizeDataAccessException
     * if the query does not return exactly one row, or does not return exactly
     * one column in that row
     * @throws org.springframework.dao.DataAccessException if the query fails
     */
    public <T> Optional<T> queryForOptionalObject(String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper) throws DataAccessException {
        Optional<T> result;

        try {
            final T obj = super.queryForObject(sql, paramSource, rowMapper);
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
     * @param paramMap map of parameters to bind to the query
     * (leaving it to the PreparedStatement to guess the corresponding SQL type)
     * @param rowMapper object that will map one object per row
     * @return the single mapped object (optionally)
     * @throws org.springframework.dao.IncorrectResultSizeDataAccessException
     * if the query does not return exactly one row, or does not return exactly
     * one column in that row
     * @throws org.springframework.dao.DataAccessException if the query fails
     */
    public <T> Optional<T> queryForOptionalObject(String sql, Map<String, ?> paramMap, RowMapper<T> rowMapper) throws DataAccessException {
        Optional<T> result;

        try {
            final T obj = super.queryForObject(sql, paramMap, rowMapper);
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
     * @param paramSource parameters to bind to the query
     * (leaving it to the PreparedStatement to guess the corresponding SQL type)
     * @param requiredType class to map
     * @return the single mapped object (optionally)
     * @throws org.springframework.dao.IncorrectResultSizeDataAccessException
     * if the query does not return exactly one row, or does not return exactly
     * one column in that row
     * @throws org.springframework.dao.DataAccessException if the query fails
     */
    public <T> Optional<T> queryForOptionalObject(String sql, SqlParameterSource paramSource, Class<T> requiredType) throws DataAccessException {
        Optional<T> result;

        try {
            final T obj = super.queryForObject(sql, paramSource, requiredType);
            result = Optional.of(obj);
        } catch (IncorrectResultSizeDataAccessException e) {
            result = Optional.empty();
        }

        return result;
    }

    /**
     * Query given SQL to create a prepared statement from SQL and a
     * list of arguments to bind to the query, expecting a result object.
     * <p>The query is expected to be a single row/single column query; the returned
     * result will be directly mapped to the corresponding object type.
     * @param sql SQL query to execute
     * @param paramMap map of parameters to bind to the query
     * (leaving it to the PreparedStatement to guess the corresponding SQL type)
     * @param requiredType the type that the result object is expected to match
     * @return the result object of the required type (optionally)
     * @throws org.springframework.dao.IncorrectResultSizeDataAccessException
     * if the query does not return exactly one row, or does not return exactly
     * one column in that row
     * @throws org.springframework.dao.DataAccessException if the query fails
     * @see org.springframework.jdbc.core.JdbcTemplate#queryForObject(String, Class)
     */
    public <T> Optional<T> queryForOptionalObject(String sql, Map<String, ?> paramMap, Class<T> requiredType) throws DataAccessException {
        Optional<T> result;

        try {
            final T obj = super.queryForObject(sql, paramMap, requiredType);
            result = Optional.of(obj);
        } catch (IncorrectResultSizeDataAccessException e) {
            result = Optional.empty();
        }

        return result;
    }
}
