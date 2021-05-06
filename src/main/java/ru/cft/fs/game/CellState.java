package ru.cft.fs.game;

import lombok.Getter;


@Getter
public enum CellState {
    DEFAULT("#", true, Colors.ANSI_RESET),
    PLAYER_ONE("X", false, Colors.ANSI_RED),
    PLAYER_TWO("O", false, Colors.ANSI_GREEN);

    private final String symbol;
    private final boolean possibleChangeState;

    CellState(String symbol, boolean possibleChangeState, String color) {
        this.symbol = color + symbol + Colors.ANSI_RESET;
        this.possibleChangeState = possibleChangeState;
    }
}
