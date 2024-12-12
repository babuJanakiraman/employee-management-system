package nl.com.ems.employeedatabase.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.com.ems.employeedatabase.common.EmployeeDataMapper;
import nl.com.ems.employeedatabase.common.data.entity.Employee;
import nl.com.ems.employeedatabase.common.data.entity.Role;
import nl.com.ems.employeedatabase.common.data.repository.EmployeeRepository;
import nl.com.ems.employeedatabase.common.data.repository.RoleRepository;
import nl.com.ems.employeedatabase.exception.ResourceNotFoundException;
import nl.com.ems.employeedatabase.model.EmployeeRequest;
import nl.com.ems.employeedatabase.model.EmployeeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeDataService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final EmployeeDataMapper employeeDataMapper;

    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
            Employee employee = employeeDataMapper.employeeRequestToEmployee(employeeRequest);
            Role role = roleRepository.findById(employeeRequest.getRoleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
            employee.setRole(role);
            Employee savedEmployee = employeeRepository.save(employee);
            return employeeDataMapper.employeeToEmployeeResponse(savedEmployee);

    }

    public EmployeeResponse getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeDataMapper::employeeToEmployeeResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeDetails) {
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
            Role role = roleRepository.findById(employeeDetails.getRoleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
            employee.setFirstname(employeeDataMapper.getFirstname(employeeDetails.getName()));
            employee.setSurname(employeeDataMapper.getSurname(employeeDetails.getName()));
            employee.setRole(role);
            return employeeDataMapper.employeeToEmployeeResponse(employeeRepository.save(employee));
    }

    public void deleteEmployee(Long id) {
            employeeRepository.deleteById(id);
    }

    public List<EmployeeResponse> getAllEmployees() {
            return toEmployeeResponseList(employeeRepository.findAll());

    }

    private List<EmployeeResponse> toEmployeeResponseList(List<Employee> employees) {
        return employees.stream()
                .map(employeeDataMapper::employeeToEmployeeResponse)
                .collect(Collectors.toList());
    }
}