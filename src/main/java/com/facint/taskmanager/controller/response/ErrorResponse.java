package com.facint.taskmanager.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    
    public ErrorResponse(String message) {
        this.message = message;
    }

    private String field;
    private String message;
}
