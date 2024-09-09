package com.htadg.taskit.service;

import com.htadg.taskit.entity.Board;
import com.htadg.taskit.entity.User;
import com.htadg.taskit.exception.TaskItServiceException;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    void save(User user) throws TaskItServiceException;
    List<Board> getBoardsForUser(User user);
}
