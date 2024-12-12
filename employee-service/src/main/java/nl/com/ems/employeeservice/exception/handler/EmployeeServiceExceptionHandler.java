package nl.com.ems.employeeservice.exception.handler;

import lombok.extern.slf4j.Slf4j;
import nl.com.ems.employeeservice.exception.ForbiddenException;
import nl.com.ems.employeeservice.exception.TechnicalException;
import nl.com.ems.employeeservice.model.ErrorResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

@RestControllerAdvice
@Slf4j
public class EmployeeServiceExceptionHandler {

    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "An unexpected error occurred on the server.";
    private static final String INVALID_INPUT_MESSAGE = "Invalid input";
    private static final String FORBIDDEN_MESSAGE = "Forbidden";


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Method argument not valid: {}", ex.getMessage());
        return buildErrorResponse(BAD_REQUEST.value(), INVALID_INPUT_MESSAGE, ex.getDetailMessageCode());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("Method argument type mismatch: {}", ex.getMessage());
        return buildErrorResponse(BAD_REQUEST.value(), INVALID_INPUT_MESSAGE, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("HTTP message not readable: {}", ex.getMessage());
        return buildErrorResponse(BAD_REQUEST.value(), INVALID_INPUT_MESSAGE, ex.getLocalizedMessage());
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error("HTTP Method Not Supported: {}", ex.getMessage());
        return buildErrorResponse(METHOD_NOT_ALLOWED.value(), INVALID_INPUT_MESSAGE, ex.getLocalizedMessage());
    }

    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<ErrorResponse> handleTechnicalException(TechnicalException ex) {
        log.error("Technical exception: {}", ex.getMessage());
        return buildErrorResponse(ex.getStatus(), ex.getMessage(), ex.getDetailedMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        log.error("Bad request: {}", ex.getMessage());
        return buildErrorResponse(BAD_REQUEST.value(), INVALID_INPUT_MESSAGE, ex.getLocalizedMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException ex) {
        log.error("Forbidden: {}", ex.getMessage());
        return buildErrorResponse(FORBIDDEN.value(), FORBIDDEN_MESSAGE, ex.getLocalizedMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Internal server error: {}", ex.getMessage());
        return buildErrorResponse(500, INTERNAL_SERVER_ERROR_MESSAGE, ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(int status, String message, String detailedMessage) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(status);
        errorResponse.setMessage(message);
        errorResponse.setDetailedMessage(detailedMessage);
        return ResponseEntity.status(status).body(errorResponse);
    }
}