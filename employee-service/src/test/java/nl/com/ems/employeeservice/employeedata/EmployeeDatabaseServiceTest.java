package nl.com.ems.employeeservice.employeedata;

import nl.com.ems.employeeservice.employeedata.model.EmployeeRequest;
import nl.com.ems.employeeservice.employeedata.model.EmployeeResponse;
import nl.com.ems.employeeservice.exception.TechnicalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class EmployeeDatabaseServiceTest {

    @InjectMocks
    private EmployeeDatabaseService employeeDatabaseService;

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEmployeeSuccessfully() {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        EmployeeResponse employeeResponse = new EmployeeResponse();
        Mockito.when(restClient.post()).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.uri(any(String.class), any(Object[].class))).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.body(any(EmployeeRequest.class))).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.body(EmployeeResponse.class)).thenReturn(employeeResponse);

        EmployeeResponse response = employeeDatabaseService.createEmployee(employeeRequest);

        assertEquals(employeeResponse, response);
        Mockito.verify(restClient, times(1)).post();
        Mockito.verify(requestBodyUriSpec, times(1)).uri(any(String.class), any(Object[].class));
        Mockito.verify(requestBodyUriSpec, times(1)).body(any(EmployeeRequest.class));
        Mockito.verify(requestBodyUriSpec.retrieve(), times(1)).body(EmployeeResponse.class);
    }


    @Test
    void getEmployeeByIdSuccessfully() {
        Long employeeId = 1L;
        EmployeeResponse employeeResponse = new EmployeeResponse();
        Mockito.when(restClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.uri(any(String.class), any(Object[].class))).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.body(EmployeeResponse.class)).thenReturn(employeeResponse);

        EmployeeResponse response = employeeDatabaseService.getEmployeeById(employeeId);

        assertEquals(employeeResponse, response);
        Mockito.verify(restClient, times(1)).get();
        Mockito.verify(requestHeadersUriSpec, times(1)).uri(any(String.class), any(Object[].class));
        Mockito.verify(requestHeadersUriSpec.retrieve(), times(1)).body(EmployeeResponse.class);
    }

    @Test
    void updateEmployeeSuccessfully() {
        Long employeeId = 1L;
        EmployeeRequest employeeRequest = new EmployeeRequest();
        EmployeeResponse employeeResponse = new EmployeeResponse();
        Mockito.when(restClient.put()).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.uri(any(String.class), any(Object[].class))).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.body(any(EmployeeRequest.class))).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.body(EmployeeResponse.class)).thenReturn(employeeResponse);

        EmployeeResponse response = employeeDatabaseService.updateEmployee(employeeId, employeeRequest);

        assertEquals(employeeResponse, response);
        Mockito.verify(restClient, times(1)).put();
        Mockito.verify(requestBodyUriSpec, times(1)).uri(any(String.class), any(Object[].class));
        Mockito.verify(requestBodyUriSpec, times(1)).body(any(EmployeeRequest.class));
        Mockito.verify(requestBodyUriSpec.retrieve(), times(1)).body(EmployeeResponse.class);
    }

    @Test
    void deleteEmployeeSuccessfully() {
        Long employeeId = 1L;
        String responseMessage = "Employee deleted successfully";
        Mockito.when(restClient.delete()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.uri(any(String.class), any(Object[].class))).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.body(String.class)).thenReturn(responseMessage);

        String response = employeeDatabaseService.deleteEmployee(employeeId);

        assertEquals(responseMessage, response);
        Mockito.verify(restClient, times(1)).delete();
        Mockito.verify(requestHeadersUriSpec, times(1)).uri(any(String.class), any(Object[].class));
        Mockito.verify(requestHeadersUriSpec.retrieve(), times(1)).body(String.class);
    }


    @Test
    void getAllEmployeesSuccessfully() {
        List<EmployeeResponse> employeeResponses = List.of(new EmployeeResponse());
        Mockito.when(restClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.uri(any(String.class), any(Object[].class))).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.body(new ParameterizedTypeReference<List<EmployeeResponse>>() {})).thenReturn(employeeResponses);

        List<EmployeeResponse> responses = employeeDatabaseService.getAllEmployees();

        assertEquals(employeeResponses, responses);
        Mockito.verify(restClient, times(1)).get();
        Mockito.verify(requestHeadersUriSpec, times(1)).uri(any(String.class), any(Object[].class));
        Mockito.verify(requestHeadersUriSpec.retrieve(), times(1)).body(new ParameterizedTypeReference<List<EmployeeResponse>>() {});
    }

    @Test
    void assignProjectToEmployeeSuccessfully() {
        Long employeeId = 1L;
        Long projectId = 1L;
        String responseMessage = "Project assigned successfully";
        Mockito.when(restClient.put()).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.uri(any(String.class), any(Object[].class))).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.body(String.class)).thenReturn(responseMessage);

        String response = employeeDatabaseService.assignProjectToEmployee(employeeId, projectId);

        assertEquals(responseMessage, response);
        Mockito.verify(restClient, times(1)).put();
        Mockito.verify(requestBodyUriSpec, times(1)).uri(any(String.class), any(Object[].class));
        Mockito.verify(requestBodyUriSpec.retrieve(), times(1)).body(String.class);
    }

    @Test
    void deleteRoleSuccessfully() {
        Long roleId = 1L;
        String responseMessage = "Role deleted successfully";
        Mockito.when(restClient.delete()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.uri(any(String.class), any(Object[].class))).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.body(String.class)).thenReturn(responseMessage);

        String response = employeeDatabaseService.deleteRole(roleId);

        assertEquals(responseMessage, response);
        Mockito.verify(restClient, times(1)).delete();
        Mockito.verify(requestHeadersUriSpec, times(1)).uri(any(String.class), any(Object[].class));
        Mockito.verify(requestHeadersUriSpec.retrieve(), times(1)).body(String.class);
    }

    @Test
    void createEmployeeThrowsException() {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        when(restClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(any(String.class), any(Object[].class))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.body(any(EmployeeRequest.class))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(EmployeeResponse.class)).thenThrow(HttpServerErrorException.create(HttpStatus.INTERNAL_SERVER_ERROR, "Backend Error", null, null, null));

        assertThrows(TechnicalException.class, () -> employeeDatabaseService.createEmployee(employeeRequest));
    }

    @Test
    void getEmployeeByIdThrowsException() {
        Long employeeId = 1L;
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(String.class), any(Object[].class))).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(EmployeeResponse.class)).thenThrow(HttpServerErrorException.create(HttpStatus.INTERNAL_SERVER_ERROR, "Backend Error", null, null, null));

        assertThrows(TechnicalException.class, () -> employeeDatabaseService.getEmployeeById(employeeId));
    }

    @Test
    void updateEmployeeThrowsException() {
        Long employeeId = 1L;
        EmployeeRequest employeeRequest = new EmployeeRequest();
        when(restClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(any(String.class), any(Object[].class))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.body(any(EmployeeRequest.class))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(EmployeeResponse.class)).thenThrow(HttpServerErrorException.create(HttpStatus.INTERNAL_SERVER_ERROR, "Backend Error", null, null, null));

        assertThrows(TechnicalException.class, () -> employeeDatabaseService.updateEmployee(employeeId, employeeRequest));
    }

    @Test
    void deleteEmployeeThrowsException() {
        Long employeeId = 1L;
        when(restClient.delete()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(String.class), any(Object[].class))).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(String.class)).thenThrow(HttpServerErrorException.create(HttpStatus.INTERNAL_SERVER_ERROR, "Backend Error", null, null, null));

        assertThrows(TechnicalException.class, () -> employeeDatabaseService.deleteEmployee(employeeId));
    }

    @Test
    void getAllEmployeesThrowsException() {
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(String.class), any(Object[].class))).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(new ParameterizedTypeReference<List<EmployeeResponse>>() {})).thenThrow(HttpServerErrorException.create(HttpStatus.INTERNAL_SERVER_ERROR, "Backend Error", null, null, null));

        assertThrows(TechnicalException.class, () -> employeeDatabaseService.getAllEmployees());
    }

    @Test
    void assignProjectToEmployeeThrowsException() {
        Long employeeId = 1L;
        Long projectId = 1L;
        when(restClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(any(String.class), any(Object[].class))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(String.class)).thenThrow(HttpServerErrorException.create(HttpStatus.INTERNAL_SERVER_ERROR, "Backend Error", null, null, null));

        assertThrows(TechnicalException.class, () -> employeeDatabaseService.assignProjectToEmployee(employeeId, projectId));
    }

    @Test
    void deleteRoleThrowsException() {
        Long roleId = 1L;
        when(restClient.delete()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(String.class), any(Object[].class))).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(String.class)).thenThrow(HttpServerErrorException.create(HttpStatus.INTERNAL_SERVER_ERROR, "Backend Error", null, null, null));

        assertThrows(TechnicalException.class, () -> employeeDatabaseService.deleteRole(roleId));
    }

}