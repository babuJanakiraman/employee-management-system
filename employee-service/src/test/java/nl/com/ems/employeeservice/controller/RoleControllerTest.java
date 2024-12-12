package nl.com.ems.employeeservice.controller;

import nl.com.ems.employeeservice.service.RoleService;
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

class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteRole_success() {
        when(roleService.deleteRole(anyLong())).thenReturn("Role deleted successfully");

        ResponseEntity<String> response = roleController.deleteRole(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Role deleted successfully", response.getBody());
    }

    @Test
    void deleteRole_failure() {
        when(roleService.deleteRole(anyLong())).thenReturn("Failed to delete role");

        ResponseEntity<String> response = roleController.deleteRole(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Failed to delete role", response.getBody());
    }
}