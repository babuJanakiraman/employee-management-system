package nl.com.ems.employeedatabase.controller;

import nl.com.ems.employeedatabase.model.EmployeeRequest;
import nl.com.ems.employeedatabase.model.EmployeeResponse;
import nl.com.ems.employeedatabase.service.EmployeeDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmployeeDatabaseControllerTest {

    @Mock
    private EmployeeDataService employeeDataService;

    @InjectMocks
    private EmployeeDatabaseController employeeDatabaseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEmployees_shouldReturnListOfEmployees() {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        when(employeeDataService.getAllEmployees()).thenReturn(Collections.singletonList(employeeResponse));

        ResponseEntity<List<EmployeeResponse>> response = employeeDatabaseController.getAllEmployees();

        assertEquals(1, response.getBody().size());
        verify(employeeDataService, times(1)).getAllEmployees();
    }

    @Test
    void createEmployee_shouldReturnCreatedEmployee() {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        EmployeeResponse employeeResponse = new EmployeeResponse();
        when(employeeDataService.createEmployee(employeeRequest)).thenReturn(employeeResponse);

        ResponseEntity<EmployeeResponse> response = employeeDatabaseController.createEmployee(employeeRequest);

        assertEquals(employeeResponse, response.getBody());
        verify(employeeDataService, times(1)).createEmployee(employeeRequest);
    }

    @Test
    void getEmployeeById_shouldReturnEmployee() {
        Long id = 1L;
        EmployeeResponse employeeResponse = new EmployeeResponse();
        when(employeeDataService.getEmployeeById(id)).thenReturn(employeeResponse);

        ResponseEntity<EmployeeResponse> response = employeeDatabaseController.getEmployeeById(id);

        assertEquals(employeeResponse, response.getBody());
        verify(employeeDataService, times(1)).getEmployeeById(id);
    }

    @Test
    void updateEmployee_shouldReturnUpdatedEmployee() {
        Long id = 1L;
        EmployeeRequest employeeRequest = new EmployeeRequest();
        EmployeeResponse employeeResponse = new EmployeeResponse();
        when(employeeDataService.updateEmployee(id, employeeRequest)).thenReturn(employeeResponse);

        ResponseEntity<EmployeeResponse> response = employeeDatabaseController.updateEmployee(id, employeeRequest);

        assertEquals(employeeResponse, response.getBody());
        verify(employeeDataService, times(1)).updateEmployee(id, employeeRequest);
    }

    @Test
    void deleteEmployee_shouldReturnSuccessMessage() {
        Long id = 1L;

        ResponseEntity<String> response = employeeDatabaseController.deleteEmployee(id);

        assertEquals("{ \"message\": \"Employee deleted successfully\" }", response.getBody());
        verify(employeeDataService, times(1)).deleteEmployee(id);
    }
}