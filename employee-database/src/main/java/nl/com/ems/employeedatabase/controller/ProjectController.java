package nl.com.ems.employeedatabase.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.com.ems.employeedatabase.api.ProjectApi;
import nl.com.ems.employeedatabase.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class ProjectController implements ProjectApi {

    private static final String ASSIGN_PROJECT_200_RESPONSE = "{ \"message\": \"Project assigned to employee successfully\" }";

    private final ProjectService projectService;

    @Override
    public ResponseEntity<String> assignProjectToEmployee(Long EmployeeId, Long projectId) {
        log.info("Assigning project to employee with id: {}", EmployeeId);
        projectService.assignProjectToEmployee(EmployeeId, projectId);
        return ResponseEntity.ok(ASSIGN_PROJECT_200_RESPONSE);
    }


}
