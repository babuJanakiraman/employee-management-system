package nl.com.ems.employeeservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import nl.com.ems.employeeservice.common.EmployeeMapper;
import nl.com.ems.employeeservice.employeedata.EmployeeDatabaseService;
import nl.com.ems.employeeservice.employeedata.model.EmployeeRequest;
import nl.com.ems.employeeservice.employeedata.model.EmployeeResponse;
import nl.com.ems.employeeservice.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeDatabaseService employeeDatabaseService;
    private final EmployeeMapper employeeMapper;

    public Employee createEmployee(String role, Employee employee) {
        EmployeeRequest employeeRequest = employeeMapper.employeeToEmployeeRequest(employee);
        employeeRequest.setRoleId(mapRoleToRoleId(role));
        EmployeeResponse employeeResponse = employeeDatabaseService.createEmployee(employeeRequest);
        return employeeMapper.employeeResponseToEmployee(employeeResponse);
    }

    public Employee getEmployeeById(Long id) {
        EmployeeResponse employeeResponse = employeeDatabaseService.getEmployeeById(id);
        return employeeMapper.employeeResponseToEmployee(employeeResponse);
    }

    public Employee updateEmployee(Long id, String role, Employee employee) {
        EmployeeRequest employeeRequest = employeeMapper.employeeToEmployeeRequest(employee);
        employeeRequest.setRoleId(mapRoleToRoleId(role));
        EmployeeResponse employeeResponse = employeeDatabaseService.updateEmployee(id, employeeRequest);
        return employeeMapper.employeeResponseToEmployee(employeeResponse);
    }


    public String deleteEmployee(Long id) {
        return employeeDatabaseService.deleteEmployee(id);
    }

    public List<Employee> getAllEmployees() {
        List<EmployeeResponse> employeeResponses = employeeDatabaseService.getAllEmployees();
        return toEmployees(employeeResponses);
    }

    private List<Employee> toEmployees(List<EmployeeResponse> employeeResponses) {
        return employeeResponses.stream()
                .map(employeeMapper::employeeResponseToEmployee)
                .collect(Collectors.toList());
    }
    private Long mapRoleToRoleId(String role) {
        return switch (role) {
            case "ADMIN" -> 1L;
            case "USER" -> 2L;
            case "MANAGER" -> 3L;
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }


}