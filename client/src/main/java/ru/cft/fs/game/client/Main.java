package ru.cft.fs.game.client;

import java.util.Optional;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.cft.fs.game.common.Command;
import ru.cft.fs.game.common.ConsolePrinter;
import ru.cft.fs.game.common.Dice;
import ru.cft.fs.game.common.GameObjectDto;
import ru.cft.fs.game.common.Player;

@Slf4j
@Service
public class Main {

    private final GameClient gameClient;
    private final ConsolePrinter consolePrinter;
    private final UserInputReader userInputReader;

    private Player currentPlayer = Player.FIRST;

    public Main(GameClient gameClient, ConsolePrinter consolePrinter,
        UserInputReader userInputReader) {
        this.gameClient = gameClient;
        this.consolePrinter = consolePrinter;
        this.userInputReader = userInputReader;
    }

    @EventListener
    public void main(ContextRefreshedEvent event) {
        gameClient.sendCommand(Command.NEW_GAME);
        while (true) {
            initMove();
        }
    }

    private void initMove() {
        final Optional<GameObjectDto> validGameDto = createValidGameDto();
        validGameDto.map(gameClient::move).ifPresent(text -> {
            consolePrinter.print(text);
            changePlayer();
        });
    }

    public Optional<GameObjectDto> createValidGameDto() {
        log.info("createValidGameDto started");
        consolePrinter.print("Throwing dices");
        var dice1 = Dice.randomDice();
        var dice2 = Dice.randomDice();
        log.info("Roll result: {} and {}", dice1, dice2);
        while (true) {
            consolePrinter.print("Enter coordinates of rectangle with sides %d and %d"
                .formatted(dice1.getValue(), dice2.getValue())
            );

            try {
                String input = userInputReader.getLine();

                final Optional<Command> command = Command.parseCommand(input);
                if (command.isPresent()) {
                    execute(command.get());
                    return Optional.empty();
                }
                Scanner sc = new Scanner(input);
                int x = currentPlayer.calculateCoordinate(sc.nextInt(), dice1.getValue());
                int y = currentPlayer.calculateCoordinate(sc.nextInt(), dice2.getValue());
                var gameObject = new GameObjectDto(x, y, dice1, dice2,
                    currentPlayer.getCellState());
                //FIXME выводить из чекера Optional и использовать его строку, для отображения пользователю
                if (!gameClient.checkPossibleMove(gameObject)) {
                    log.info("input coordinates are not valid: x={}, y={}", x, y);
                    consolePrinter.print("Move (%d, %d) is not possible".formatted(x, y));
                    continue;
                }
                log.info("accepted input coordinates: x={}, y={}", x, y);
                return Optional.of(gameObject);
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
                consolePrinter.print("Incorrect input. Enter two integer values");
            }
        }
    }

    public void execute(Command command) {
        log.info("run method execute(command: {})", command.getDescription());
        if (command == Command.EXIT) {
            //FIXME
            System.exit(0);
            return;
        }
        gameClient.sendCommand(command);
    }

    private void changePlayer() {
        currentPlayer = currentPlayer.nextPlayer();
        log.info("player changed: {}", currentPlayer);
        consolePrinter.print("turn player: " + currentPlayer.name());
    }
}
