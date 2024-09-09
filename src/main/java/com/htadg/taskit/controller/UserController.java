package com.htadg.taskit.controller;

import com.htadg.taskit.constant.TaskItConstants;
import com.htadg.taskit.dto.BoardDto;
import com.htadg.taskit.dto.TaskItResponseBuilder;
import com.htadg.taskit.entity.Board;
import com.htadg.taskit.entity.User;
import com.htadg.taskit.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;
import java.util.List;


@Slf4j
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @RequestMapping("/authenticated/getAllBoardsForUser")
    public ResponseEntity<Object> getAllBoardsForUser(Principal principal) {
        TaskItResponseBuilder response;
        String username = principal.getName();
        log.info("Fetching all boards for User [{}]", username);
        try {
            User user = userService.findByUsername(username);
            if (user != null) {
                Collection<Board> boardList = userService.getBoardsForUser(user);
                List<BoardDto> boardDtoList = boardList.stream().map(Board::getDto).toList();
                response = TaskItResponseBuilder.status(HttpStatus.OK).message("Boards successfully retrieved.").data(boardDtoList);
            } else {
                String errorMessage = TaskItConstants.NOT_FOUND.formatted("User", username);
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
