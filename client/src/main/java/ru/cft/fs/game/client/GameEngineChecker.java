package ru.cft.fs.game.client;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class GameEngineChecker {

    private final int height;
    private final int width;

    GameEngineChecker(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public Optional<String> checkCoordinate(int x, int y) {
        log.info("Checking coordinates ({}, {})", x, y);
        if (x >= height || y >= width) {
            return Optional.of("Выходит за границы поля");
        }
        if (x < 0 || y < 0) {
            return Optional.of("Не может быть меньше 0");
        }
        log.info("Coordinates ({}, {}) are correct", x, y);
        return Optional.empty();
    }
}
