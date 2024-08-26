package com.htadg.taskit.constant;

import lombok.Getter;

public class TaskitConstants {

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

}
