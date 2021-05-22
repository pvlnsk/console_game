package ru.cft.fs.game.client;


import java.net.URI;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.cft.fs.game.common.Command;
import ru.cft.fs.game.common.CommandDto;
import ru.cft.fs.game.common.GameObjectDto;
import ru.cft.fs.game.common.Paths;

@Slf4j
@Component
public class GameClient {

  private final RestTemplate restTemplate;
  private final URI uri;

  public GameClient(ClientProperties properties) {
    restTemplate = new RestTemplate();
    uri = properties.getUri();
  }


  public void sendCommand(Command command) {
    final CommandDto request = new CommandDto();
    request.setCommand(command);
    final ResponseEntity<String> responseEntity = restTemplate
        .postForEntity(uri + Paths.COMMAND, request, String.class);
    log.info("sendCommand response: {}", responseEntity.getBody());
  }

  public String move(GameObjectDto gameObjectDto) {
    final ResponseEntity<String> responseEntity = restTemplate
        .postForEntity(uri + Paths.MOVE, gameObjectDto, String.class);
    final String body = responseEntity.getBody();
    log.info("move response: {}", body);
    return body;
  }

  public String getGameState() {
    final ResponseEntity<String> responseEntity = restTemplate
        .getForEntity(uri + Paths.GAME_STATE, String.class);
    final String body = responseEntity.getBody();
    log.info("getGameState response: {}", body);
    return body;
  }

  public boolean checkPossibleMove(GameObjectDto gameObject) {
    final ResponseEntity<Boolean> responseEntity = restTemplate
        .postForEntity(uri + Paths.CHECK_POSSIBLE_MOVE, gameObject, Boolean.class);
    final Boolean body = responseEntity.getBody();
    log.info("getGameState response: {}", body);
    return Optional.ofNullable(body).orElseThrow(
        () -> new IllegalStateException("Server return empty body. Entity: " + responseEntity));
  }
}
