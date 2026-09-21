package ru.yandex.practicum.service;

import ru.yandex.practicum.entity.UserAnswerResult;

public interface WordleGame {
    void initialize();

    String getHint();

    UserAnswerResult checkUserAnswer(String userAnswer);

    String getSecretWord();
}
