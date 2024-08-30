package com.htadg.taskit.controller;

import com.htadg.taskit.entity.Task;
import com.htadg.taskit.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public @ResponseBody Collection<Task> getTasks(@PathVariable String boardName) {
        return boardRepository.findByName(boardName).getTaskList();
    }

}