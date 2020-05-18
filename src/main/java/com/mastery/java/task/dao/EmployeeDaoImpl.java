package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.sun.org.apache.bcel.internal.generic.LOOKUPSWITCH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * EmployeeDao interface implementation.
 */
public class EmployeeDaoImpl implements EmployeeDao {

    /**
     * Logger.
     */
    private Logger LOGGER = LoggerFactory.getLogger(EmployeeDaoImpl.class);

    /**
     * SQL statement inserts a new record in the "employee" table.
     */
    @Value("${employee.create}")
    private String sqlCreateEmployee;

    @Value("${employee.getAllEmployees}")
    private String sqlGetAllEmployees;

    @Value("${employee.getById}")
    private String sqlGetEmployeeById;

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

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sqlCreateEmployee, mapSqlParameterSource, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    /**
     * {@inheritDoc}
     * @return Employee list.
     */
    @Override
    public List<Employee> getAll() {
        LOGGER.debug("getAll()");


        return jdbcTemplate.query(sqlGetAllEmployees, BeanPropertyRowMapper.newInstance(Employee.class));
    }

    /**
     * {@inheritDoc}
     * @param id employee id.
     * @return Employee wrapped in Optional.
     */
    @Override
    public Optional<Employee> getById(Integer id) {
        LOGGER.debug("getById ({})", id);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        List<Employee> employee = jdbcTemplate.query(sqlGetEmployeeById,
                mapSqlParameterSource, BeanPropertyRowMapper.newInstance(Employee.class));

        return Optional.ofNullable(DataAccessUtils.uniqueResult(employee));
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
