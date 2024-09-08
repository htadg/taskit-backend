package com.htadg.taskit.controller;

import com.htadg.taskit.dto.TaskItResponseBuilder;
import com.htadg.taskit.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    @GetMapping(path="/getAvailableStatus", produces = "application/json")
    public ResponseEntity<Object> getAvailableStatus() {
        TaskItResponseBuilder response;
        log.info("Fetching list of available task status");
        try  {
            List<String> statusList = taskService.getAllAvailableStatus();
            response = TaskItResponseBuilder.status(HttpStatus.OK).data(statusList);
        } catch (Exception e) {
            response = TaskItResponseBuilder.status(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage());
        }
        return response.build();
    }

}