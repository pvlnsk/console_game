package ru.cft.fs.game;

import lombok.Getter;

import java.util.Map;


public enum Player {

    FIRST(CellState.PLAYER_ONE) {
        @Override
        public Player _nextPlayer() {
            return SECOND;
        }
    },
    SECOND(CellState.PLAYER_TWO) {
        @Override
        public Player _nextPlayer() {
            return FIRST;
        }
    };

    private static final Map<Player, Player> MAP = Map.of(
            Player.FIRST, Player.SECOND,
            Player.SECOND, Player.FIRST
    );
    @Getter
    private final CellState cellState;


    Player(CellState cellState) {
        this.cellState = cellState;
    }


    public Player nextPlayer() {
        return MAP.get(this);
    }

    public abstract Player _nextPlayer();
}
