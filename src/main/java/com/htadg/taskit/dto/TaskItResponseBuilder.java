package com.htadg.taskit.dto;

import com.htadg.taskit.constant.TaskItConstants;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;


public class TaskItResponseBuilder {

    private final HttpStatusCode statusCode;
    private final String status;
    private String message;
    private Object data;

    public TaskItResponseBuilder(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
        this.status = statusCode.is2xxSuccessful() ? TaskItConstants.SUCCESS : TaskItConstants.FAILURE;
    }

    public TaskItResponseBuilder message(String message) {
        this.message = message;
        return this;
    }

    public TaskItResponseBuilder data(Object data) {
        this.data = data;
        return this;
    }

    public ResponseEntity<Object> build() {
        Map<String, Object> body = new HashMap<>();
        body.put("status", this.status);
        body.put("data", this.data);
        if (this.message != null) body.put("message", this.message);

        return new ResponseEntity<>(body, this.statusCode);
    }

    public static TaskItResponseBuilder status(HttpStatusCode statusCode) {
        return new TaskItResponseBuilder(statusCode);
    }

}
