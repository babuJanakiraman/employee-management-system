package nl.com.ems.employeeservice.exception.handler;

import nl.com.ems.employeeservice.exception.ForbiddenException;
import nl.com.ems.employeeservice.exception.TechnicalException;
import nl.com.ems.employeeservice.model.ErrorResponse;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeServiceExceptionHandlerTest {

    private EmployeeServiceExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new EmployeeServiceExceptionHandler();
    }

    @Test
    void handleMethodArgumentTypeMismatchException_ReturnsBadRequestResponse() {
        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(null, null, null, null, null);

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleMethodArgumentTypeMismatchException(ex);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals("Invalid input", response.getBody().getMessage());
    }

    @Test
    void handleHttpMessageNotReadableException_ReturnsBadRequestResponse() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Message not readable");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleHttpMessageNotReadableException(ex);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals("Invalid input", response.getBody().getMessage());
        assertEquals("Message not readable", response.getBody().getDetailedMessage());
    }

    @Test
    void handleHttpRequestMethodNotSupportedException_ReturnsMethodNotAllowedResponse() {
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("Method not supported");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleHttpRequestMethodNotSupportedException(ex);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), response.getStatusCodeValue());
        assertEquals("Invalid input", response.getBody().getMessage());
    }

    @Test
    void handleTechnicalException_ReturnsErrorResponse() {
        TechnicalException ex = new TechnicalException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Technical error", "Detailed technical error");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleTechnicalException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("Technical error", response.getBody().getMessage());
        assertEquals("Detailed technical error", response.getBody().getDetailedMessage());
    }

    @Test
    void handleBadRequestException_ReturnsBadRequestResponse() {
        BadRequestException ex = new BadRequestException("Bad request");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleBadRequestException(ex);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals("Invalid input", response.getBody().getMessage());
        assertEquals("Bad request", response.getBody().getDetailedMessage());
    }

    @Test
    void handleForbiddenException_ReturnsForbiddenResponse() {
        ForbiddenException ex = new ForbiddenException("Forbidden");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleForbiddenException(ex);

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCodeValue());
        assertEquals("Forbidden", response.getBody().getMessage());
        assertEquals("Forbidden", response.getBody().getDetailedMessage());
    }

    @Test
    void handleException_ReturnsInternalServerErrorResponse() {
        Exception ex = new Exception("Unexpected error");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("An unexpected error occurred on the server.", response.getBody().getMessage());
        assertEquals("Unexpected error", response.getBody().getDetailedMessage());
    }
}