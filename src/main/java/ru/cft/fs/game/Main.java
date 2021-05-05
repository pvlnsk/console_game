package ru.cft.fs.game;

import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        GameController gameController = new GameController(new GameEngine(50, 50),
            new ConsolePrinter());

        while (true) {
            String line = scanner.nextLine();
            Optional<Command> command = Command.parseCommand(line);
            command.ifPresent(gameController::execute);
            System.out.println("press enter");
            gameController.move();
        }
    }
}
