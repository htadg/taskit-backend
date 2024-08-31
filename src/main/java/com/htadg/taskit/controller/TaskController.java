package com.htadg.taskit.controller;

import com.htadg.taskit.entity.Task;
import com.htadg.taskit.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final BoardRepository boardRepository;

    @Autowired
    public TaskController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /**
     * Retrieves a list of tasks in JSON format.
     *
     * @return a Response object containing the list of tasks in JSON format
     */
    @GetMapping(path="/getTasksForBoard/{boardName}", produces = "application/json")
    public ResponseEntity<Collection<Task>>  getTasks(@PathVariable String boardName) {
        return ResponseEntity.ok(boardRepository.findByName(boardName).getTaskList());
    }

}