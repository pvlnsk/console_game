package ru.cft.fs.game;

import java.util.Optional;
import java.util.stream.Stream;
import lombok.Getter;

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

    public static Optional<Command> parseCommand(String code) {
        return Stream.of(Command.values())
            .filter(command -> command.getCode().equalsIgnoreCase(code))
            .findAny();
    }

    public String getDescription() {
        return description;
    }
}
