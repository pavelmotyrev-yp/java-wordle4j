package ru.yandex.practicum.service;

import ru.yandex.practicum.entity.UserAnswerResult;

public interface WordleGame {
    void initialize();

    boolean getHint();

    UserAnswerResult checkUserAnswer(String userAnswer);

    Object getSecreteWord();
}
