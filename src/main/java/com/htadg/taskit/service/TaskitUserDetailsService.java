package com.htadg.taskit.service;

import com.htadg.taskit.entity.User;
import com.htadg.taskit.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
