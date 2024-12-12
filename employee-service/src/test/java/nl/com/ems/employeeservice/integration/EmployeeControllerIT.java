package nl.com.ems.employeeservice.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class EmployeeControllerIT extends IntegrationTestConfig {

    @Test
    void createEmployee_success(){

        wireMockServer.stubFor(post(urlPathMatching("/api/employees"))
                    .withBasicAuth("user", "userpass")
                .withRequestBody(equalToJson(
                        """
                        {
                          "name": "John Doe",
                           "role_id": 1
                        }
                        """))
                .willReturn(aResponse()
                        .withBody("""
                                {
                                  "id": 1,
                                  "name": "John Doe",
                                  "role_id": 1
                                }
                                """)
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)));


        given()
                .auth().preemptive()
                .basic("user", "userpass")
                .header("role", "ADMIN")
                .contentType(ContentType.JSON)
                .body("""
                               {
                                     "firstname": "John",
                                     "surname": "Doe"
                                 }

          """
                )
                .when()
                .post("/employees")
                .then()
                .statusCode(201)
                .body("firstname", equalTo("John"))
                .body("surname", equalTo("Doe"))
                .body("role_id", equalTo(1));

    }

    @Test
    void getAllEmployees_shouldReturnAllEmployees() throws IOException {
        String response = Files.readString(Paths.get("src/test/resources/employees/employees.json"));
        wireMockServer.stubFor(get(urlEqualTo("/api/employees"))
                .withBasicAuth("user", "userpass")
                .willReturn(aResponse()
                        .withBody(response)
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)));

        given()
                .auth().preemptive()
                .basic("user", "userpass")
                .header("role", "USER")
                .contentType(ContentType.JSON)
                .when()
                .get("/employees")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2));
    }

    @Test
    void getEmployee_shouldReturnEmployee() throws IOException {
        String response = Files.readString(Paths.get("src/test/resources/employees/getEmployee.json"));
        wireMockServer.stubFor(get(urlEqualTo("/api/employees/1"))
                .withBasicAuth("user", "userpass")
                .willReturn(aResponse()
                        .withBody(response)
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)));

        given()
                .auth().preemptive()
                .basic("user", "userpass")
                .header("role", "USER")
                .contentType(ContentType.JSON)
                .when()
                .get("/employees/1")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("John"))
                .body("surname", equalTo("Doe"))
                .body("role_id", equalTo(1))
                .body("project_ids", equalTo(List.of(1,2)));


    }

    @Test
    void updateEmployee_shouldReturnUpdatedEmployee() throws IOException {
        String response = Files.readString(Paths.get("src/test/resources/employees/Updatedemployee.json"));
        wireMockServer.stubFor(put(urlEqualTo("/api/employees/1"))
                .withBasicAuth("user", "userpass")
                .withRequestBody(equalToJson(
                        """
                        {
                          "name": "Jane Doe",
                           "role_id": 1
                        }
                        """))
                .willReturn(aResponse()
                        .withBody(response)
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)));

        given()
                .auth().preemptive()
                .basic("user", "userpass")
                .header("role", "ADMIN")
                .contentType(ContentType.JSON)
                .body("""
                               {
                                     "firstname": "Jane",
                                     "surname": "Doe",
                                     "role_id": 1
                                 }

          """
                )
                .when()
                .put("/employees/1")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Jane"))
                .body("surname", equalTo("Doe"))
                .body("role_id", equalTo(1));
    }

    @Test
    void deleteEmployee_success() {
        wireMockServer.stubFor(WireMock.delete(urlEqualTo("/api/employees/1"))
                .withBasicAuth("user", "userpass")
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"message\": \"Employee deleted successfully\" }")));

        given()
                .auth().preemptive()
                .basic("user", "userpass")
                .header("role", "ADMIN")
                .contentType(ContentType.JSON)
                .when()
                .delete("/employees/1")
                .then()
                .statusCode(200)
                .body(equalTo("{ \"message\": \"Employee deleted successfully\" }"));
    }

}