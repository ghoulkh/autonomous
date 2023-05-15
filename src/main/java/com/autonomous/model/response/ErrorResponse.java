package com.autonomous.model.response;

import com.autonomous.exception.AutonomousException;
import com.autonomous.model.error.ErrorCode;
import lombok.Data;

@Data
public class ErrorResponse {
    private String code;
    private String description;

    public ErrorResponse(AutonomousException e) {
        this.code = e.getCode();
        this.description = e.getDescription();
    }

    public ErrorResponse() {
    }

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.code();
        this.description = errorCode.description();
    }
}
