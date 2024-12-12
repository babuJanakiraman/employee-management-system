-- Insert default roles
INSERT INTO Role (name) VALUES ('ADMIN');
INSERT INTO Role (name) VALUES ('USER');
INSERT INTO Role (name) VALUES ('MANAGER');
INSERT INTO Role (name) VALUES ('DEFAULT');

-- Insert default employees
INSERT INTO Employee (firstname, surname, role_id) VALUES ('Default', 'Employee', 4);
INSERT INTO Employee (firstname, surname, role_id) VALUES ('Jane', 'Doe', 2);
INSERT INTO Employee (firstname, surname, role_id) VALUES ('Tom', 'Smith', 1);
INSERT INTO Employee (firstname, surname, role_id) VALUES ('Jerry', 'Smith', 2);

-- Insert default projects
INSERT INTO Project (name) VALUES ('Project 1');
INSERT INTO Project (name) VALUES ('Project 2');
INSERT INTO Project (name) VALUES ('Project 3');
INSERT INTO Project (name) VALUES ('Project 4');