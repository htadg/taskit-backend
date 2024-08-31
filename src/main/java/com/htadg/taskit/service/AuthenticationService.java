package com.htadg.taskit.service;

import com.htadg.taskit.dto.request.LoginUserDto;
import com.htadg.taskit.dto.request.RegisterUserDto;
import com.htadg.taskit.entity.User;
import com.htadg.taskit.exception.TaskItServiceException;

public interface AuthenticationService {
    User signup(RegisterUserDto input);
    User authenticate(LoginUserDto input) throws TaskItServiceException;
}
