package com.htadg.taskit.controller;

import com.htadg.taskit.constant.TaskItConstants;
import com.htadg.taskit.dto.TaskItResponseBuilder;
import com.htadg.taskit.entity.Board;
import com.htadg.taskit.entity.Task;
import com.htadg.taskit.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


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
    public ResponseEntity<Object>  getTasks(@PathVariable String boardName) {
        TaskItResponseBuilder response;
        try {
            Board board = boardRepository.findByName(boardName);
            if (board != null) {
                Collection<Task> tasks = board.getTaskList();
                response = TaskItResponseBuilder.status(HttpStatus.OK).message("Tasks successfully retrieved.").data(tasks);
            } else {
                response = TaskItResponseBuilder.status(HttpStatus.NOT_FOUND).message("Board not found.");
            }
        } catch (Exception e) {
            response = TaskItResponseBuilder.status(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage());
        }
        return response.build();
    }

    @GetMapping(path="/getAvailableStatus", produces = "application/json")
    public ResponseEntity<Object> getAvailableStatus() {
        TaskItResponseBuilder response;
        try  {
            List<String> statusList = Arrays.stream(TaskItConstants.TASK_STATUS.values())
                    .map(TaskItConstants.TASK_STATUS::getValue)
                    .toList();
            response = TaskItResponseBuilder.status(HttpStatus.OK).data(statusList);
        } catch (Exception e) {
            response = TaskItResponseBuilder.status(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage());
        }
        return response.build();
    }

}