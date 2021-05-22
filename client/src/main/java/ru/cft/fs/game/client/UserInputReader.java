package ru.cft.fs.game.client;

import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserInputReader {

    private final Scanner scanner;

    public UserInputReader() {
        this.scanner = new Scanner(System.in);
    }

    public String getLine() {
        log.info("Waiting for user input...");

        final String input = scanner.nextLine();
        log.info("User input: \"{}\"", input);
        return input;
    }
}
