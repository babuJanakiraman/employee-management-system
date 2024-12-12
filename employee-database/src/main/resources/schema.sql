-- Create Role table
CREATE TABLE Role
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

-- Create Employee table
CREATE TABLE Employee
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    firstname VARCHAR(255) NOT NULL,
    surname   VARCHAR(255) NOT NULL,
    role_id   INT,
    FOREIGN KEY (role_id) REFERENCES Role (id),
    UNIQUE (firstname, surname, role_id)

);

-- Create Project table
CREATE TABLE Project
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL

);

-- Create Employee_Project join table for Many-to-Many relationship
CREATE TABLE Employee_Project
(
    employee_id INT,
    project_id  INT,
    PRIMARY KEY (employee_id, project_id),
    FOREIGN KEY (employee_id) REFERENCES Employee (id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES Project (id)
);


-- Add indexes for performance optimization
CREATE INDEX idx_employee_roleid ON Employee (role_id);
CREATE INDEX idx_employee_project_employee_id ON Employee_Project (employee_id);
CREATE INDEX idx_employee_project_project_id ON Employee_Project (project_id);

-- Stored procedure to delete a role and reassign projects to a default employee
-- CREATE PROCEDURE deleteRoleAndReassignProjects(
--     IN roleId INT,
--     IN defaultEmployeeId INT
-- )
-- BEGIN
--     -- Reassign projects to the default employee
-- UPDATE projects
-- SET employee_id = defaultEmployeeId
-- WHERE employee_id IN (SELECT id FROM employees WHERE role_id = roleId);
--
-- -- Delete employees associated with the role
-- DELETE FROM employees WHERE role_id = roleId;
--
-- -- Delete the role
-- DELETE FROM roles WHERE id = roleId;
-- END;

