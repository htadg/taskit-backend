package com.htadg.taskit.controller;

import com.htadg.taskit.constant.TaskItConstants;
import com.htadg.taskit.dto.TaskItResponseBuilder;
import com.htadg.taskit.dto.UserDto;
import com.htadg.taskit.entity.Board;
import com.htadg.taskit.entity.Task;
import com.htadg.taskit.entity.User;
import com.htadg.taskit.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;


@Slf4j
@Setter
@Getter
@AllArgsConstructor(onConstructor_ = @__({ @Autowired}))
@NoArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {

    private BoardService boardService;

    @PreAuthorize("@taskItAccessResolver.hasTaskItAccess('ALL', 'SUPER_ADMIN')")
    @GetMapping(value = "/authenticated/checkIfBoardExists", produces = "application/json")
    public ResponseEntity<Object> checkIfBoardExists(@RequestParam(name = "boardName") String boardName) {
        TaskItResponseBuilder response;
        log.info("Checking if Board [{}] exists", boardName);
        try {
            if (!boardService.existsByName(boardName)) {
                response = TaskItResponseBuilder.status(HttpStatus.OK).message("Board successfully retrieved.");
            } else {
                String message = "Board [%s] already exists.".formatted(boardName);
                log.info(message);
                response = TaskItResponseBuilder.status(HttpStatus.OK).message(message);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response = TaskItResponseBuilder.status(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage());
        }
        return response.build();
    }

    @PreAuthorize("@taskItAccessResolver.hasTaskItAccess(#boardName, 'USER')")
    @GetMapping(value = "/authenticated/getUsersForBoard", produces = "application/json")
    public ResponseEntity<Object> getUsersForBoard(@RequestParam(name = "boardName") String boardName) {
        TaskItResponseBuilder response;
        log.info("Fetching users for board [{}]", boardName);
        try {
            Board board = boardService.getBoardByName(boardName);
            if (board != null) {
                List<UserDto> userList = boardService.getUsersForBoard(board).stream().map(User::getDto).toList();
                response = TaskItResponseBuilder.status(HttpStatus.OK).message("Board Users successfully retrieved.").data(userList);
            } else {
                String errorMessage = TaskItConstants.NOT_FOUND.formatted("Board", boardName);
                log.error(errorMessage);
                response = TaskItResponseBuilder.status(HttpStatus.NOT_FOUND).message(errorMessage);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response = TaskItResponseBuilder.status(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage());
        }
        return response.build();
    }

    /**
     * Retrieves a list of tasks in JSON format.
     *
     * @return a Response object containing the list of tasks in JSON format
     */
    @GetMapping(path="/getTasksForBoard", produces = "application/json")
    public ResponseEntity<Object>  getTasks(@RequestParam(name = "boardName") String boardName) {
        TaskItResponseBuilder response;
        log.info("Fetching tasks for board [{}]", boardName);
        try {
            Board board = boardService.getBoardByName(boardName);
            if (board != null) {
                Collection<Task> tasks = board.getTaskList();
                response = TaskItResponseBuilder.status(HttpStatus.OK).message("Tasks successfully retrieved.").data(tasks);
            } else {
                String errorMessage = TaskItConstants.NOT_FOUND.formatted("Board", boardName);
                log.error(errorMessage);
                response = TaskItResponseBuilder.status(HttpStatus.NOT_FOUND).message(errorMessage);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response = TaskItResponseBuilder.status(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage());
        }
        return response.build();
    }

}
