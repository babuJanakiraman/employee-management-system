package nl.com.ems.employeedatabase.integration;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class ProjectControllerIT extends IntegrationTestConfig {

    @Test
    void assignProjectToEmployee_shouldReturnSuccessMessage() {
        Long employeeId = 2L;
        Long projectId = 1L;

        given()
                .auth().preemptive()
                .basic("user", "userpass")
                .contentType(ContentType.JSON)
                .when()
                .put("/api/{employeeId}/projects/{projectId}", employeeId, projectId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo("Project assigned to employee successfully"));
    }

    @Test
    void assignProjectToEmployee_withWrongCredentials() {
        Long employeeId = 2L;
        Long projectId = 1L;

        given()
                .auth().preemptive()
                .basic("user", "user")
                .contentType(ContentType.JSON)
                .when()
                .put("/api/{employeeId}/projects/{projectId}", employeeId, projectId)
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void assignProjectToEmployee_shouldHandleNullEmployeeId() {
        Long projectId = 1L;

        given()
                .auth().preemptive()
                .basic("user", "userpass")
                .contentType(ContentType.JSON)
                .when()
                .put("/api/{employeeId}/projects/{projectId}", " ", projectId)
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body("message", equalTo("Internal server error"))
                .body("detailedMessage", equalTo("Required URI template variable 'employeeId' for method parameter type Long is present but converted to null"));
    }

    @Test
    void assignProjectToEmployee_shouldHandleNullProjectId() {
        Long employeeId = 1L;

        given()
                .auth().preemptive()
                .basic("user", "userpass")
                .contentType(ContentType.JSON)
                .when()
                .put("/api/{employeeId}/projects/{projectId}", employeeId, " ")
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body("message", equalTo("Internal server error"))
                .body("detailedMessage", equalTo("Required URI template variable 'projectId' for method parameter type Long is present but converted to null"));
    }

    @Test
    void assignProjectToEmployee_shouldHandleBothIdsNull() {
        given()
                .auth().preemptive()
                .basic("user", "userpass")
                .contentType(ContentType.JSON)
                .when()
                .put("/api/{employeeId}/projects/{projectId}", " ", " ")
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body("message", equalTo("Internal server error"))
                .body("detailedMessage", equalTo("Required URI template variable 'employeeId' for method parameter type Long is present but converted to null"));
    }
}