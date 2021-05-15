package ru.cft.fs.game.server;

import org.springframework.stereotype.Component;

@Component
public class ConsolePrinter {

    public void print(String text) {
        System.out.println(text);
    }
}
