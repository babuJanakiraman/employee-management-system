# Employee Management System

## Overview
The Employee Management System is a Spring Boot application designed to manage employee data. It provides RESTful APIs for creating, updating, retrieving, and deleting employee records. The system also includes resilience features such as retries and circuit breakers using Resilience4j.

## Endpoints
The following endpoints are available in the Employee Management System:
#### Create Employee
- **URL:** `/employees`
- **Headers:** `Role: ADMIN`
- **Method:** `POST`
- **Description:** Creates a new employee.
- **Request Body:**
  ```json
  {
    "firstname": "John",
    "surname": "Doe",
  }
  ```
- **Response:**
  ```json
  {
    "id": 1,
    "firstname": "John",
    "surname": "Doe",
    "roleId": 1
  }
  ```

#### Get Employee by ID
- **URL:** `/employees/{id}`
- **Method:** `GET`
- **Description:** Retrieves an employee by ID.
- **Response:**
  ```json
  {
    "id": 1,
    "firstname": "John",
    "surname": "Doe",
    "roleId": 1,
  "project_ids": [
        2,
        1
    ]
  }
  ```

#### Update Employee
- **URL:** `/employees/{id}`
- **Headers:** `Role: USER`
- **Method:** `PUT`
- **Description:** Updates an existing employee.
- **Request Body:**
  ```json
  {
    "firstname": "John",
    "surname": "Doe"
  }
  ```
- **Response:**
  ```json
  {
    "id": 1,
    "firstname": "John",
    "surname": "Doe",
    "roleId": 2
  }
  ```

#### Delete Employee
- **URL:** `/employees/{id}`
- **Headers:** `Role: ADMIN`
- **Method:** `DELETE`
- **Description:** Deletes an employee by ID.
- **Response:**
  ```json
  {
    "message": "Employee deleted successfully"
  }
  ```
#### Get All Employees
- **URL:** `/employees`
- **Method:** `GET`
- **Description:** Retrieves all employees.
- **Response:**
  ```json
  [
    {
      "id": 1,
      "firstname": "John",
      "surname": "Doe",
      "roleId": 1
    },
    {
      "id": 2,
      "firstname": "Jane",
      "surname": "Smith",
      "roleId": 2
    }
  ]
  ```

#### Assign Project to Employee
- **URL:** `/{employeeId}/projects/{projectId}`
- **Method:** `PUT`
- **Description:** Assigns a project to an employee.
- **Response:**
  ```json
  {
    "message": "Project assigned to employee successfully"
  }
  ```
#### Delete Role
- **URL:** `/roles/{id}`
- **Method:** `DELETE`
- **Description:** Deletes a role by ID.
- **Response:**
  ```json
  {
    "message": "Role deleted successfully"
  }
  ```
## Features
- Create, update, retrieve, and delete employee records
- Assign projects to employees
- Delete role and associated employees
- Resilience features with retries and circuit breakers
- H2 in-memory database for development and testing
- Swagger UI for API documentation

## Technologies Used
- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Resilience4j
- Swagger
- Docker

## Prerequisites
- Java 17
- Maven
- Docker (for containerization)

## Getting Started

### Clone the Repository
```sh
git clone https://github.com/babuJanakiraman/employee-management-system.git
cd employee-management-system
```

### Build the Project
```sh
mvn clean install
```

### Run the Application
Navigate to each module(employee-service & employee-database) directory and run
```sh
mvn spring-boot:run
```

### Access H2 Console
- URL: `http://localhost:8200/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

### Spring Security Basic Authentication
Below credentials are used for basic authentication for both employee service and employee database service:
- Username: `user`
- Password: `userpass`

### Access Swagger UI
#### Employee Service:
- URL: `http://localhost:8100/swagger-ui/index.html`
#### Employee database service:
- URL: `http://localhost:8200/swagger-ui/index.html`

## Configuration
Configuration properties are defined in the `application.yml` files located in the `src/main/resources` directory.

### `application.yml` for Employee Service
```yaml
server:
    port: 8100

spring:
    application:
        name: employee-service
    security:
        user:
            name: user
            password: userpass

management:
    endpoint:
        health:
            show-details: always
    endpoints:
        web:
            exposure:
                include: health
    health:
        circuitbreakers:
            enabled: true

springdoc:
    api-docs:
        path: /v3/api-docs
    swagger-ui:
        path: /swagger-ui.html

employeeDatabaseService:
    connectionTimeoutInMillis: 300
    keepAliveStrategy: 5000
    maxHttpPoolSize: 20
    readTimeoutInMillis: 3000
    url: http://localhost:8200
    username: user
    password: userpass

resilience4j:
    circuitbreaker:
        instances:
            employee-database-service:
                failure-rate-threshold: 50
                max-wait-duration-in-half-open-state: 3000
                minimumNumberOfCalls: 10
                permitted-number-of-calls-in-half-open-state: 4
                register-health-indicator: true
                sliding-window-size: 10
                sliding-window-type: COUNT_BASED
                wait-duration-in-open-state: 5000
    retry:
        instances:
            employee-database:
                max-attempts: 3
                wait-duration: 1s
                retry-exceptions:
                    - org.springframework.web.client.HttpServerErrorException
```

### `application.yml` for Employee Database
```yaml
server:
    port: 8200
spring:
    application:
        name: employee-database
    datasource:
        driverClassName: org.h2.Driver
        password: abc123
        url: jdbc:h2:mem:testdb
        username: sa
    h2:
        console:
            enabled: true
            path: /h2-console
            settings:
                web-allow-others: true
    jpa:
        hibernate:
            ddl-auto: update
    security:
        user:
            name: user
            password: userpass
springdoc:
    api-docs:
        path: /v3/api-docs
    swagger-ui:
        path: /swagger-ui.html
```

### `schema.sql` for Employee Database
```sql
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


-- Indexes for performance optimization
CREATE INDEX idx_employee_roleid ON Employee (role_id);
CREATE INDEX idx_employee_project_employee_id ON Employee_Project (employee_id);
CREATE INDEX idx_employee_project_project_id ON Employee_Project (project_id);

```

## Running with Docker
### Build Docker Image
```sh
docker build -t employee-service .
docker build -t employee-database .

```

### Run Docker Container
Update the application.yml of the employee-service to use the container name of the employee-database service.
```yaml
employeeDatabaseService:
url: http://employee-database:8200
```

```sh
docker run -p 8100:8100 employee-service
docker run -p 8200:8200 employee-database
```

## Testing
1. Navigate to the employee-service / employee-database project directory.
2. Run `mvn test` to execute the unit tests.
3. Run `mvn verify` to execute the integration tests.

## Additional Notes
1. **Stored** **Procedure** : I have tried to implement the Stored Procedure for delete role, but it is not working as expected. Seems like the issue is with the H2 database. its not supporting the stored procedure with sql script.So I have implemented the delete role with prepared statement.
2. **Retry** : Due to time constraint, I have implemented the retry mechanism with Resilience4j only. I have not implemented in a way it's said in the requirement(with storing retryCount in repo and scheduler).


## License
This project is licensed under the MIT License. See the `LICENSE` file for details.
