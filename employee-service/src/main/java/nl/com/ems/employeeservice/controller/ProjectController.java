package nl.com.ems.employeeservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.com.ems.employeeservice.api.ProjectApi;
import nl.com.ems.employeeservice.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class ProjectController implements ProjectApi {

    private final ProjectService projectService;

    @Override
    public ResponseEntity<String> assignProjectToEmployee(Long EmployeeId, Long projectId) {
        log.info("Assigning projectId : {} to employee with id: {}", projectId, EmployeeId);
        String response = projectService.assignProjectToEmployee(EmployeeId, projectId);
        return ResponseEntity.ok(response);
    }

}
