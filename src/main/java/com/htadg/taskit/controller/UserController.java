package com.htadg.taskit.controller;

import com.htadg.taskit.dto.TaskItResponseBuilder;
import com.htadg.taskit.dto.UserDto;
import com.htadg.taskit.entity.Board;
import com.htadg.taskit.entity.User;
import com.htadg.taskit.entity.UserBoardRoleLink;
import com.htadg.taskit.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private final BoardRepository boardRepository;

    @Autowired
    public UserController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @PreAuthorize("@taskItAccessResolver.hasTaskItAccess(#boardName, 'USER')")
    @RequestMapping("/authenticated/getUsersForBoard/{boardName}")
    public ResponseEntity<Object> getUsersForBoard(@PathVariable String boardName) {
        TaskItResponseBuilder response;
        try {
            Board board = boardRepository.findByName(boardName);
            if (board != null) {
                List<UserDto> usersForBoard = board.getUserBoardRoleLinks().stream().map(UserBoardRoleLink::getUser).map(User::getDto).toList();
                response = TaskItResponseBuilder.status(HttpStatus.OK).message("Users successfully retrieved.").data(usersForBoard);
            } else {
                response = TaskItResponseBuilder.status(HttpStatus.NOT_FOUND).message("Board not found.");
            }
        } catch (Exception e) {
            response = TaskItResponseBuilder.status(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage());
        }
        return response.build();
    }

}
