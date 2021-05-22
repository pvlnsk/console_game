package ru.cft.fs.game.server.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.fs.game.common.Command;
import ru.cft.fs.game.common.CommandDto;
import ru.cft.fs.game.common.Constants;
import ru.cft.fs.game.common.GameObjectDto;
import ru.cft.fs.game.common.MoveResponse;
import ru.cft.fs.game.common.Paths;
import ru.cft.fs.game.server.GameEngine;

@Slf4j
@RestController
public class MainController {

  private final RabbitTemplate rabbitTemplate;
  private final GameEngine gameEngine;

  public MainController(
      RabbitTemplate rabbitTemplate,
      GameEngine gameEngine
  ) {
    this.rabbitTemplate = rabbitTemplate;
    this.gameEngine = gameEngine;
  }

  @PostMapping(Paths.COMMAND)
  public ResponseEntity<String> command(@Valid @NotNull @RequestBody CommandDto commandDto) {
    final Command command = commandDto.getCommand();
    execute(command);
    return ResponseEntity.ok().body("command complete: " + command);
  }

  @PostMapping(Paths.CHECK_POSSIBLE_MOVE)
  public ResponseEntity<Boolean> checkPossibleMove(
      @Valid @NotNull @RequestBody GameObjectDto gameObjectDto) {
    final boolean possibleMove = gameEngine.checkPossibleMove(gameObjectDto);
    return ResponseEntity.ok().body(possibleMove);
  }

  @PostMapping(Paths.MOVE)
  public ResponseEntity<MoveResponse> move(
      @Valid @NotNull @RequestBody GameObjectDto gameObjectDto) {
    //FIXME залогировать запросы
    log.info("current game state: {}", gameEngine.getGameStateAsText());
    if (!gameEngine.checkPossibleMove(gameObjectDto)) {
      //FIXME при проверке возвращать строку
      return ResponseEntity.ok(new MoveResponse("move incorrect: {}" + gameObjectDto, false));
    }
    gameEngine.acceptMove(gameObjectDto);
    notifyGameChangedState();
    return ResponseEntity.ok(new MoveResponse("move completed", true));
  }

  private void execute(Command command) {
    log.info("execute command: {}", command.getDescription());
    switch (command) {
      case NEW_GAME -> {
        gameEngine.reset();
        notifyGameChangedState();
        log.info("Start new game");
      }
      case EXIT -> log.info("пользователь отключитлся");
      default -> throw new IllegalStateException();
    }
  }

  private void notifyGameChangedState() {
    rabbitTemplate.convertAndSend(Constants.QUEUE_NAME, gameEngine.getGameStateAsText());
  }
}
