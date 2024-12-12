package nl.com.ems.employeedatabase.controller;

import nl.com.ems.employeedatabase.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void assignProjectToEmployee_shouldReturnSuccessMessage() {
        Long employeeId = 1L;
        Long projectId = 1L;

        ResponseEntity<String> response = projectController.assignProjectToEmployee(employeeId, projectId);

        assertEquals("{ \"message\": \"Project assigned to employee successfully\" }", response.getBody());
        verify(projectService, times(1)).assignProjectToEmployee(employeeId, projectId);
    }

    @Test
    void assignProjectToEmployee_shouldHandleNullEmployeeId() {
        Long employeeId = null;
        Long projectId = 1L;

        ResponseEntity<String> response = projectController.assignProjectToEmployee(employeeId, projectId);

        assertEquals("{ \"message\": \"Project assigned to employee successfully\" }", response.getBody());
        verify(projectService, times(1)).assignProjectToEmployee(employeeId, projectId);
    }

    @Test
    void assignProjectToEmployee_shouldHandleNullProjectId() {
        Long employeeId = 1L;
        Long projectId = null;

        ResponseEntity<String> response = projectController.assignProjectToEmployee(employeeId, projectId);

        assertEquals("{ \"message\": \"Project assigned to employee successfully\" }", response.getBody());
        verify(projectService, times(1)).assignProjectToEmployee(employeeId, projectId);
    }

    @Test
    void assignProjectToEmployee_shouldHandleBothIdsNull() {
        Long employeeId = null;
        Long projectId = null;

        ResponseEntity<String> response = projectController.assignProjectToEmployee(employeeId, projectId);

        assertEquals("{ \"message\": \"Project assigned to employee successfully\" }", response.getBody());
        verify(projectService, times(1)).assignProjectToEmployee(employeeId, projectId);
    }
}