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
        return newBoard;
    }

    public boolean checkNotEnclave(GameObjectDto gameObjectDto) {
        log.info("run checkNotEnclave GameObjectDto: {}", gameObjectDto);

        final int xCoordinate = gameObjectDto.getXCoordinate();
        final int yCoordinate = gameObjectDto.getYCoordinate();
        final int xLength = gameObjectDto.getFirst().getValue();
        final int yLength = gameObjectDto.getSecond().getValue();
        final CellState state = gameObjectDto.getState();

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

        for (int i = xCoordinate - 1; i < xCoordinate + xLength - 1; i++) {
            for (int j = yCoordinate - 1; j < yCoordinate + yLength - 1; j++) {
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
        final CellState newCellState = gameObjectDto.getState();
        updateCells(gameObjectDto);
    }

    private void updateCells(GameObjectDto gameObjectDto) {
        log.info("Updating cells {}", gameObjectDto);
        final int x = gameObjectDto.getXCoordinate();
        final int y = gameObjectDto.getYCoordinate();
        final Dice first = gameObjectDto.getFirst();
        final Dice second = gameObjectDto.getSecond();
        final CellState state = gameObjectDto.getState();
        log.info("current state symbol: {}", state.getSymbol());

        for (int i = x - 1; i < x + first.getValue() - 1; i++) {
            for (int j = y - 1; j < y + second.getValue() - 1; j++) {
                Cell cell = board[i][j];
                cell.changeState(state);
            }
        }
    }

    public void reset() {
        log.info("Resetting view");
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                board[i][j].setDefault();
            }
        }
        board[0][0].changeState(CellState.PLAYER_ONE);
        board[getHeight() - 1][getWidth() - 1].changeState(CellState.PLAYER_TWO);
    }

    public String getGameStateAsText() {
        log.info("run method getGameStateAsText");
        StringBuilder sb = new StringBuilder(boardSize);
        addColNumbers(sb);
        sb.append(System.lineSeparator());

        for (int row = 1; row <= getHeight(); row++) {
            sb.append(formattedNumber(row))
                .append(" ");
            for (int col = 1; col <= getWidth(); col++) {
                sb.append(board[row - 1][col - 1].getAsText()).append(" ");
            }
            sb.append(formattedNumber(row));
            sb.append(" ");
            sb.append(System.lineSeparator());
        }

        addColNumbers(sb);

        return sb.toString();
    }

    private void addColNumbers(StringBuilder sb) {
        sb.append("  ");
        for (int col = 1; col <= getWidth(); col++) {
            sb.append(formattedNumber(col));
        }
    }

    private String formattedNumber(int number) {
        return "%2d".formatted(number);
    }

    public int getHeight() {
        return board.length;
    }

    public int getWidth() {
        return board[0].length;
    }

    private Optional<Cell> getCell(int col, int row) {
        if (col < 0 || row < 0 || col >= getHeight() || row >= getWidth()) {
            return Optional.empty();
        }
        return Optional.of(board[col][row]);
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

        public CellState getState() {
            return state;
        }

        public void setDefault() {
            state = CellState.DEFAULT;
        }
    }
}
