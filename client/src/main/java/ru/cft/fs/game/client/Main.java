package ru.cft.fs.game.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.cft.fs.game.common.Command;

@Slf4j
@Service
public class Main {

    private final GameClient gameClient;

    public Main(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    @EventListener
    public void main(ContextRefreshedEvent event) {
        gameClient.sendCommand(Command.NEW_GAME);
        while (true) {
            String gameState = gameClient.getGameState();
            System.out.println(System.lineSeparator() + gameState);
            gameClient.move();
        }
    }
}
