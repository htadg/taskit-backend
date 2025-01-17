package com.htadg.taskit.repository;

import com.htadg.taskit.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName(String name);
    public Set<Role> findByIdIn(Set<String> name);
}
