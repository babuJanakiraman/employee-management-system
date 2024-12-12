package nl.com.ems.employeeservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.com.ems.employeeservice.employeedata.EmployeeDatabaseService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ProjectService {

    private final EmployeeDatabaseService employeeDatabaseService;

    public String assignProjectToEmployee(Long employeeId, Long projectId) {
        return employeeDatabaseService.assignProjectToEmployee(employeeId, projectId);
    }
}
