package nl.com.ems.employeeservice.exception;

import lombok.Getter;

@Getter
public class TechnicalException extends RuntimeException {
    private final int status;
    private final String message;
    private final String detailedMessage;

    public TechnicalException(int status, String message, String detailedMessage) {
        super(message);
        this.status = status;
        this.message = message;
        this.detailedMessage = detailedMessage;
    }

}