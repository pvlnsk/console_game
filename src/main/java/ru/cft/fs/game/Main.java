package ru.cft.fs.game;

import ru.cft.fs.game.dto.GameObjectDto;

import java.util.Random;
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
            System.out.println("input: " + line);
            Command command = Command.parseCommand(line);
            if (command == Command.EXIT) {
                break;
            }
            if (command == Command.NEW_GAME) {
                gameEngine = new GameEngine(50, 50);
                System.out.println("Start new game");
            }

            if (command == Command.MOVE) {
                if (gameEngine == null) {
                    break;
                }
                final GameObjectDto gameObjectDto = new GameObjectDto(
                        random.nextInt(46),
                        random.nextInt(46),
                        Dice.randomDice(),
                        Dice.randomDice(),
                        currentPlayer);
                gameEngine.acceptMove(gameObjectDto);
            }
            if (command == Command.DRAW) {
                if (gameEngine == null) {
                    break;
                }
                String gameState = gameEngine.getGameStateAsText();
                System.out.println(gameState);
            }

        }
    }
}
