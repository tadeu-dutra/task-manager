package com.facint.taskmanager.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    
    private String field;
    private String message;
}
