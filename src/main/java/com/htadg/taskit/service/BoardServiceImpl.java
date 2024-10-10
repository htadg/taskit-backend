package com.htadg.taskit.service;

import com.htadg.taskit.entity.Board;
import com.htadg.taskit.entity.User;
import com.htadg.taskit.entity.UserBoardRoleLink;
import com.htadg.taskit.repository.BoardRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Setter
@Getter
@Slf4j
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @__({ @Autowired }))
@Service
public class BoardServiceImpl implements BoardService {

    private BoardRepository boardRepository;

    @Override
    public Board getBoardByName(String boardName) {
        return boardRepository.findByName(boardName);
    }

    @Override
    public List<User> getUsersForBoard(Board board) {
        return board.getUserBoardRoleLinks().stream().map(UserBoardRoleLink::getUser).toList();
    }

    @Override
    public boolean existsByName(String name) {
        return boardRepository.existsByName(name);
    }
}
