package ru.yandex.practicum.service;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */

/*WordleGame — класс игры, хранит её состояние, то есть все переменные, которые относятся непосредственно к
процессу угадывания слов из словаря. Это может быть количество оставшихся шагов, правильный ответ и, самое главное,
словарь вариантов. Методы игрового класса позволяют сделать ход, проверить ответ и вычислить подсказку. Важно, что
класс игры не взаимодействует с пользователем или консолью, это задача главного класса.*/

import ru.yandex.practicum.entity.UserAnswerResult;
import ru.yandex.practicum.repository.WordleDictionary;

public class WordleGameImpl implements WordleGame {
    private final WordleDictionary wordleDictionary;

    public WordleGameImpl(WordleDictionary wordleDictionary) {
        this.wordleDictionary = wordleDictionary;
    }

    @Override
    public void initialize() {

    }

    @Override
    public boolean getHint() {
        return false;
    }

    @Override
    public UserAnswerResult checkUserAnswer(String userAnswer) {
        return null;
    }

    @Override
    public Object getSecreteWord() {
        return null;
    }
}
