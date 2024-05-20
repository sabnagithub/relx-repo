package com.relx.exam.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomException extends RuntimeException {

    private HttpStatus httpStatus;
    private String messages = "";

    public CustomException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.messages = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
