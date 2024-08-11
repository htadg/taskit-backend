package com.htadg.taskit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    public TaskController() {
    }

    /**
     * Retrieves a list of tasks in JSON format.
     *
     * @return a Response object containing the list of tasks in JSON format
     */
    @GetMapping(path="/getTasks", produces = "application/json")
    public ResponseEntity<String> getTasks() {
        String tasks = "{\"tasks\": [\"task1\", \"task2\", \"task3\"]}";
        return ResponseEntity.ok(tasks);
    }

}