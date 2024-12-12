package nl.com.ems.employeeservice.service;

 import lombok.AllArgsConstructor;
 import lombok.Setter;
 import nl.com.ems.employeeservice.employeedata.EmployeeDatabaseService;
 import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Setter
public class RoleService {

    private final EmployeeDatabaseService employeeDatabaseService;

    public String deleteRole(Long roleId) {
        return employeeDatabaseService.deleteRole(roleId);
    }
}
