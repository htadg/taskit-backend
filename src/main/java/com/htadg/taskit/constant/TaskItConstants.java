package com.htadg.taskit.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class TaskItConstants {

    public static final String SUCCESS = "Success";
    public static final String FAILURE = "Failure";
    public static final String BAD_CREDENTIALS = "Invalid Credentials";
    public static final String LOCKED_ACCOUNT = "User Account is Locked";
    public static final String DISABLED_ACCOUNT = "User Account is Disabled";
    public static final String NOT_FOUND = "%s: [%s] not found.";

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

        @JsonValue
        public static TASK_STATUS fromValue(String value) {
            return Arrays.stream(TASK_STATUS.values())
                    .filter(status -> status.getValue().equals(value))
                    .findFirst()
                    .orElseThrow();
        }
        
        public static List<String> getAllStatusValues() {
            return Arrays.stream(TASK_STATUS.values())
                    .map(TASK_STATUS::getValue)
                    .toList();
        }
    }

}
