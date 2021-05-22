package ru.cft.fs.game.common;

import java.util.function.Consumer;

public class ConsolePrinter implements Printer {

    private final Consumer<String> consumer;

    public ConsolePrinter(Consumer<String> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void print(String text) {
        consumer.accept(text);
    }
}
