package ru.cft.fs.game;

import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Random random = new Random();

        GameEngine gameEngine = null;
        Player lastPlayer = Player.FIRST;
        while (true) {
            Player currentPlayer = lastPlayer = lastPlayer._nextPlayer();

            String line = scanner.nextLine();
            Optional<Command> command = Command.parseCommand(line);
            command.ifPresent(gameController::execute);
            System.out.println("press enter");
            gameController.move();
        }
    }
}
