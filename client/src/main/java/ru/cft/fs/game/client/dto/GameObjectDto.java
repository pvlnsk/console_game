package ru.cft.fs.game.client.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.cft.fs.game.CellState;
import ru.cft.fs.game.Dice;

@RequiredArgsConstructor
@Getter
@ToString
public class GameObjectDto {

    private final int xCoordinate;
    private final int yCoordinate;
    private final Dice first;
    private final Dice second;
    private final CellState state;
}

