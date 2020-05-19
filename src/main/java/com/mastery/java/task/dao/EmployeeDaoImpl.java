package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
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
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * EmployeeDao interface implementation. Used Spring Jdbc to access the database.
 */
@Repository
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

    @Value("${employee.getAll}")
    private String sqlGetAllEmployees;

    @Value("${employee.getById}")
    private String sqlGetEmployeeById;

    @Value("${employee.update}")
    private String sqlUpdateEmployee;

    @Value("${employee.delete}")
    private String sqlDeleteEmployee;

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
    public Long create(Employee employee) {
        LOGGER.debug("create ({})", employee);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("first_name", employee.getFirstName())
                .addValue("last_name", employee.getLastName())
                .addValue("department_id", employee.getDepartmentId())
                .addValue("job_title", employee.getJobTitle())
                .addValue("gender", employee.getGender().toString())
                .addValue("birthday", employee.getBirthday());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sqlCreateEmployee, mapSqlParameterSource, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
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
    public Optional<Employee> getById(Long id) {
        LOGGER.debug("getById ({})", id);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        List<Employee> employee = jdbcTemplate.query(sqlGetEmployeeById,
                mapSqlParameterSource, BeanPropertyRowMapper.newInstance(Employee.class));

        return Optional.ofNullable(DataAccessUtils.uniqueResult(employee));
    }

    /**
     * {@inheritDoc}
     * @param employee Employee.
     * @return number of updated records.
     */
    @Override
    public Integer update(Employee employee) {
        LOGGER.debug("update ({})", employee);

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", employee.getId())
                .addValue("first_name", employee.getFirstName())
                .addValue("last_name", employee.getLastName())
                .addValue("department_id", employee.getDepartmentId())
                .addValue("job_title", employee.getJobTitle())
                .addValue("gender", employee.getGender().name())
                .addValue("birthday", employee.getBirthday());

        return jdbcTemplate.update(sqlUpdateEmployee, parameterSource);
    }

    /**
     * {@inheritDoc}
     * @param id employee id.
     * @return number of deleted records.
     */
    @Override
    public Integer delete(Long id) {
        LOGGER.debug("delete ({})", id);

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);

        return jdbcTemplate.update(sqlDeleteEmployee, parameterSource);
    }
}
