package ru.cft.fs.game.server.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.fs.game.common.Command;
import ru.cft.fs.game.common.CommandDto;
import ru.cft.fs.game.common.Paths;
import ru.cft.fs.game.server.GameController;

@RestController
public class WebController {

  private final GameController gameController;

  public WebController(GameController gameController) {
    this.gameController = gameController;
  }

  @PostMapping(Paths.COMMAND)
  public ResponseEntity<String> command(@Valid @NotNull @RequestBody CommandDto commandDto) {
    final Command command = commandDto.getCommand();
    gameController.execute(command);
    return ResponseEntity.ok().body("command complete: " + command);
  }

  @GetMapping(Paths.MOVE)
  public ResponseEntity<String> command() {
    gameController.move();
    return ResponseEntity.ok().body("move complete");
  }

  @GetMapping(Paths.GAME_STATE)
  public ResponseEntity<String> gameState() {
    final String gameState = gameController.getGameState();
    return ResponseEntity.ok().body(gameState);
  }
}
