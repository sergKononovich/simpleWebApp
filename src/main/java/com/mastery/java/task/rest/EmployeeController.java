package com.mastery.java.task.rest;

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
    ResponseEntity<Long> create(@Valid @RequestBody Employee employee) {
        LOGGER.debug("create ({})", employee);

        //Проверка, нет ли нулл полей в экземпляре employee.
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
    Integer update(@Valid @RequestBody Employee employee) {
        LOGGER.debug("update ({})", employee);

        //Тут тоже возможна ситация обновления сотрудника,
        // которого нет в бд или неполная информация о сотруднике в запросе.
        return employeeService.update(employee);
    }

    @DeleteMapping(value = "/employee/{id}")
    Integer delete(@PathVariable Long id) {
        LOGGER.debug("delete ({})", id);

        //Попытка удалить несуществующего сотрудника
        return employeeService.delete(id);
    }
}
