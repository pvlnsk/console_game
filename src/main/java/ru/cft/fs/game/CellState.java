package ru.cft.fs.game;

import lombok.Getter;


@Getter
public enum CellState {
    DEFAULT("#", true),
    PLAYER_ONE("X", false),
    PLAYER_TWO("O", false);

    private final String symbol;
    private final boolean possibleChangeState;

    CellState(String symbol, boolean possibleChangeState) {
        this.symbol = symbol;
        this.possibleChangeState = possibleChangeState;
    }
}
