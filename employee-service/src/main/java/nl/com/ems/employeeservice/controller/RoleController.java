package nl.com.ems.employeeservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.com.ems.employeeservice.api.RoleApi;
import nl.com.ems.employeeservice.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class RoleController implements RoleApi {

    private final RoleService roleService;

    @Override
    public  ResponseEntity<String> deleteRole(Long roleId) {
        log.info("Deleting role with id: {}", roleId);
        String response = roleService.deleteRole(roleId);
        return ResponseEntity.ok(response);

    }
}
