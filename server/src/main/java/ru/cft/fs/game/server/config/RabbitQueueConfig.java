package ru.cft.fs.game.server.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.cft.fs.game.common.Constants;

@Configuration
public class RabbitQueueConfig {

  @Bean
  public Queue gameQueue() {
    return new Queue(Constants.QUEUE_NAME, false);
  }
}
