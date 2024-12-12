package nl.com.ems.employeeservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.com.ems.employeeservice.api.EmployeeApi;
import nl.com.ems.employeeservice.model.Employee;
import nl.com.ems.employeeservice.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
@Slf4j
public class EmployeeController implements EmployeeApi {


    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees(String role) {
        log.info("Getting all employees");
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(String role, Employee employee) {
        log.info("Creating employee");
            Employee createdEmployee = employeeService.createEmployee(role, employee);
            return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Employee> getEmployee(Long id,  String role) {
        log.info("Getting employee by id: {}", id);
        Employee employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> updateEmployee(Long id, String role, Employee employee) {
        log.info("Updating employee by id: {}", id);
        Employee updatedEmployee = employeeService.updateEmployee(id, role, employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteEmployee(Long id, String role) {
        log.info("Deleting employee by id: {}", id);
            String response = employeeService.deleteEmployee(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
