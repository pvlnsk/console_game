package ru.cft.fs.game;

import java.util.Optional;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum Command {
    EXIT("exit", "Выход"),
    NEW_GAME("new game", "Новая игра");

    private final String code;
    private final String description;

    Command(String code, String description) {
        this.code = code;
        this.description = description;
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
