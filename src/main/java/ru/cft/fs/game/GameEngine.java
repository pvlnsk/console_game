package ru.cft.fs.game;

import ru.cft.fs.game.dto.GameObjectDto;

import java.util.Optional;

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
        Cell[][] newBoard = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newBoard[i][j] = new Cell();
            }
        }
        return newBoard;
    }

    public boolean checkNotEnclave(GameObjectDto gameObjectDto) {
        final int xCoordinate = gameObjectDto.getXCoordinate();
        final int yCoordinate = gameObjectDto.getYCoordinate();
        final int xLength = gameObjectDto.getFirst().getValue();
        final int yLength = gameObjectDto.getSecond().getValue();
        final Player player = gameObjectDto.getPlayer();

        for (int i = xCoordinate; i < xCoordinate + xLength; i++) {
            for (int j = yCoordinate; j < yCoordinate + yLength; j++) {
                if (i >= 0 && i < getHeight() &&
                        j >= 0 && j < getWidth()) {
                    if (board[i][j].getState() == player.getCellState()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


    public boolean checkPossibleMove(GameObjectDto gameObjectDto) {
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

    public void updateCells(int x, int y, Dice first, Dice second, CellState state) {
        System.out.println("updatedCells");
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
