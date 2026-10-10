package ru.yandex.practicum.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final String LOG_FILE = "app.log";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void log(String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String line = "[" + timestamp + "] " + message;
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(line);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write log: " + e.getMessage());
        }
    }
}
