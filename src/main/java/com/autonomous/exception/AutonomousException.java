package com.autonomous.exception;

import com.autonomous.model.error.ErrorCode;
import org.springframework.http.HttpStatus;

public class AutonomousException extends RuntimeException {
    private final String code;
    private final String description;
    private final HttpStatus httpStatus;

    public AutonomousException(ErrorCode errorCode) {
        super(errorCode.description());
        this.code = errorCode.code();
        this.description = errorCode.description();
        this.httpStatus = errorCode.httpStatus();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
