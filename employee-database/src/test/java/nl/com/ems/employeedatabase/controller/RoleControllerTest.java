package nl.com.ems.employeedatabase.controller;

import nl.com.ems.employeedatabase.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
    void deleteRole_shouldReturnSuccessMessage() {
        Long roleId = 1L;

        ResponseEntity<String> response = roleController.deleteRole(roleId);

        assertEquals("{ \"message\": \"Role deleted successfully\" }", response.getBody());
        verify(roleService, times(1)).deleteRole(roleId);
    }

    @Test
    void deleteRole_shouldHandleNullRoleId() {
        Long roleId = null;

        ResponseEntity<String> response = roleController.deleteRole(roleId);

        assertEquals("{ \"message\": \"Role deleted successfully\" }", response.getBody());
        verify(roleService, times(1)).deleteRole(roleId);
    }

    @Test
    void deleteRole_shouldHandleNonExistentRoleId() {
        Long roleId = 999L;
        doThrow(new RuntimeException("Role not found")).when(roleService).deleteRole(roleId);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> roleController.deleteRole(roleId));
        assertEquals("Role not found", exception.getMessage());
        verify(roleService, times(1)).deleteRole(roleId);
    }
}