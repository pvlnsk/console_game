package ru.cft.fs.game.server.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.cft.fs.game.server.CellState;
import ru.cft.fs.game.server.Dice;

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

