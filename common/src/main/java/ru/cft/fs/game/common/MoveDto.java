package ru.cft.fs.game.common;


import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MoveDto {

  @Min(1)
  private int xCoordinate;
  @Min(1)
  private int yCoordinate;
}
