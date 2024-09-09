package com.htadg.taskit.service;

import com.htadg.taskit.entity.Board;
import com.htadg.taskit.entity.User;

import java.util.List;

public interface BoardService {
    Board getBoardByName(String boardName);
    List<User> getUsersForBoard(Board board);
    boolean existsByName(String name);
}
