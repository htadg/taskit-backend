package com.htadg.taskit.constant;

import lombok.Getter;

public class TaskitConstants {
    
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
