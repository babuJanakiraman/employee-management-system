package nl.com.ems.employeedatabase.service;

import nl.com.ems.employeedatabase.common.data.entity.Employee;
import nl.com.ems.employeedatabase.common.data.entity.Project;
import nl.com.ems.employeedatabase.common.data.repository.EmployeeRepository;
import nl.com.ems.employeedatabase.common.data.repository.ProjectRepository;
import nl.com.ems.employeedatabase.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void assignProjectToEmployee_success() {
        Employee employee = new Employee();
        Project project = new Project();
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        projectService.assignProjectToEmployee(1L, 1L);

        verify(employeeRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(employee);
        assertTrue(employee.getProjects().contains(project));
    }

    @Test
    void assignProjectToEmployee_employeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> projectService.assignProjectToEmployee(1L, 1L));

        verify(employeeRepository, times(1)).findById(1L);
        verify(projectRepository, times(0)).findById(anyLong());
        verify(employeeRepository, times(0)).save(any(Employee.class));
    }

    @Test
    void assignProjectToEmployee_projectNotFound() {
        Employee employee = new Employee();
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> projectService.assignProjectToEmployee(1L, 1L));

        verify(employeeRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).findById(1L);
        verify(employeeRepository, times(0)).save(any(Employee.class));
    }

}