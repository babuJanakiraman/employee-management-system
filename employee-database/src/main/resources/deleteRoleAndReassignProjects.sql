CREATE PROCEDURE deleteRoleAndReassignProjects(
    IN roleId INT,
    IN defaultEmployeeId INT
)
BEGIN
    -- Reassign projects to the default employee
UPDATE projects
SET employee_id = defaultEmployeeId
WHERE employee_id IN (SELECT id FROM employees WHERE role_id = roleId);

-- Delete employees associated with the role
DELETE FROM employees WHERE role_id = roleId;
END;