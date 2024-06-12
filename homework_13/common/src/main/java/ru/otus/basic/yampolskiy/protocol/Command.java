package ru.otus.basic.yampolskiy.protocol;

public enum Command {
    REGISTER("REGISTER"),
    LOGIN("LOGIN"),
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
