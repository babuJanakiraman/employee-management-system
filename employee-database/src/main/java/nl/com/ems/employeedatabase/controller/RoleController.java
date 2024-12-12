package nl.com.ems.employeedatabase.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.com.ems.employeedatabase.api.RoleApi;
import nl.com.ems.employeedatabase.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@AllArgsConstructor
public class RoleController implements RoleApi {

    private static final String DELETE_ROLE_200_RESPONSE = "{ \"message\": \"Role deleted successfully\" }";

    private final RoleService roleService;

    @Override
    public ResponseEntity<String> deleteRole(Long roleId) {
        log.info("Deleting role with id: {}", roleId);
        roleService.deleteRole(roleId);
        return ResponseEntity.ok(DELETE_ROLE_200_RESPONSE);
    }
}
