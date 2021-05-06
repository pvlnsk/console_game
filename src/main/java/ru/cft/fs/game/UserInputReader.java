package ru.cft.fs.game;

import java.util.Scanner;

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
