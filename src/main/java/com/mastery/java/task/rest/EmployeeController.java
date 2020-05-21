package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {
    Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    Long create(@RequestBody Employee employee) {
        LOGGER.debug("create ({})", employee);

        //Проверка, нет ли нулл полей в экземпляре employee.
        return employeeService.create(employee);
    }

    @GetMapping("/employees")
    List<Employee> getAll() {
        LOGGER.debug("getAll ()");

        return employeeService.getAll();
    }

    @GetMapping("/employee/{id}")
    Employee getById(@RequestParam Long id) {
        LOGGER.debug("getById ({})", id);

        Optional<Employee> optionalEmployee = employeeService.getById(id);
        if(optionalEmployee.isPresent()) {
            return optionalEmployee.get();
        } else {
            //Нужна обработка ситуации, когда указанного сотрудника не существует в бд.
            return new Employee();
        }
    }

    @PutMapping("/employee")
    Integer update(@RequestBody Employee employee) {
        LOGGER.debug("update ({})", employee);

        //Тут тоже возможна ситация обновления сотрудника,
        // которого нет в бд или неполная информация о сотруднике в запросе.
        return employeeService.update(employee);
    }

    @DeleteMapping("/employee/{id}")
    Integer delete(@RequestParam Long id) {
        LOGGER.debug("delete ({})", id);

        //Попытка удалить несуществующего сотрудника
        return employeeService.delete(id);
    }
}
