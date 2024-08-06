package com.htadg.taskit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.htadg.taskit.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUserName(String userName);
}
