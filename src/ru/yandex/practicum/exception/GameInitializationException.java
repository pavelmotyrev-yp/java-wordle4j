package ru.yandex.practicum.exception;

public class GameInitializationException extends RuntimeException {
    public GameInitializationException(String message) {
        super(message);
    }
}
