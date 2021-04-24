package ru.cft.fs.game.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.cft.fs.game.Dice;
import ru.cft.fs.game.Player;

@RequiredArgsConstructor
@Getter
public class GameObjectDto {
    private final int xCoordinate;
    private final int yCoordinate;
    private final Dice first;
    private final Dice second;
    private final Player player;
}

