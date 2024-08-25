package com.htadg.taskit.entity;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "USER_BOARD_ROLE")
public class UserBoardRoleLink extends AuditableEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
