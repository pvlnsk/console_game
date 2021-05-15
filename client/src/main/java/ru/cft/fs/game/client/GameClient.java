package ru.cft.fs.game.client;


import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.cft.fs.game.common.Command;
import ru.cft.fs.game.common.CommandDto;
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
    final ResponseEntity<String> forEntity = restTemplate
        .postForEntity(uri + Paths.COMMAND, request, String.class);
    log.info("sendCommand response: {}", forEntity.getBody());
  }

  public void move() {
    final ResponseEntity<String> forEntity = restTemplate
        .getForEntity(uri + Paths.MOVE, String.class);
    log.info("move response: {}", forEntity.getBody());
  }

  public String getGameState() {
    final ResponseEntity<String> forEntity = restTemplate
        .getForEntity(uri + Paths.GAME_STATE, String.class);
    final String body = forEntity.getBody();
    log.info("getGameState response: {}", body);
    return body;
  }
}
