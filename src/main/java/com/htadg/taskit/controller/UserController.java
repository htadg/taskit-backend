package com.htadg.taskit.controller;

import com.htadg.taskit.dto.UserDto;
import com.htadg.taskit.entity.User;
import com.htadg.taskit.entity.UserBoardRoleLink;
import com.htadg.taskit.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<UserDto>> getUsersForBoard(@PathVariable String boardName) {
        return ResponseEntity.ok(boardRepository.findByName(boardName).getUserBoardRoleLinks().stream().map(UserBoardRoleLink::getUser).map(User::getDto).toList());
    }

}
