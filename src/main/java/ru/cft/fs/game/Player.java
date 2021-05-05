package ru.cft.fs.game;

import lombok.Getter;


public enum Player {

    FIRST(CellState.PLAYER_ONE) {
        @Override
        public Player nextPlayer() {
            return SECOND;
        }
    },
    SECOND(CellState.PLAYER_TWO) {
        @Override
        public Player nextPlayer() {
            return FIRST;
        }
    };

    @Getter
    private final CellState cellState;


    Player(CellState cellState) {
        this.cellState = cellState;
    }

    public abstract Player nextPlayer();
}
