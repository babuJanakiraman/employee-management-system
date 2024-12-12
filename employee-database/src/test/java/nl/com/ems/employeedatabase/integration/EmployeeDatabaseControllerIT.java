package nl.com.ems.employeedatabase.integration;

import io.restassured.http.ContentType;
import nl.com.ems.employeedatabase.model.EmployeeRequest;
import nl.com.ems.employeedatabase.model.EmployeeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class EmployeeDatabaseControllerIT extends IntegrationTestConfig {

    @Test
    void getAllEmployees_shouldReturnListOfEmployees() {
        List<EmployeeResponse> employees = given()
                .auth().preemptive()
                .basic("user", "userpass")
                .when()
                .get("/api/employees")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", EmployeeResponse.class);

    }

    @Test
    void createEmployee_shouldReturnCreatedEmployee() {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setName("John Doe");
        employeeRequest.setRoleId(1L);

        EmployeeResponse employeeResponse = given()
                .auth().preemptive()
                .basic("user", "userpass")
                .contentType(ContentType.JSON)
                .body(employeeRequest)
                .when()
                .post("/api/employees")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(EmployeeResponse.class);

        assertEquals("John Doe", employeeResponse.getName());
        assertEquals(1L, employeeResponse.getRoleId());
    }

    @Test
    void getEmployeeById_shouldReturnEmployee() {
        Long id = 2L;

        given()
                .auth().preemptive()
                .basic("user", "userpass")
                .when()
                .get("/api/employees/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Jane Doe"))
                .body("role_id", equalTo(2));
    }

    @Test
    void updateEmployee_shouldReturnUpdatedEmployee() {
        Long id = 3L;
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setName("Glenn Maxwell");
        employeeRequest.setRoleId(2L);

        EmployeeResponse employeeResponse = given()
                .auth().preemptive()
                .basic("user", "userpass")
                .contentType(ContentType.JSON)
                .body(employeeRequest)
                .when()
                .put("/api/employees/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(EmployeeResponse.class);

        assertEquals("Glenn Maxwell", employeeResponse.getName());
        assertEquals(2L, employeeResponse.getRoleId());
    }

    @Test
    void deleteEmployee_shouldReturnSuccessMessage() {
        Long id = 2L;
        given()
                .auth().preemptive()
                .basic("user", "userpass")
                .when()
                .delete("/api/employees/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo("Employee deleted successfully"));
    }
}