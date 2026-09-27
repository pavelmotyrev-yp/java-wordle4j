package ru.yandex.practicum.exception;

public class WordleDictionaryLoadException extends RuntimeException {
    public WordleDictionaryLoadException(String message) {
        super(message);
    }
}
