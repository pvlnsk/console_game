package ru.cft.fs.game.client;


import java.net.URI;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("connection")
public class ClientProperties {

  @NotNull
  private URI uri;
}
