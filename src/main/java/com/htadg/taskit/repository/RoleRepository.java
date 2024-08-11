package com.htadg.taskit.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.htadg.taskit.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role findByName(String name);
    public Collection<Role> findByIdIn(Collection<String> name);
}
