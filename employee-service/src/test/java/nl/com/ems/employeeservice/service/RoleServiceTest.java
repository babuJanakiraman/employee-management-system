package nl.com.ems.employeeservice.service;

import nl.com.ems.employeeservice.employeedata.EmployeeDatabaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RoleServiceTest {

    @Mock
    private EmployeeDatabaseService employeeDatabaseService;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteRole_Success() {
        Long roleId = 1L;
        String expectedResponse = "Role deleted successfully";

        when(employeeDatabaseService.deleteRole(1L)).thenReturn(expectedResponse);

        String actualResponse = roleService.deleteRole(roleId);

        assertEquals(expectedResponse, actualResponse);
        verify(employeeDatabaseService, times(1)).deleteRole(roleId);
    }

    @Test
    void testDeleteRole_InvalidRoleId() {
        Long roleId = -1L;
        String expectedResponse = "Invalid role ID";

        when(employeeDatabaseService.deleteRole(roleId)).thenReturn(expectedResponse);

        String actualResponse = roleService.deleteRole(roleId);

        assertEquals(expectedResponse, actualResponse);
        verify(employeeDatabaseService, times(1)).deleteRole(roleId);
    }

    @Test
    void testDeleteRole_Exception() {
        Long roleId = 1L;

        when(employeeDatabaseService.deleteRole(roleId)).thenThrow(new RuntimeException("Internal Server error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> roleService.deleteRole(roleId));

        assertEquals("Internal Server error", exception.getMessage());
        verify(employeeDatabaseService, times(1)).deleteRole(roleId);
    }
}