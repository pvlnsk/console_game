package ru.cft.fs.game;

import java.util.Optional;

class GameEngineChecker {

    private final int height;
    private final int width;

    GameEngineChecker(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public Optional<String> checkCoordinate(int x, int y) {
        if (x >= height || y >= width) {
            return Optional.of("Выходит за границы поля");
        }
        if (x < 0 || y < 0) {
            return Optional.of("Не может быть меньше 0");
        }
        return Optional.empty();
    }
}
