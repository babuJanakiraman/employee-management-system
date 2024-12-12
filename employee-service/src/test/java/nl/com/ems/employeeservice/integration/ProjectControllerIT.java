package nl.com.ems.employeeservice.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class ProjectControllerIT extends IntegrationTestConfig {

    @Test
    public void assignProjectToEmployee_success() {
        wireMockServer.stubFor(put(urlEqualTo("/api/1/projects/1"))
                .withBasicAuth("user", "userpass")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"message\": \"Project assigned to employee successfully\" }")));

        given()
                .auth().preemptive()
                .basic("user", "userpass")
                .contentType("application/json")
                .when()
                .put("/1/projects/1")
                .then()
                .statusCode(200)
                .assertThat()
                .body("message", equalTo("Project assigned to employee successfully"));
    }

    @Test
    public void assignProjectToEmployee_invalidEmployeeId() {
        wireMockServer.stubFor(put(urlEqualTo("/api/999/projects/1"))
                .withBasicAuth("user", "userpass")
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "status": 404,
                                    "message": "Resource not found",
                                    "detailedMessage": "Employee with id 999 not found"
                                }
                                """)));

        given()
                .auth().preemptive()
                .basic("user", "userpass")
                .contentType("application/json")
                .when()
                .put("/999/projects/1")
                .then()
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("Resource not found"))
                .body("detailedMessage", equalTo("Employee with id 999 not found"));
    }

    @Test
    public void assignProjectToEmployee_invalidProjectId() {
        wireMockServer.stubFor(put(urlEqualTo("/api/1/projects/999"))
                .withBasicAuth("user", "userpass")
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "status": 404,
                                    "message": "Resource not found",
                                    "detailedMessage": "Project with id 999 not found"
                                }
                                """)));

        given()
                .auth().preemptive()
                .basic("user", "userpass")
                .contentType("application/json")
                .when()
                .put("/1/projects/999")
                .then()
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("Resource not found"))
                .body("detailedMessage", equalTo("Project with id 999 not found"));

    }

    @Test
    public void assignProjectToEmployee_invalidEmployeeAndProjectId() {
        wireMockServer.stubFor(put(urlEqualTo("/api/999/projects/999"))
                .withBasicAuth("user", "userpass")
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "status": 404,
                                    "message": "Resource not found",
                                    "detailedMessage": "Employee with id 999 not found"
                                }
                                """)));

        given()
                .auth().preemptive()
                .basic("user", "userpass")
                .contentType("application/json")
                .when()
                .put("/999/projects/999")
                .then()
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("Resource not found"))
                .body("detailedMessage", equalTo("Employee with id 999 not found"));

    }

    @Test
    public void assignProjectToEmployee_unauthorized() {
        wireMockServer.stubFor(put(urlEqualTo("/api/1/projects/1"))
                .withBasicAuth("user", "wrongpass")
                .willReturn(aResponse()
                        .withStatus(401)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"message\": \"Unauthorized\" }")));

        given()
                .auth().preemptive()
                .basic("user", "wrongpass")
                .contentType("application/json")
                .when()
                .put("/1/projects/1")
                .then()
                .statusCode(401);

    }
}