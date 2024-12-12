package nl.com.ems.employeedatabase.common.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class StoredProcedures {

    public static void deleteRoleWithEmployees(Connection conn, long roleId, long defaultEmployeeId) throws SQLException {
        try (Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("UPDATE Employee_Project SET employee_id = " + defaultEmployeeId + " WHERE employee_id IN (SELECT id FROM Employee WHERE role_id = " + roleId + ")");

            stmt.executeUpdate("DELETE FROM Employee WHERE role_id = " + roleId);

            stmt.executeUpdate("DELETE FROM Role WHERE id = " + roleId);
        }
    }

}