package ru.cft.fs.game.client;

import java.util.function.IntBinaryOperator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Player {

    FIRST(CellState.PLAYER_ONE, (coordinate, diceValue) -> coordinate) {
        @Override
        public Player nextPlayer() {
            log.info("run nextPlayer from FIRST Player");

            return SECOND;
        }
    },
    SECOND(CellState.PLAYER_TWO, (coordinate, diceValue) -> coordinate - diceValue + 1) {
        @Override
        public Player nextPlayer() {
            log.info("run nextPlayer from SECOND Player");

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
