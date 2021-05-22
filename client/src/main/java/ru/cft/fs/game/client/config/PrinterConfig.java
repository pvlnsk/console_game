package ru.cft.fs.game.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.cft.fs.game.common.ConsolePrinter;

@Configuration
public class PrinterConfig {

  @Bean
  public ConsolePrinter consolePrinter() {
    return new ConsolePrinter(System.out::println);
  }
}
