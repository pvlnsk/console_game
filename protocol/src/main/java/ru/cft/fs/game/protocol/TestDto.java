package ru.cft.fs.game.protocol;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class TestDto {

  @NotNull
  private final String text;
}
