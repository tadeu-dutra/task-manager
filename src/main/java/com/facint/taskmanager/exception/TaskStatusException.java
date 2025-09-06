package com.facint.taskmanager.exception;

public class TaskStatusException extends RuntimeException {

    public static final long serialVersionUID = 1L;

    public TaskStatusException() {
        super();
    }
    
    public TaskStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskStatusException(String message) {
        super(message);
    }

    public TaskStatusException(Throwable cause) {
        super(cause);
    }
}
