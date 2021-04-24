package ru.cft.fs.game;

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
            Command command = Command.parseCommand(line);
            gameController.execute(command);

            gameController.changePlayer();
        }
    }
}
