package com.htadg.taskit.controller;

import com.htadg.taskit.service.BoardService;
import com.htadg.taskit.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private BoardService boardService;


}
