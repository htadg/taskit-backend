package com.htadg.taskit.service;

import java.util.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.htadg.taskit.constant.TaskitConstants.ROLE;
import com.htadg.taskit.entity.Role;
import com.htadg.taskit.entity.User;
import com.htadg.taskit.repository.RoleRepository;
import com.htadg.taskit.repository.UserRepository;

@Setter
@Getter
@Service("userDetailsService")
@Transactional
public class TaskitUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public TaskitUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return User.getGuestUser();
        }
        return user;
    }

}
