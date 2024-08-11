package com.htadg.taskit.constant;

import lombok.Getter;

public class TaskitConstants {
    
    @Getter
    public static enum ROLE {
        SUPER_USER("ROLE_SUPER_USER"),
        ADMIN("ROLE_ADMIN"),
        USER("ROLE_USER"),
        GUEST("ROLE_GUEST");

        private final String value;

        private ROLE(String value) {
            this.value = value;
        }
    }

    @Getter
    public static enum PRIVILEGE {
        READ_TASK("READ_TASK"),
        UPDATE_TASK("UPDATE_TASK"),
        CREATE_TASK("CREATE_TASK"),
        DELETE_TASK("DELETE_TASK"),
        ONBOARD_USER("ONBOARD_USER"),
        ONBOARD_ADMIN("ONBOARD_ADMIN");

        private final String value;

        private PRIVILEGE(String value) {
            this.value = value;
        }
    }
}
