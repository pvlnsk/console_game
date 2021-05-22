package ru.cft.fs.game.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.cft.fs.game.common.Constants;

@Slf4j
@Service
public class UpdateListener {

  @RabbitListener(queues = Constants.QUEUE_NAME, concurrency = "2")
  public void consumeGameState(String gameStateText) {
    log.info("game state: {}", gameStateText);
  }
}
