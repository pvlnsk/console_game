package ru.cft.fs.game.server;

import java.util.Random;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Dice {
    FIRST_SIDE(1, "D1"),
    SECOND_SIDE(2, "D2"),
    THIRD_SIDE(3, "D3"),
    FORTH_SIDE(4, "D4"),
    FIFTH_SIDE(5, "D5"),
    SIXTH_SIDE(6, "D6");

    private static final Random random = new Random();
    private static final int SIZE = Dice.values().length;

    @Getter
    private final int value;
    @Getter
    private final String text;

    Dice(int i, String text) {
        this.value = i;
        this.text = text;
    }

    private static Dice ofInt(int value) {
        log.info("run ofInt with parameter: {}", value);

        return Stream.of(Dice.values())
            .filter(dice -> dice.value == value)
            .findAny()
            .orElseThrow();
    }

    public static Dice randomDice() {
        log.info("run randomDice");

        Random random = Dice.random;
        int value = random.nextInt(SIZE) + 1;
        return ofInt(value);
    }
}
