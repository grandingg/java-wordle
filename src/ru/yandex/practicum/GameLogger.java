package ru.yandex.practicum;

import java.io.PrintWriter;

public class GameLogger {
    private final PrintWriter writer;


    public GameLogger(PrintWriter writer) {
        this.writer = writer;
    }

    public void log(String message) {
        writer.println(message);
        writer.flush();
    }

    public void logError(Exception e) {
        writer.println("Ошибка: " + e.getMessage());
        e.printStackTrace(writer);
        writer.flush();
    }

}
