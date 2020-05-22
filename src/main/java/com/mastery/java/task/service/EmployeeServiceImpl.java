package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.exceptions.ThereIsNoSuchEmployeeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Employee service implementation
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    /**
     * Logger.
     */
    private Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    /**
     * EmployeeDao instance.
     */
    @Autowired
    private EmployeeDao employeeDao;

    /**
     * {@inheritDoc}
     * @param employee Employee.
     * @return Employee id.
     */
    @Override
    public Long create(Employee employee) {
        LOGGER.debug("create ({})", employee);

        return employeeDao.create(employee);
    }

    /**
     * {@inheritDoc}
     * @return Employee list.
     */
    @Override
    public List<Employee> getAll() {
        LOGGER.debug("getAll");

        return employeeDao.getAll();
    }

    /**
     * {@inheritDoc}
     * @param id employee id.
     * @return Employee wrapped in Optional.
     */
    @Override
    public Employee getById(Long id) {
        LOGGER.debug("getById ({})", id);

        Optional<Employee> optionalEmployee = employeeDao.getById(id);

        if(!optionalEmployee.isPresent()){
            throw new ThereIsNoSuchEmployeeException();
        }

        return optionalEmployee.get();
    }

    /**
     * {@inheritDoc}
     * @param employee Employee.
     * @return number of updated records.
     */
    @Override
    public Integer update(Employee employee) {
        LOGGER.debug("update ({})", employee);

        return employeeDao.update(employee);
    }

    /**
     * {@inheritDoc}
     * @param id id.
     * @return number of deleted records.
     */
    @Override
    public Integer delete(Long id) {
        LOGGER.debug("delete ({})", id);

        return employeeDao.delete(id);
    }
}
