package ru.cft.fs.game;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Command {
    EXIT("exit"),
    NEW_GAME("new game"),
    MOVE("move"),
    UNKNOWN("unknown"),
    DRAW("draw");

    private final String code;

    Command(String code) {
        this.code = code;
    }

    public static Command parseCommand(String code) {
        return Stream.of(Command.values())
                .filter(command -> command.getCode().equalsIgnoreCase(code))
                .findAny()
                .orElse(Command.UNKNOWN);
    }
}
