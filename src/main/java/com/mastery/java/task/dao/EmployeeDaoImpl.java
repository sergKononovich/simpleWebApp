package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Objects;

/**
 * EmployeeDao interface implementation.
 */
public class EmployeeDaoImpl implements EmployeeDao {

    /**
     * Logger.
     */
    private Logger LOGGER = LoggerFactory.getLogger(EmployeeDaoImpl.class);

    /**
     * Interface for retrieving keys, used for auto-generated keys as returned by JDBC insert statements.
     */
    private KeyHolder keyHolder = new GeneratedKeyHolder();

    /**
     * SQL statement inserts a new record in the "employee" table.
     */
    @Value("${employee.create}")
    private String sqlCreateEmployee;

    /**
     * Template class with a basic set of JDBC operations.
     */
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * {@inheritDoc}
     * @param employee Employee.
     * @return employee id.
     */
    @Override
    public Integer create(Employee employee) {
        LOGGER.debug("create ({})", employee);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("first_name", employee.getFirstName());
        mapSqlParameterSource.addValue("gender", employee.getGender());

        jdbcTemplate.update(sqlCreateEmployee, mapSqlParameterSource, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public List<Employee> getAll() {
        return null;
    }

    @Override
    public Employee getById(Integer id) {
        return null;
    }

    @Override
    public Integer update(Employee employee) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }
}
