package ru.cft.fs.game;

import java.util.Scanner;

public class UserInputReader {

    private final Scanner scanner;

    public UserInputReader() {
        this.scanner = new Scanner(System.in);
    }

    public String getLine() {
        return scanner.nextLine();
    }
}
