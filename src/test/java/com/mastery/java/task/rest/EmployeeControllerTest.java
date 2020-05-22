package com.mastery.java.task.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import com.mastery.java.task.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    private MockMvc mockMvc;

    ObjectMapper objectMapper;

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();

        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @AfterEach
    public void after() {
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void shouldCreateEmployee() throws Exception {
        Employee testEmployee = createEmployee(1);
        when(employeeService.create(testEmployee)).thenReturn(5L);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/employee")
                        .content(objectMapper.writeValueAsString(testEmployee))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("5"))
        ;

        verify(employeeService).create(testEmployee);
    }

    @Test
    public void shouldGetEmployeeById() throws Exception {
        Employee testEmployee = createEmployee(2);
        when(employeeService.getById(testEmployee.getId())).thenReturn(Optional.of(testEmployee));

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/employee/" + testEmployee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Employee employee = objectMapper.readValue(response.getContentAsString(), new TypeReference<Employee>() {});

        assertNotNull(employee);
        assertEquals(testEmployee, employee);
    }

    @Test
    public void shouldGetAllEmployees() throws Exception {
        List<Employee> testEmployees = Arrays.asList(createEmployee(1), createEmployee(2));
        when(employeeService.getAll()).thenReturn(testEmployees);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        List<Employee> responseEmployees = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Employee>>() {});

        assertNotNull(responseEmployees);
        assertEquals(testEmployees, responseEmployees);
    }

    @Test
    public void shouldUpdateEmployee() throws Exception {
        Employee testEmployee = createEmployee(3);
        when(employeeService.update(any(Employee.class))).thenReturn(1);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testEmployee))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("1"));

        verify(employeeService).update(testEmployee);
    }

    @Test
    public void shouldDeleteEmployee() throws Exception {
        when(employeeService.delete(anyLong())).thenReturn(1);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/employee/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("1"));

        verify(employeeService).delete(anyLong());
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
