package nl.com.ems.employeedatabase.service;

import nl.com.ems.employeedatabase.common.EmployeeDataMapper;
import nl.com.ems.employeedatabase.common.data.entity.Employee;
import nl.com.ems.employeedatabase.common.data.entity.Role;
import nl.com.ems.employeedatabase.common.data.repository.EmployeeRepository;
import nl.com.ems.employeedatabase.common.data.repository.RoleRepository;
import nl.com.ems.employeedatabase.exception.ResourceNotFoundException;
import nl.com.ems.employeedatabase.model.EmployeeRequest;
import nl.com.ems.employeedatabase.model.EmployeeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeDataServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private EmployeeDataMapper employeeDataMapper;

    @InjectMocks
    private EmployeeDataService employeeDataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEmployee_success() {
        EmployeeRequest request = new EmployeeRequest();
        request.setRoleId(1L);
        Role role = new Role();
        Employee employee = new Employee();
        Employee savedEmployee = new Employee();
        EmployeeResponse response = new EmployeeResponse();

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(employeeDataMapper.employeeRequestToEmployee(request)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(savedEmployee);
        when(employeeDataMapper.employeeToEmployeeResponse(savedEmployee)).thenReturn(response);

        EmployeeResponse result = employeeDataService.createEmployee(request);

        assertEquals(response, result);
        verify(roleRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void createEmployee_roleNotFound() {
        EmployeeRequest request = new EmployeeRequest();
        request.setRoleId(1L);

        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeDataService.createEmployee(request));

        verify(roleRepository, times(1)).findById(1L);
        verify(employeeRepository, times(0)).save(any(Employee.class));
    }

    @Test
    void getEmployeeById_success() {
        Employee employee = new Employee();
        EmployeeResponse response = new EmployeeResponse();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeDataMapper.employeeToEmployeeResponse(employee)).thenReturn(response);

        EmployeeResponse result = employeeDataService.getEmployeeById(1L);

        assertEquals(response, result);
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void getEmployeeById_employeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeDataService.getEmployeeById(1L));

        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void updateEmployee_success() {
        EmployeeRequest request = new EmployeeRequest();
        request.setRoleId(1L);
        Employee employee = new Employee();
        Role role = new Role();
        Employee updatedEmployee = new Employee();
        EmployeeResponse response = new EmployeeResponse();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(employeeRepository.save(employee)).thenReturn(updatedEmployee);
        when(employeeDataMapper.employeeToEmployeeResponse(updatedEmployee)).thenReturn(response);

        EmployeeResponse result = employeeDataService.updateEmployee(1L, request);

        assertEquals(response, result);
        verify(employeeRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void updateEmployee_employeeNotFound() {
        EmployeeRequest request = new EmployeeRequest();
        request.setRoleId(1L);

        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeDataService.updateEmployee(1L, request));

        verify(employeeRepository, times(1)).findById(1L);
        verify(roleRepository, times(0)).findById(anyLong());
        verify(employeeRepository, times(0)).save(any(Employee.class));
    }

    @Test
    void updateEmployee_roleNotFound() {
        EmployeeRequest request = new EmployeeRequest();
        request.setRoleId(1L);
        Employee employee = new Employee();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeDataService.updateEmployee(1L, request));

        verify(employeeRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).findById(1L);
        verify(employeeRepository, times(0)).save(any(Employee.class));
    }

    @Test
    void deleteEmployee_success() {
        doNothing().when(employeeRepository).deleteById(1L);

        employeeDataService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void getAllEmployees_success() {
        List<Employee> employees = List.of(new Employee());
        List<EmployeeResponse> responses = List.of(new EmployeeResponse());

        when(employeeRepository.findAll()).thenReturn(employees);
        when(employeeDataMapper.employeeToEmployeeResponse(any(Employee.class))).thenReturn(new EmployeeResponse());

        List<EmployeeResponse> result = employeeDataService.getAllEmployees();

        assertEquals(responses.size(), result.size());
        verify(employeeRepository, times(1)).findAll();
    }
}