package com.mastery.java.task.rest;

import com.mastery.java.task.dto.ValidationEmployee;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.List;

@RestController
@EnableSwagger2
public class EmployeeController {
    Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/employee")
    ResponseEntity<Long> create(@Valid @RequestBody ValidationEmployee validationEmployee) {
        LOGGER.debug("create ({})", validationEmployee);

        Employee employee = new Employee(validationEmployee);

        return ResponseEntity.ok(employeeService.create(employee));
    }

    @GetMapping(value = "/employees")
    List<Employee> getAll() {
        LOGGER.debug("getAll ()");

        return employeeService.getAll();
    }

    @GetMapping(value = "/employee/{id}")
    Employee getById(@PathVariable Long id) {
        LOGGER.debug("getById ({})", id);

        return employeeService.getById(id);
    }

    @PutMapping(value = "/employee")
    Integer update(@Valid @RequestBody ValidationEmployee validationEmployee) {
        LOGGER.debug("update ({})", validationEmployee);

        Employee employee = new Employee(validationEmployee);

        return employeeService.update(employee);
    }

    @DeleteMapping(value = "/employee/{id}")
    Integer delete(@PathVariable Long id) {
        LOGGER.debug("delete ({})", id);

        return employeeService.delete(id);
    }
}
