package ru.cft.fs.game;

import java.util.Optional;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import ru.cft.fs.game.dto.GameObjectDto;

@Slf4j
public class GameController {

    private final GameEngine gameEngine;
    private final ConsolePrinter consolePrinter;
    private final UserInputReader scanner = new UserInputReader();
    private Player currentPlayer = Player.FIRST;

    public GameController(GameEngine gameEngine, ConsolePrinter consolePrinter) {
        this.gameEngine = gameEngine;
        this.consolePrinter = consolePrinter;
    }


    public void move() {
        consolePrinter.print(gameEngine.getGameStateAsText());
        final Optional<GameObjectDto> validGameDto = createValidGameDto();
        if (validGameDto.isEmpty()) {
            return;
        }
        gameEngine.acceptMove(validGameDto.get());
        consolePrinter.print(gameEngine.getGameStateAsText());
        changePlayer();
    }

    public void execute(Command command) {
        log.info("execute command: {}", command.getDescription());
        switch (command) {
            case NEW_GAME -> {
                gameEngine.reset();
                consolePrinter.print("Start new game");
            }
            case EXIT -> System.exit(0);
            default -> throw new IllegalStateException();
        }
    }

    public Optional<GameObjectDto> createValidGameDto() {
        consolePrinter.print("Throwing dices");
        var dice1 = Dice.randomDice();
        var dice2 = Dice.randomDice();
        while (true) {
            consolePrinter.print("Enter coordinates of rectangle with sides %d and %d"
                .formatted(dice1.getValue(), dice2.getValue())
            );

            try {
                String input = scanner.getLine();
                final Optional<Command> command = Command.parseCommand(input);
                if (command.isPresent()) {
                    execute(command.get());
                    return Optional.empty();
                }
                Scanner sc = new Scanner(input);
                int x = sc.nextInt();
                int y = sc.nextInt();
                var gameObject = new GameObjectDto(x, y, dice1, dice2,
                    currentPlayer.getCellState());
                if (!gameEngine.checkPossibleMove(gameObject)) {
                    consolePrinter.print("Move (%d, %d) is not possible".formatted(x, y));
                    continue;
                }
                return Optional.of(gameObject);
            } catch (Exception e) {
                consolePrinter.print("Incorrect input. Enter two integer values");
            }
        }
    }

    private void changePlayer() {
        currentPlayer = currentPlayer.nextPlayer();
        consolePrinter.print("turn player: " + currentPlayer.name());
    }
}
