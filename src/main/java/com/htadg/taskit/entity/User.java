package com.htadg.taskit.entity;

import com.htadg.taskit.constant.TaskitConstants;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "USER")
public class User extends AuditableEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String firstName;
    private String lastName;

    @EqualsAndHashCode.Include
    @Column(unique = true)
    private String username;

    private String email;
    private String password;
    private boolean superAdmin = false;
    private boolean active = true;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Collection<UserBoardRoleLink> userBoardRoleLinks;

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        for (UserBoardRoleLink userBoardRoleLink : userBoardRoleLinks) {
            authorities.add(new SimpleGrantedAuthority(userBoardRoleLink.getBoard().getName() + "_" + userBoardRoleLink.getRole().getName()));
        }
        if (authorities.isEmpty()) {
            if (this.isSuperAdmin())
                authorities.add(new SimpleGrantedAuthority(TaskitConstants.ROLE.SUPER_ADMIN.getValue()));
            else
                authorities.add(new SimpleGrantedAuthority(TaskitConstants.ROLE.GUEST.getValue()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive();
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public static User getGuestUser() {
        return new User();
    }

}
