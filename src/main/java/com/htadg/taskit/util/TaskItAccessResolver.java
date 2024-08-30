package com.htadg.taskit.util;

import com.htadg.taskit.constant.TaskItConstants;
import com.htadg.taskit.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

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
        final Set<String> accessNames = getAccessNamesWithHierarchy(boardName, role);
        boolean hasAccess = user.isSuperAdmin() || user.getAuthorities().stream().anyMatch(authority -> accessNames.contains(authority.getAuthority()));
        log.debug("User {} Has Access {}: {}", user.getUsername(), accessName, hasAccess);
        return hasAccess;
    }


    /**
     * Returns a set of access names with hierarchy based on the provided board name and role.
     * Super Admin is not included in the hierarchy here since it has access to all boards.
     *
     * @param  boardName  the name of the board
     * @param  role       the role to determine the access names for
     * @return            a set of access names with hierarchy
     */
    private Set<String> getAccessNamesWithHierarchy(String boardName, String role) {
        Set<String> accessNames = new HashSet<>();
        switch (TaskItConstants.ROLE.valueOf(role)) {
            case BOARD_ADMIN -> accessNames.add(boardName + "_" + TaskItConstants.ROLE.BOARD_ADMIN.getValue());
            case USER -> {
                accessNames.add(boardName + "_" + TaskItConstants.ROLE.BOARD_ADMIN.getValue());
                accessNames.add(boardName + "_" + TaskItConstants.ROLE.USER.getValue());
            }
            default -> log.error("Invalid Access Specifiers: {}, {}", boardName, role);
        }
        return accessNames;
    }

}
