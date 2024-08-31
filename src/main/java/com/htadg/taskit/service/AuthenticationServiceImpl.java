package com.htadg.taskit.service;

import com.htadg.taskit.constant.TaskItConstants;
import com.htadg.taskit.dto.request.LoginUserDto;
import com.htadg.taskit.dto.request.RegisterUserDto;
import com.htadg.taskit.entity.User;
import com.htadg.taskit.exception.TaskItServiceException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@Setter
@Getter
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User signup(RegisterUserDto input) {
        User user = new User();
        try {
            user.setUsername(input.getUsername());
            user.setPassword(input.getPassword());
            user.setFirstName(input.getFirstName());
            user.setLastName(input.getLastName());
            user.setEmail(input.getEmail());
            user.setActive(input.isActive());
            userService.save(user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        return user;
    }

    @Override
    public User authenticate(LoginUserDto input) throws TaskItServiceException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword())
            );
        } catch (DisabledException e) {
            throw new TaskItServiceException(TaskItConstants.DISABLED_ACCOUNT, e);
        } catch (LockedException e) {
            throw new TaskItServiceException(TaskItConstants.LOCKED_ACCOUNT, e);
        } catch (BadCredentialsException e) {
            throw new TaskItServiceException(TaskItConstants.BAD_CREDENTIALS, e);
        }

        User authenticatedUser = null;
        try {
            authenticatedUser = userService.findByUsername(input.getUsername());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return authenticatedUser;
    }
}
