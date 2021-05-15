package ru.cft.fs.game.client;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        GameController gameController = new GameController(new GameEngine(50, 50),
            new ConsolePrinter());

        gameController.execute(Command.NEW_GAME);

        while (true) {
            gameController.move();
        }
    }
}
