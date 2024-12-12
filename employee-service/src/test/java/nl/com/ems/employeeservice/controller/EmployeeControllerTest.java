package nl.com.ems.employeeservice.controller;

import nl.com.ems.employeeservice.model.Employee;
import nl.com.ems.employeeservice.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEmployees_success() {
        Employee employee = new Employee();
        employee.setFirstname("John");
        employee.setSurname("Doe");
        employee.setRoleId(1L);
        employee.setProjectIds(Arrays.asList(1L, 2L));
        when(employeeService.getAllEmployees()).thenReturn(Collections.singletonList(employee));
        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees("USER");
        assertEquals(1, response.getBody().size());
        verify(employeeService, times(1)).getAllEmployees();

    }

    @Test
    void createEmployee_success() {
        Employee employee = new Employee();
        employee.setFirstname("John");
        employee.setSurname("Doe");
        employee.setRoleId(1L);
        employee.setProjectIds(Arrays.asList(1L, 2L));
        when(employeeService.createEmployee(anyString(), any(Employee.class))).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.createEmployee("ADMIN", employee);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John", response.getBody().getFirstname());
        assertEquals("Doe", response.getBody().getSurname());
        assertEquals(1L, response.getBody().getRoleId());
    }

    @Test
    void getEmployee_success() {
        Employee employee = new Employee();
        employee.setFirstname("John");
        employee.setSurname("Doe");
        employee.setRoleId(1L);
        employee.setProjectIds(Arrays.asList(1L, 2L));
        when(employeeService.getEmployeeById(anyLong())).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.getEmployee(1L, "USER");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John", response.getBody().getFirstname());
        assertEquals("Doe", response.getBody().getSurname());
        assertEquals(1L, response.getBody().getRoleId());
        assertEquals(Arrays.asList(1L, 2L), response.getBody().getProjectIds());
    }

    @Test
    void updateEmployee_success() {
        Employee employee = new Employee();
        employee.setFirstname("Jane");
        employee.setSurname("Doe");
        employee.setRoleId(1L);
        employee.setProjectIds(Arrays.asList(1L, 2L));
        when(employeeService.updateEmployee(anyLong(),anyString() ,any(Employee.class))).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.updateEmployee(1L, "ADMIN", employee);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Jane", response.getBody().getFirstname());
        assertEquals("Doe", response.getBody().getSurname());
        assertEquals(1L, response.getBody().getRoleId());
    }

    @Test
    void deleteEmployee_success() {
        when(employeeService.deleteEmployee(anyLong())).thenReturn("Employee deleted successfully");

        ResponseEntity<String> response = employeeController.deleteEmployee(1L, "ADMIN");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee deleted successfully", response.getBody());
    }
}