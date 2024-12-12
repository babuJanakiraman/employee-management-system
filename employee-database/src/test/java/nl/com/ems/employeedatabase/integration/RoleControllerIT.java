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
class RoleControllerIT extends IntegrationTestConfig {

    @Test
    void deleteRole_shouldReturnSuccessMessage() {
        Long roleId = 1L;

        given()
                .auth().preemptive()
                .basic("user", "userpass")
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/roles/{roleId}", roleId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo("Role deleted successfully"));
    }

    @Test
    void deleteRole_shouldHandleInvalidRoleId() {
        Long roleId = 999L;

        given()
                .auth().preemptive()
                .basic("user", "userpass")
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/roles/{roleId}", roleId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("Resource not found"))
                .body("detailedMessage", equalTo("Role not found"));
    }

    @Test
    void deleteRole_shouldHandleUnauthorizedAccess() {
        Long roleId = 1L;

        given()
                .auth().preemptive()
                .basic("user", "wrongpass")
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/roles/{roleId}", roleId)
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}