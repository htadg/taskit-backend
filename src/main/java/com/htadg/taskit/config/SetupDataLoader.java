package com.htadg.taskit.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Component
@ConfigurationProperties(prefix="com.htadg.taskit.config.initial")
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

    private String adminUserName;
    private String adminFirstName;
    private String adminLastName;
    private String adminEmail;
    private String adminPassword;
 
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;

        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        Privilege deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE");
 
        createRoleIfNotFound("ROLE_ADMIN", Arrays.asList(readPrivilege, writePrivilege, deletePrivilege));
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege, writePrivilege));
        createRoleIfNotFound("ROLE_GUEST", Arrays.asList(readPrivilege));

        if (adminExists()) {
            log.info("Admin user already exists! Skipping initial data setup...");
            alreadySetup = true;
            return;
        }

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
