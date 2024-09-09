package com.htadg.taskit.repository;

import com.htadg.taskit.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    public Board findByName(String name);
    public boolean existsByName(String name);
}
