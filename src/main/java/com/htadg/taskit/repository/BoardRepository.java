package com.htadg.taskit.repository;

import com.htadg.taskit.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    public Board findByName(String name);
}
