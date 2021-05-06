package ru.cft.fs.game;

import lombok.Getter;


public enum Player {

    FIRST(CellState.PLAYER_ONE, (coordinate, diceValue) -> coordinate) {
        @Override
        public Player nextPlayer() {
            return SECOND;
        }
    },
    SECOND(CellState.PLAYER_TWO, (coordinate, diceValue) -> coordinate - diceValue + 1) {
        @Override
        public Player nextPlayer() {
            return FIRST;
        }
    };

    @Getter
    private final CellState cellState;
    private final IntBinaryOperator calculateInitCoordinateFunction;

    Player(CellState cellState,
        IntBinaryOperator calculateInitCoordinateFunction) {
        this.cellState = cellState;
        this.calculateInitCoordinateFunction = calculateInitCoordinateFunction;
    }

    public int calculateCoordinate(int coordinate, int dice) {
        return calculateInitCoordinateFunction.applyAsInt(coordinate, dice);
    }

    public abstract Player nextPlayer();
}
