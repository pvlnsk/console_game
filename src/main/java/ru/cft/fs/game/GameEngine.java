package ru.cft.fs.game;

import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import ru.cft.fs.game.dto.GameObjectDto;

@Slf4j
public class GameEngine {

    private final Cell[][] board;
    private final int boardSize;
    private final GameEngineChecker checker;

    public GameEngine(int height, int width) {
        this.checker = new GameEngineChecker(height, width);
        this.boardSize = height * width;

        this.board = createBoard(height, width);
    }

    private static Cell[][] createBoard(int height, int width) {
        log.info("run createBoard for parameters: {}, {}", height, width);
        Cell[][] newBoard = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newBoard[i][j] = new Cell();
            }
        }
        newBoard[0][0].changeState(CellState.PLAYER_ONE);
        newBoard[height - 1][width - 1].changeState(CellState.PLAYER_TWO);
        return newBoard;
    }

    public boolean checkNotEnclave(GameObjectDto gameObjectDto) {
        log.info("run checkNotEnclave GameObjectDto: {}", gameObjectDto);

        final int xCoordinate = gameObjectDto.getXCoordinate();
        final int yCoordinate = gameObjectDto.getYCoordinate();
        final int xLength = gameObjectDto.getFirst().getValue();
        final int yLength = gameObjectDto.getSecond().getValue();
        final Player player = gameObjectDto.getPlayer();

        for (int i = xCoordinate - 1; i < xCoordinate + xLength + 1; i++) {
            for (int j = yCoordinate - 1; j < yCoordinate + yLength + 1; j++) {
                final Optional<Cell> cell = getCell(i, j);
                if (cell.isPresent()) {
                    final CellState currentState = cell.get().getState();
                    if (currentState == state) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


    public boolean checkPossibleMove(GameObjectDto gameObjectDto) {
        log.info("run checkPossibleMove GameObjectDto: {}", gameObjectDto);
        Objects.requireNonNull(gameObjectDto);
        final int xCoordinate = gameObjectDto.getXCoordinate();
        final int yCoordinate = gameObjectDto.getYCoordinate();
        final int xLength = gameObjectDto.getFirst().getValue();
        final int yLength = gameObjectDto.getSecond().getValue();
        if (xCoordinate + xLength > getHeight() || yCoordinate + yLength > getWidth()) {
            return false;
        }

        for (int i = xCoordinate; i < xCoordinate + xLength; i++) {
            for (int j = yCoordinate; j < yCoordinate + yLength; j++) {
                if (!board[i][j].isPossibleChangeState()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void acceptMove(GameObjectDto gameObjectDto) {
        log.info("run acceptMove GameObjectDto: {}", gameObjectDto);
        final int xCoordinate = gameObjectDto.getXCoordinate();
        final int yCoordinate = gameObjectDto.getYCoordinate();
        final Optional<String> resultCheck = checker.checkCoordinate(
            xCoordinate,
            yCoordinate);
        if (resultCheck.isPresent()) {
            throw new IllegalArgumentException(resultCheck.get());
        }
        final CellState newCellState = gameObjectDto.getPlayer().getCellState();
        updateCells(xCoordinate, yCoordinate, gameObjectDto.getFirst(), gameObjectDto.getSecond(), newCellState);
    }

    private void updateCells(GameObjectDto gameObjectDto) {
        log.info("updatedCells");
        final int x = gameObjectDto.getXCoordinate();
        final int y = gameObjectDto.getYCoordinate();
        final Dice first = gameObjectDto.getFirst();
        final Dice second = gameObjectDto.getSecond();
        final CellState state = gameObjectDto.getState();

        for (int i = x; i < x + first.getValue(); i++) {
            for (int j = y; j < y + second.getValue(); j++) {
                Cell cell = board[i][j];
                cell.changeState(state);
            }
        }
    }

    public String getGameStateAsText() {
        StringBuilder sb = new StringBuilder(boardSize);
        for (Cell[] rowCells : board) {
            for (Cell cell : rowCells) {
                sb.append(cell.getAsText()).append(" ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    private static class Cell {

        private CellState state;

        public Cell() {
            this.state = CellState.DEFAULT;
        }

        public boolean isPossibleChangeState() {
            return state.isPossibleChangeState();
        }

        public void changeState(CellState state) {
            this.state = state;
        }

        public String getAsText() {
            return state.getSymbol();
        }
    }
}
