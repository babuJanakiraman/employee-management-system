package nl.com.ems.employeeservice.service;

import nl.com.ems.employeeservice.common.EmployeeMapper;
import nl.com.ems.employeeservice.employeedata.EmployeeDatabaseService;
import nl.com.ems.employeeservice.employeedata.model.EmployeeRequest;
import nl.com.ems.employeeservice.employeedata.model.EmployeeResponse;
import nl.com.ems.employeeservice.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeDatabaseService employeeDatabaseService;
    @Mock
    private EmployeeMapper employeeMapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeService(employeeDatabaseService, employeeMapper);

    }

    @Test
    void createEmployee_withValidRole_createsEmployee() {
        Employee employee = new Employee();
        employee.setFirstname("John");
        employee.setSurname("Doe");

        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setName("John Doe");
        employeeRequest.setRoleId(1L);

        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setId(1L);
        employeeResponse.name("John Doe");
        employeeResponse.setRoleId(1L);

        when(employeeMapper.employeeToEmployeeRequest(employee)).thenReturn(employeeRequest);
        when(employeeDatabaseService.createEmployee(employeeRequest)).thenReturn(employeeResponse);

        when(employeeMapper.employeeResponseToEmployee(employeeResponse)).thenReturn(employee);
        Employee createdEmployee = employeeService.createEmployee("ADMIN", employee);


        assertEquals("John", createdEmployee.getFirstname());
        assertEquals("Doe", createdEmployee.getSurname());
        verify(employeeDatabaseService).createEmployee(employeeRequest);
    }

    @Test
    void createEmployee_withInvalidRole_throwsException() {
        Employee employee = new Employee();
        employee.setFirstname("John");
        employee.setSurname("Doe");

        assertThrows(IllegalArgumentException.class, () -> {
            employeeService.createEmployee("INVALID_ROLE", employee);
        });
    }

    @Test
    void getEmployeeById_withExistingEmployee_returnsEmployee() {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setName("John Doe");
        employeeResponse.setRoleId(1L);

        when(employeeDatabaseService.getEmployeeById(1L)).thenReturn(employeeResponse);
        when(employeeMapper.employeeResponseToEmployee(employeeResponse)).thenReturn(new Employee("John", "Doe"));

        Employee employee = employeeService.getEmployeeById(1L);

        assertEquals("John", employee.getFirstname());
        assertEquals("Doe", employee.getSurname());
    }

    @Test
    void getEmployeeById_withNonExistingEmployee_throwsException() {
        when(employeeDatabaseService.getEmployeeById(1L)).thenThrow(new RuntimeException("Employee not found"));

        assertThrows(RuntimeException.class, () -> {
            employeeService.getEmployeeById(1L);
        });
    }

    @Test
    void updateEmployee_withValidData_updatesEmployee() {
        Employee employee = new Employee();
        employee.setFirstname("John");
        employee.setSurname("Doe");
        employee.setRoleId(2L);

        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setName("John Doe");
        employeeRequest.setRoleId(2L);

        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setName("John Doe");
        employeeResponse.setRoleId(2L);

        when(employeeMapper.employeeToEmployeeRequest(employee)).thenReturn(employeeRequest);
        when(employeeDatabaseService.updateEmployee(1L, employeeRequest)).thenReturn(employeeResponse);
        when(employeeMapper.employeeResponseToEmployee(employeeResponse)).thenReturn(employee);

        Employee updatedEmployee = employeeService.updateEmployee(1L, "USER", employee);

        assertEquals("John", updatedEmployee.getFirstname());
        assertEquals("Doe", updatedEmployee.getSurname());
        assertEquals(2L, updatedEmployee.getRoleId());
        verify(employeeDatabaseService).updateEmployee(1L, employeeRequest);
    }

    @Test
    void testDeleteEmployee_Success() {
        Long employeeId = 1L;
        String expectedResponse = "Employee deleted successfully";

        when(employeeDatabaseService.deleteEmployee(employeeId)).thenReturn(expectedResponse);

        String actualResponse = employeeService.deleteEmployee(employeeId);

        assertEquals(expectedResponse, actualResponse);
        verify(employeeDatabaseService, times(1)).deleteEmployee(employeeId);
    }

    @Test
    void testDeleteEmployee_InvalidEmployeeId() {
        Long employeeId = -1L;
        String expectedResponse = "Invalid employee ID";

        when(employeeDatabaseService.deleteEmployee(employeeId)).thenReturn(expectedResponse);

        String actualResponse = employeeService.deleteEmployee(employeeId);

        assertEquals(expectedResponse, actualResponse);
        verify(employeeDatabaseService, times(1)).deleteEmployee(employeeId);
    }

    @Test
    void testDeleteEmployee_Exception() {
        Long employeeId = 1L;

        when(employeeDatabaseService.deleteEmployee(employeeId)).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            employeeService.deleteEmployee(employeeId);
        });

        assertEquals("Database error", exception.getMessage());
        verify(employeeDatabaseService, times(1)).deleteEmployee(employeeId);
    }

    @Test
    void deleteEmployee_withNonExistingEmployee_throwsException() {
        doThrow(new RuntimeException("Employee not found")).when(employeeDatabaseService).deleteEmployee(1L);

        assertThrows(RuntimeException.class, () -> {
            employeeService.deleteEmployee(1L);
        });
    }

@Test
void getAllEmployees_returnsListOfEmployees() {
        EmployeeResponse employeeResponse1 = new EmployeeResponse();
        employeeResponse1.setId(1L);
        employeeResponse1.setName("John Doe");
        employeeResponse1.setRoleId(1L);
    EmployeeResponse employeeResponse2 = new EmployeeResponse();
    employeeResponse1.setId(2L);
    employeeResponse1.setName("Jane Smith");
    employeeResponse1.setRoleId(1L);
    List<EmployeeResponse> employeeResponses = List.of(employeeResponse1, employeeResponse2);

    when(employeeDatabaseService.getAllEmployees()).thenReturn(employeeResponses);
    when(employeeMapper.employeeResponseToEmployee(employeeResponse1)).thenReturn(new Employee("John", "Doe"));
    when(employeeMapper.employeeResponseToEmployee(employeeResponse2)).thenReturn(new Employee("Jane", "Smith"));

    List<Employee> employees = employeeService.getAllEmployees();

    assertEquals(2, employees.size());
    assertEquals("John", employees.get(0).getFirstname());
    assertEquals("Doe", employees.get(0).getSurname());
    assertEquals("Jane", employees.get(1).getFirstname());
    assertEquals("Smith", employees.get(1).getSurname());
}

    @Test
    void getAllEmployees_returnsEmptyList() {
        when(employeeDatabaseService.getAllEmployees()).thenReturn(List.of());

        List<Employee> employees = employeeService.getAllEmployees();

        assertTrue(employees.isEmpty());
    }

}