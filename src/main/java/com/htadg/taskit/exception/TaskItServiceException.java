package com.htadg.taskit.exception;


import java.io.Serial;

public class TaskItServiceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public TaskItServiceException(String message) {
        super(message);
    }

    public TaskItServiceException(Throwable cause) {
        super(cause);
    }

    public TaskItServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
