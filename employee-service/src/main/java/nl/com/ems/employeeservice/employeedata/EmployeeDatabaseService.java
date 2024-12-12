package nl.com.ems.employeeservice.employeedata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.com.ems.employeeservice.employeedata.model.EmployeeResponse;
import nl.com.ems.employeeservice.employeedata.model.EmployeeRequest;
import nl.com.ems.employeeservice.exception.TechnicalException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
public class EmployeeDatabaseService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String EMPLOYEES = "/api/employees";
    private final RestClient restClient;

    @Retry(name = "employee-database")
    @CircuitBreaker(name = "employee-database")
    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        try {
            EmployeeResponse response = restClient.post()
                    .uri(String.format("%s", EMPLOYEES))
                    .body(employeeRequest)
                    .retrieve()
                    .body(EmployeeResponse.class);
            log.debug("Received response: {}", response);
            return response;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw parseErrorResponse(ex.getResponseBodyAsString());
        }
    }

    @Retry(name = "employee-database")
    @CircuitBreaker(name = "employee-database")
    public EmployeeResponse getEmployeeById(Long id) {
        log.debug("Getting employee by id: {}", id);
        try {
            EmployeeResponse response = restClient.get()
                    .uri(String.format("%s/%d", EMPLOYEES, id))
                    .retrieve()
                    .body(EmployeeResponse.class);
            log.debug("Received employee response: {}", response);
            return response;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw parseErrorResponse(ex.getResponseBodyAsString());
        }
    }

    @Retry(name = "employee-database")
    @CircuitBreaker(name = "employee-database")
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        log.debug("Updating employee with id: {}", id);
        try {
            EmployeeResponse response = restClient.put()
                    .uri(String.format("%s/%d", EMPLOYEES, id))
                    .body(request)
                    .retrieve()
                    .body(EmployeeResponse.class);
            log.debug("Received updated employee response: {}", response);
            return response;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw parseErrorResponse(ex.getResponseBodyAsString());
        }
    }

    @Retry(name = "employee-database")
    @CircuitBreaker(name = "employee-database")
    public String deleteEmployee(Long id) {
        log.debug("Deleting employee with id: {}", id);
        try {
            return restClient.delete()
                    .uri(String.format("%s/%d", EMPLOYEES, id))
                    .retrieve()
                    .body(String.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw parseErrorResponse(ex.getResponseBodyAsString());
        }
    }

    @Retry(name = "employee-database")
    @CircuitBreaker(name = "employee-database")
    public List<EmployeeResponse> getAllEmployees() {
        log.debug("Getting all employees");
        try {
            return restClient.get()
                    .uri(String.format("%s", EMPLOYEES))
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw parseErrorResponse(ex.getResponseBodyAsString());
        }
    }

    @Retry(name = "employee-database")
    @CircuitBreaker(name = "employee-database")
    public String assignProjectToEmployee(Long employeeId, Long projectId) {
        log.info("Assigning project to employee with id: {}", employeeId);
        try {
            return restClient.put()
                    .uri(String.format("/api/%d/projects/%d", employeeId, projectId))
                    .retrieve()
                    .body(String.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw parseErrorResponse(ex.getResponseBodyAsString());
        }
    }

    @Retry(name = "employee-database")
    @CircuitBreaker(name = "employee-database")
    public String deleteRole(Long roleId) {
        log.info("Deleting role with id: {}", roleId);
        try {
            return restClient.delete()
                    .uri(String.format("/api/roles/%d", roleId))
                    .retrieve()
                    .body(String.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw parseErrorResponse(ex.getResponseBodyAsString());
        }
    }

    private TechnicalException parseErrorResponse(String responseBody) {
        try {
            JsonNode rootNode = OBJECT_MAPPER.readTree(responseBody);
            int status = rootNode.path("status").asInt();
            String message = rootNode.path("message").asText();
            String detailedMessage = rootNode.path("detailedMessage").asText();
            return new TechnicalException(status, message, detailedMessage);
        } catch (Exception e) {
            return new TechnicalException(500, "Internal Server Error", "Error parsing response body");
        }
    }
}

