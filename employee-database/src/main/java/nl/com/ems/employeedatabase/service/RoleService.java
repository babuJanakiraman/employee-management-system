package nl.com.ems.employeedatabase.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.com.ems.employeedatabase.common.data.entity.Role;
import nl.com.ems.employeedatabase.common.data.repository.RoleRepository;
import nl.com.ems.employeedatabase.common.util.StoredProcedures;
import nl.com.ems.employeedatabase.exception.InternalServerException;
import nl.com.ems.employeedatabase.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class RoleService {

    private static final Long DEFAULT_EMPLOYEE_ID = 1L;
    private final DataSource dataSource;
    private final RoleRepository roleRepository;

    @Transactional
    public void deleteRole(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        if(role.isEmpty()) {
            log.error("Role with id {} not found", roleId);
            throw new ResourceNotFoundException("Role not found");
        }
        try (Connection conn = dataSource.getConnection()) {
            StoredProcedures.deleteRoleWithEmployees(conn, roleId, DEFAULT_EMPLOYEE_ID);
            log.info("Role with id {} deleted and projects reassigned to employee with id {}", roleId, DEFAULT_EMPLOYEE_ID);
        } catch (SQLException e) {
            log.error("Error deleting role with id {}", roleId, e);
            throw new InternalServerException("Error deleting role", e);
        }
    }
}