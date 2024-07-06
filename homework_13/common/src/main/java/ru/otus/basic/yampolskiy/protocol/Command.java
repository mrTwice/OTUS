package ru.otus.basic.yampolskiy.protocol;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public enum Command {
    REGISTER("REGISTER"),
    REGISTER_SUCCESSFUL("REGISTER_SUCCESSFUL"),
    REGISTER_UNSUCCESSFUL("REGISTER_UNSUCCESSFUL"),
    LOGIN("LOGIN"),
    LOGIN_SUCCESSFUL("LOGIN_SUCCESSFUL"),
    LOGIN_UNSUCCESSFUL("LOGIN_UNSUCCESSFUL"),
    LOGOUT("LOGOUT"),
    LOGOUT_SUCCESSFUL("LOGOUT_SUCCESSFUL"),
    INFORMATION("INFORMATION"),
    MESSAGE("MESSAGE"),
    CREATE_CHAT("CREATE_CHAT"),
    CREATE_PRIVATE_CHAT("CREATE_PRIVATE_CHAT"),
    DELETE_CHAT("DELETE_CHAT"),
    ADD_USER_TO_CHAT("ADD_USER_TO_CHAT"),
    DEL_USER_FROM_CHAT("DEL_USER_FROM_CHAT"),
    DELETE_PRIVATE_CHAT("DELETE_PRIVATE_CHAT"),
    REQUEST_PUBLIC_KEY("REQUEST_PUBLIC_KEY"),
    SEND_PUBLIC_KEY("SEND_PUBLIC_KEY"),
    SEND_SYMMETRIC_KEY("SEND_SYMMETRIC_KEY"),
    VERIFY_SYMMETRIC_KEY("VERIFY_SYMMETRIC_KEY"),
    IDENTIFICATION("IDENTIFICATION");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static Command fromString(String command) {
        for (Command cmd : Command.values()) {
            if (cmd.getCommand().equalsIgnoreCase(command)) {
                return cmd;
            }
        }
        throw new IllegalArgumentException("Unknown command: " + command);
    }
}
