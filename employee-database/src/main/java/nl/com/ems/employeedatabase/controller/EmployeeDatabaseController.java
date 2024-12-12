package nl.com.ems.employeedatabase.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.com.ems.employeedatabase.api.EmployeeDatabaseApi;
import nl.com.ems.employeedatabase.model.EmployeeRequest;
import nl.com.ems.employeedatabase.model.EmployeeResponse;
import nl.com.ems.employeedatabase.service.EmployeeDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class EmployeeDatabaseController implements EmployeeDatabaseApi {

    private static final String DELETE_EMPLOYEE_200_RESPONSE = "{ \"message\": \"Employee deleted successfully\" }";

    private final EmployeeDataService employeeDataService;

    @Override
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        log.info("Getting all employees");
        return ResponseEntity.ok(employeeDataService.getAllEmployees());
    }

    @Override
    public ResponseEntity<EmployeeResponse> createEmployee(EmployeeRequest employee) {
        log.info("Creating employee");
        EmployeeResponse employeeResponse = employeeDataService.createEmployee(employee);
        return ResponseEntity.ok(employeeResponse);
    }

    @Override
    public ResponseEntity<EmployeeResponse> getEmployeeById(Long id) {
        log.info("Getting employee with id: {}", id);
        EmployeeResponse employeeResponse = employeeDataService.getEmployeeById(id);
        return ResponseEntity.ok(employeeResponse);
    }

    @Override
    public ResponseEntity<EmployeeResponse> updateEmployee(Long id, EmployeeRequest employee) {
        log.info("Updating employee with id: {}", id);
        EmployeeResponse updatedEmployee = employeeDataService.updateEmployee(id, employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @Override
    public ResponseEntity<String> deleteEmployee(Long id) {
        log.info("Deleting employee with id: {}", id);
        employeeDataService.deleteEmployee(id);
        return ResponseEntity.ok(DELETE_EMPLOYEE_200_RESPONSE);
    }

}
