package com.htadg.taskit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.htadg.taskit.constant.TaskItConstants;
import com.htadg.taskit.dto.UserDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "USER")
public class User extends AuditableEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @EqualsAndHashCode.Include
    @Column(unique = true)
    private String username;

    private String email;

    @ToString.Exclude
    @JsonIgnore
    private String password;

    @Column(name = "is_super_admin")
    private boolean superAdmin = false;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserBoardRoleLink> userBoardRoleLinks = new HashSet<>();

    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        for (UserBoardRoleLink userBoardRoleLink : userBoardRoleLinks) {
            authorities.add(new SimpleGrantedAuthority(userBoardRoleLink.getBoard().getName() + "_" + userBoardRoleLink.getRole().getName()));
        }
        if (authorities.isEmpty()) {
            if (this.isSuperAdmin())
                authorities.add(new SimpleGrantedAuthority(TaskItConstants.ROLE.SUPER_ADMIN.getValue()));
            else
                authorities.add(new SimpleGrantedAuthority(TaskItConstants.ROLE.GUEST.getValue()));
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

    public UserDto getDto() {
        UserDto userDto = new UserDto();
        userDto.setUsername(this.getUsername());
        userDto.setFirstName(this.getFirstName());
        userDto.setLastName(this.getLastName());
        userDto.setEmail(this.getEmail());
        return userDto;
    }

}
