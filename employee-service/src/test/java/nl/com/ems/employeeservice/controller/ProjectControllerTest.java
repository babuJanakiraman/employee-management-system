package nl.com.ems.employeeservice.controller;

import nl.com.ems.employeeservice.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

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
    void assignProjectToEmployee_success() {
        when(projectService.assignProjectToEmployee(anyLong(), anyLong())).thenReturn("Project assigned successfully");

        ResponseEntity<String> response = projectController.assignProjectToEmployee(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Project assigned successfully", response.getBody());
    }

    @Test
    void assignProjectToEmployee_failure() {
        when(projectService.assignProjectToEmployee(anyLong(), anyLong())).thenReturn("Failed to assign project");

        ResponseEntity<String> response = projectController.assignProjectToEmployee(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Failed to assign project", response.getBody());
    }
}