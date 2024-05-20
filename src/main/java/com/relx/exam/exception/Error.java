package com.relx.exam.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {
    private String id;
    private String code;
    private String message;
    private String errorCode;
    private String reasonCode;
    private String errorMessage;

    public Error(String id, String message){
        this.id = id;
        this.message = message;
    }
}
