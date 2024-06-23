package ru.otus.basic.yampolskiy.protocol;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public enum Command {
    REGISTER("REGISTER"),
    REGISTER_COMPLETE("REGISTER_COMPLETE"),
    LOGIN("LOGIN"),
    LOGIN_COMPLETE("LOGIN_COMPLETE"),
    REQUEST_PUBLIC_KEY("REQUEST_PUBLIC_KEY"),
    SEND_PUBLIC_KEY("SEND_PUBLIC_KEY"),
    SEND_SYMMETRIC_KEY("SEND_SYMMETRIC_KEY"),
    VERIFY_SYMMETRIC_KEY("VERIFY_SYMMETRIC_KEY"),
    SEND_MESSAGE("SEND_MESSAGE"),
    LOGOUT("LOGOUT"),
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
