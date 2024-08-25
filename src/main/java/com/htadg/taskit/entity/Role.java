package com.htadg.taskit.entity;

import java.sql.Timestamp;
import java.util.Collection;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name="ROLE")
public class Role extends AuditableEntity {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @EqualsAndHashCode.Include
    private String name;

    private String description;
    private boolean active = true;

    @OneToMany(mappedBy = "role")
    private Collection<UserBoardRoleLink> userBoardRoleLinks;

}
