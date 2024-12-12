package nl.com.ems.employeeservice.service;

import nl.com.ems.employeeservice.employeedata.EmployeeDatabaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    @Mock
    private EmployeeDatabaseService employeeDatabaseService;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAssignProjectToEmployee_Success() {
        Long employeeId = 1L;
        Long projectId = 1L;
        String expectedResponse = "Project assigned successfully";

        when(employeeDatabaseService.assignProjectToEmployee(employeeId, projectId)).thenReturn(expectedResponse);

        String actualResponse = projectService.assignProjectToEmployee(employeeId, projectId);

        assertEquals(expectedResponse, actualResponse);
        verify(employeeDatabaseService, times(1)).assignProjectToEmployee(employeeId, projectId);
    }

    @Test
    void testAssignProjectToEmployee_InvalidEmployeeId() {
        Long employeeId = -1L;
        Long projectId = 1L;
        String expectedResponse = "Invalid employee ID";

        when(employeeDatabaseService.assignProjectToEmployee(employeeId, projectId)).thenReturn(expectedResponse);

        String actualResponse = projectService.assignProjectToEmployee(employeeId, projectId);

        assertEquals(expectedResponse, actualResponse);
        verify(employeeDatabaseService, times(1)).assignProjectToEmployee(employeeId, projectId);
    }

    @Test
    void testAssignProjectToEmployee_InvalidProjectId() {
        Long employeeId = 1L;
        Long projectId = -1L;
        String expectedResponse = "Invalid project ID";

        when(employeeDatabaseService.assignProjectToEmployee(employeeId, projectId)).thenReturn(expectedResponse);

        String actualResponse = projectService.assignProjectToEmployee(employeeId, projectId);

        assertEquals(expectedResponse, actualResponse);
        verify(employeeDatabaseService, times(1)).assignProjectToEmployee(employeeId, projectId);
    }

    @Test
    void testAssignProjectToEmployee_Exception() {
        Long employeeId = 1L;
        Long projectId = 1L;

        when(employeeDatabaseService.assignProjectToEmployee(employeeId, projectId)).thenThrow(new RuntimeException("Internal Server error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            projectService.assignProjectToEmployee(employeeId, projectId);
        });

        assertEquals("Internal Server error", exception.getMessage());
        verify(employeeDatabaseService, times(1)).assignProjectToEmployee(employeeId, projectId);
    }
}