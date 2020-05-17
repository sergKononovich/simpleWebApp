package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class EmployeeDaoTest {

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @InjectMocks
    private EmployeeDaoImpl employeeDao;

    @AfterEach
    void after() {
        verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void shouldCreateEmployee() {
        Employee employee = createEmployee(1);

        Mockito.when(namedParameterJdbcTemplate.update(Mockito.anyString(),
                Mockito.any(MapSqlParameterSource.class), Mockito.any(GeneratedKeyHolder.class)))
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

    private Employee createEmployee(long index) {
        return new Employee()
                .setEmployeeId(index)
                .setFirstName("Employee " + index)
                .setGender(Gender.MALE);
    }
}

