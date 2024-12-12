package nl.com.ems.employeedatabase.exception.handler;

import nl.com.ems.employeedatabase.exception.BadRequestException;
import nl.com.ems.employeedatabase.exception.InternalServerException;
import nl.com.ems.employeedatabase.exception.ResourceNotFoundException;
import nl.com.ems.employeedatabase.model.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeDataExceptionHandlerTest {

    private EmployeeDataExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new EmployeeDataExceptionHandler();
    }

    @Test
    void handleResourceNotFoundException_ReturnsNotFoundResponse() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleResourceNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        assertEquals("Resource not found", response.getBody().getMessage());
        assertEquals("Resource not found", response.getBody().getDetailedMessage());
    }

    @Test
    void handleBadRequestException_ReturnsBadRequestResponse() {
        BadRequestException ex = new BadRequestException("Bad request");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleBadRequestException(ex);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals("Bad request", response.getBody().getMessage());
        assertEquals("Bad request", response.getBody().getDetailedMessage());
    }

    @Test
    void handleInternalServerException_ReturnsInternalServerErrorResponse() {
        InternalServerException ex = new InternalServerException("Internal server error", new Exception());

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleInternalServerException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("Internal server error", response.getBody().getMessage());
        assertEquals("Internal server error", response.getBody().getDetailedMessage());
    }

    @Test
    void handleException_ReturnsInternalServerErrorResponse() {
        Exception ex = new Exception("Unexpected error");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("Internal server error", response.getBody().getMessage());
        assertEquals("Unexpected error", response.getBody().getDetailedMessage());
    }
}