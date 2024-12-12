package nl.com.ems.employeedatabase.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.com.ems.employeedatabase.common.data.entity.Employee;
import nl.com.ems.employeedatabase.common.data.entity.Project;
import nl.com.ems.employeedatabase.common.data.repository.EmployeeRepository;
import nl.com.ems.employeedatabase.common.data.repository.ProjectRepository;
import nl.com.ems.employeedatabase.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ProjectService {

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;

    public void assignProjectToEmployee(Long employeeId, Long projectId) {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + employeeId + " not found"));

            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Project with id " + projectId + " not found"));

            employee.getProjects().add(project);
            employeeRepository.save(employee);

    }
}