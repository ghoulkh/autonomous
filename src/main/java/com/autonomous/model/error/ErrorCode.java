package com.autonomous.model.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    ACCESS_DENIED("CD-010", "Xin lỗi, bạn không có quyền truy cập!", HttpStatus.FORBIDDEN),
    BAD_REQUEST("CD-010", "Yêu cầu của bạn chưa đúng!!", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("CD-11", "User not found!", HttpStatus.NOT_FOUND),
    BANNED_PERFORM_ACTION("CD-CLIENT-403", "Banded perform action!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("UNAUTHORIZED", "Login session expired!", HttpStatus.UNAUTHORIZED),
    INTERNAL_SERVER("INTERNAL-SERVER", "internal server!", HttpStatus.INTERNAL_SERVER_ERROR),
    CONFLICT_USERNAME("APP-20", "Conflict username!", HttpStatus.CONFLICT),
    BANNED_USER("APP-21", "User is banned", HttpStatus.FORBIDDEN),
    INVALID_USERNAME_OR_PASSWORD("APP-22", "Invalid username or password!", HttpStatus.FORBIDDEN);


    private final String code;
    private final String description;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }

    public String code() {
        return this.code;
    }

    public String description() {
        return this.description;
    }

    public HttpStatus httpStatus() {
        return this.httpStatus;
    }
}

