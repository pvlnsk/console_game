package ru.cft.fs.game.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GameObjectDto {

    private int xCoordinate;
    private int yCoordinate;
    private Dice first;
    private Dice second;
    private CellState state;
}

