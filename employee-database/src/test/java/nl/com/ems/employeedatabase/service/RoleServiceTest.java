package nl.com.ems.employeedatabase.service;

import nl.com.ems.employeedatabase.common.data.entity.Role;
import nl.com.ems.employeedatabase.common.data.repository.RoleRepository;
import nl.com.ems.employeedatabase.exception.InternalServerException;
import nl.com.ems.employeedatabase.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private Connection connection;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(dataSource.getConnection()).thenReturn(connection);
    }

   // @Test
    void deleteRole_success() throws SQLException {
        Role role = new Role();
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        PreparedStatement ps = connection.prepareStatement("DELETE FROM roles WHERE id = ?");
        if (ps != null) {
            ps.setLong(1, 1L);
        } else {
            throw new SQLException("Failed to create PreparedStatement");
        }

        roleService.deleteRole(1L);

        verify(roleRepository, times(1)).findById(1L);
        verify(connection, times(1)).close();
    }

    @Test
    void deleteRole_roleNotFound() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roleService.deleteRole(1L));

        verify(roleRepository, times(1)).findById(1L);
    }

    //@Test
    void deleteRole_sqlException() throws SQLException {
        Role role = new Role();
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        doThrow(new SQLException()).when(connection).close();

        assertThrows(InternalServerException.class, () -> roleService.deleteRole(1L));

        verify(roleRepository, times(1)).findById(1L);
        verify(connection, times(1)).close();
    }
}