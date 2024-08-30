package com.htadg.taskit.constant;

import lombok.Getter;

public class TaskItConstants {

    public static final String BAD_CREDENTIALS = "Invalid Credentials";
    public static final String LOCKED_ACCOUNT = "User Account is Locked";
    public static final String DISABLED_ACCOUNT = "User Account is Disabled";

    @Getter
    public static enum ROLE {
        SUPER_ADMIN("SUPER_ADMIN"),
        BOARD_ADMIN("BOARD_ADMIN"),
        USER("USER"),
        GUEST("GUEST");

        private final String value;

        private ROLE(String value) {
            this.value = value;
        }
    }

    @Getter
    public static enum TASK_STATUS {
        NOT_STARTED("Not Started"),
        IN_PROGRESS("In Progress"),
        COMPLETED("Completed"),
        CANCELLED("Cancelled");

        private final String value;

        private TASK_STATUS(String value) {
            this.value = value;
        }
    }

}