package com.htadg.taskit.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.htadg.taskit.entity.Privilege;
import com.htadg.taskit.entity.Role;
import com.htadg.taskit.entity.User;
import com.htadg.taskit.repository.PrivilegeRepository;
import com.htadg.taskit.repository.RoleRepository;
import com.htadg.taskit.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SetupDataLoader implements
  ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    private RoleRepository roleRepository;
 
    @Autowired
    private PrivilegeRepository privilegeRepository;
 
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${taskit.admin.username}")
    private String adminUserName;

    @Value("${taskit.admin.first-name}")
    private String adminFirstName;

    @Value("${taskit.admin.last-name}")
    private String adminLastName;

    @Value("${taskit.admin.email}")
    private String adminEmail;

    @Value("${taskit.admin.password}")
    private String adminPassword;
 
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
 
        if (alreadySetup || adminExists()) {
            log.info("Admin user already exists! Skipping initial data setup...");
            return;
        }
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
 
        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User();
        user.setFirstName(adminFirstName);
        user.setLastName(adminLastName);
        user.setEmail(adminEmail);
        user.setUserName(adminUserName);
        user.setPassword(passwordEncoder.encode(adminPassword));
        user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        userRepository.save(user);

        alreadySetup = true;
    }

    private boolean adminExists() {
        User adminUser = null;
        try {
            adminUser = userRepository.findByUserName(adminUserName);
        } catch (Exception e) {
            log.info("Default admin user does not exist.");
        }
        return adminUser != null;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
 
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(
      String name, Collection<Privilege> privileges) {
 
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
