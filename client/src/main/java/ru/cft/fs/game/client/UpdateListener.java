package ru.cft.fs.game.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.cft.fs.game.common.ConsolePrinter;
import ru.cft.fs.game.common.Constants;

@Slf4j
@Service
public class UpdateListener {

  private static final String EMPTY_STRING = " ";
  private static final String GAME_STATE_SEPARATOR =
      EMPTY_STRING.repeat(15) + "\n" + EMPTY_STRING.repeat(30) + "!!! GAME STATE !!!" + "\n";
  private static final String GAME_STATE_TEMPLATE =
      GAME_STATE_SEPARATOR + "%s" + GAME_STATE_SEPARATOR;

  private final ConsolePrinter consolePrinter;

  public UpdateListener(ConsolePrinter consolePrinter) {
    this.consolePrinter = consolePrinter;
  }

  @RabbitListener(queues = Constants.QUEUE_NAME)
  public void consumeGameState(String gameStateText) {

    consolePrinter.print(String.format(GAME_STATE_TEMPLATE, gameStateText));
  }
}
