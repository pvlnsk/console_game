package ru.cft.fs.game;

import lombok.Getter;

import java.util.Random;
import java.util.stream.Stream;

public enum Dice {
    _1(1, ""),
    _2(2, ""),
    _3(3, ""),
    _4(4, ""),
    _5(5, ""),
    _6(6, "");

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

    private static Dice ofInt(int _int) {
        return Stream.of(Dice.values())
                .filter(dice -> dice.value == _int)
                .findAny()
                .orElseThrow();
    }

    public static Dice randomDice() {
        Random random = Dice.random;
        int value = random.nextInt(SIZE) + 1;
        return ofInt(value);
    }
}
