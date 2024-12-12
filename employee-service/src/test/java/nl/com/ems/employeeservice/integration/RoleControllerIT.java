package nl.com.ems.employeeservice.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class RoleControllerIT extends IntegrationTestConfig {

    @Test
    public void assignRoleToEmployee_success() {
        wireMockServer.stubFor(delete(urlEqualTo("/api/roles/1"))
                .withBasicAuth("user", "userpass")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"message\": \"Role deleted successfully\" }")));

        given()
                .auth().preemptive()
                .basic("user", "userpass")
                .when()
                .delete("/roles/1")
                .then()
                .statusCode(200)
                .assertThat()
                .body("message", equalTo("Role deleted successfully"));


    }

    @Test
    public void deleteRole_invalidRoleId() {
        wireMockServer.stubFor(delete(urlEqualTo("/api/roles/999"))
                .withBasicAuth("user", "userpass")
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "status": 404,
                                    "message": "Resource not found",
                                    "detailedMessage": "Role not found"
                                }
                                """)));

        given()
                .auth().preemptive()
                .basic("user", "userpass")
                .when()
                .delete("/roles/999")
                .then()
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("Resource not found"))
                .body("detailedMessage", equalTo("Role not found"));
    }

    @Test
    public void deleteRole_unauthorized() {
        wireMockServer.stubFor(delete(urlEqualTo("/api/roles/1"))
                .withBasicAuth("user", "wrongpass")
                .willReturn(aResponse()
                        .withStatus(401)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"message\": \"Unauthorized\" }")));

        given()
                .auth().preemptive()
                .basic("user", "wrongpass")
                .when()
                .delete("/roles/1")
                .then()
                .statusCode(401);

    }
}
