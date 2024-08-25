package com.htadg.taskit.util;

import com.htadg.taskit.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Slf4j
@Component
public class TaskItAccessResolver {

    /**
     * Checks if the user has access to the specified board and role.
     *
     * @param  boardName  the name of the board
     * @param  role       the role to check access for
     * @return            true if the user has access or is super admin, false otherwise
     */
    public boolean hasTaskItAccess(String boardName, String role) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            return false;
        }
        final String accessName = boardName + "_" + role;
        boolean hasAccess = user.isSuperAdmin() || user.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(accessName));
        log.debug("User {} Has Access {}: {}", user.getUsername(), accessName, hasAccess);
        return hasAccess;
    }

}
