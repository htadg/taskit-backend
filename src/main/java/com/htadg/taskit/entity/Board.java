package com.htadg.taskit.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;


@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "BOARD")
public class Board extends AuditableEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @EqualsAndHashCode.Include
    private String name;

    private String description;
    private boolean active = true;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    private Collection<UserBoardRoleLink> userBoardRoleLinks;

}
