package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Rollback
@Transactional
@SpringBootTest
public class EmployeeDaoIT {

    @Autowired
    EmployeeDaoImpl employeeDao;

    @Test
    public void shouldCreateEmployee() {

        Employee testEmployee = createEmployee(1);
        employeeDao.create(testEmployee);

        Optional<Employee> optionalEmployee = employeeDao.getById(1);
        assertTrue(optionalEmployee.isPresent());

        Employee employeeFromDb = optionalEmployee.get();

        assertNotNull(employeeFromDb);
        assertEquals(testEmployee, employeeFromDb);
    }

    private Employee createEmployee(long index) {
        Random random = new Random();

        Employee employee = new Employee();
        employee.setId(index);
        employee.setFirstName("First " + index );
        employee.setLastName("Last " + index);
        employee.setDepartmentId(index);
        employee.setGender(Gender.MALE);
        employee.setJobTitle("Title " + index);
        employee.setBirthday(LocalDate.of(random.nextInt(25) + 2000,
                Month.of(random.nextInt(12) + 1),
                random.nextInt(27) + 1));

        return employee;
    }
}
