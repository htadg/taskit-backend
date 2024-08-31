package com.htadg.taskit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "BOARD")
public class Board extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    private String name;

    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    private Set<UserBoardRoleLink> userBoardRoleLinks = new HashSet<>();

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    private Set<Task> taskList = new HashSet<>();

}
