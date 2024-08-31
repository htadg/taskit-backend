package com.htadg.taskit.service;

import com.htadg.taskit.entity.User;
import com.htadg.taskit.exception.TaskItServiceException;

public interface UserService {
    User findByUsername(String username);

    void save(User user) throws TaskItServiceException;
}
