package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeDaoTest {

    @Mock
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @InjectMocks
    EmployeeDaoImpl employeeDao;

    @Value("${employee.create}")
    private String sqlCreateEmployee;

    @Value("${employee.getAllEmployees}")
    private String sqlGetAllEmployees;

    @AfterEach
    public void after() {
        verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void shouldCreateEmployee() {
        Employee employee = createEmployee(1);

        ReflectionTestUtils.setField(employeeDao, "sqlCreateEmployee", "create");

        when(namedParameterJdbcTemplate.update(anyString(),
                any(MapSqlParameterSource.class), any(GeneratedKeyHolder.class)))
                .thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Map<String, Object> keyMap = new HashMap<>();
                keyMap.put("", 1);
                ((GeneratedKeyHolder)args[2]).getKeyList().add(keyMap);

                return 1;
            }
        }).thenReturn(1);

        assertEquals(1, employeeDao.create(employee));

        verify(namedParameterJdbcTemplate).update(anyString(), any(MapSqlParameterSource.class), any(GeneratedKeyHolder.class));
    }

    @Test
    public void shouldGetAllEmployees() {

        ReflectionTestUtils.setField(employeeDao, "sqlGetAllEmployees", "get all");

        when(namedParameterJdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(Arrays.asList(createEmployee(1), createEmployee(2)));

        List<Employee> employees = employeeDao.getAll();

        assertNotNull(employees);
        assertEquals(createEmployee(1), employees.get(0));
        assertEquals(createEmployee(2), employees.get(1));

        verify(namedParameterJdbcTemplate).query(anyString(), any(RowMapper.class));
    }

    @Test
    public void shouldGetEmployeeById() {

        Employee testEmployee = createEmployee(1);
        ReflectionTestUtils.setField(employeeDao, "sqlGetEmployeeById", "get by id");

        when(namedParameterJdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class)))
                .thenReturn(Collections.singletonList(testEmployee));

        Optional<Employee> optionalEmployee = employeeDao.getById(1);
        assertTrue(optionalEmployee.isPresent());

        Employee employee = optionalEmployee.get();

        assertEquals(testEmployee, employee);
    }

    private Employee createEmployee(long index) {
        return new Employee()
                .setEmployeeId(index)
                .setFirstName("Employee " + index)
                .setGender(Gender.MALE);
    }
}
