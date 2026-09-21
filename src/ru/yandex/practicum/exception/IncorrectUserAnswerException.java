package ru.yandex.practicum.exception;

public class IncorrectUserAnswerException extends RuntimeException{
    public IncorrectUserAnswerException(String message) {
        super(message);
    }
}
