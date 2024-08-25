package com.htadg.taskit.config;


import com.htadg.taskit.entity.User;
import com.htadg.taskit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Getter
@Setter
@Slf4j
@Component
public class SetupInitialData implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SetupInitialData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        User adminUser = userRepository.findByUsername("admin");
        if (adminUser != null) {
            log.info("Super Admin user already exists! {}", adminUser);
            return;
        }
        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setFirstName("Admin");
        user.setLastName("Super");
        user.setEmail("superadmin@taskit.com");
        user.setSuperAdmin(true);
        user.setActive(true);
        userRepository.save(user);

        log.info("New Super Admin user created : {}", user);
    }
}
