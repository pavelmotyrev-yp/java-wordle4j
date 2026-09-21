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
import ru.yandex.practicum.exception.IncorrectUserAnswerException;
import ru.yandex.practicum.repository.WordleDictionary;

import java.util.*;

public class WordleGameImpl implements WordleGame {
    private static final boolean CORRECT = true;
    private static final boolean INCORRECT = false;
    private final WordleDictionary wordleDictionary;
    private Set<String> invalidLetters;
    private Set<String> validLetters;
    private Map<Integer, String> validLettersPosition;
    private String secretWord;

    public WordleGameImpl(WordleDictionary wordleDictionary) {
        this.wordleDictionary = wordleDictionary;
        this.invalidLetters = new HashSet<>();
        this.validLetters = new HashSet<>();
        this.validLettersPosition = new HashMap<>();
    }

    @Override
    public void initialize() {
        Random rn = new Random();
        int wordPosition = rn.nextInt(wordleDictionary.getDictionarySize());
        Optional<String> optionalSecreteWord = wordleDictionary.getWord(wordPosition);
        if (optionalSecreteWord.isPresent()) {
            secretWord = optionalSecreteWord.get();
        } else {
            throw new RuntimeException("Некорректная инициализация игры");
        }
    }

    @Override
    public String getHint() {
        Optional<String> optionalHint = wordleDictionary.getWord(invalidLetters, validLetters, validLettersPosition);
        if (optionalHint.isPresent()) {
            return optionalHint.get();
        } else {
            throw new RuntimeException("Ошибка поиска подсказки");
        }
    }

    @Override
    public UserAnswerResult checkUserAnswer(String userAnswer) {
        if (!wordleDictionary.isExist(userAnswer)) {
            throw new IncorrectUserAnswerException("Слова " + userAnswer + " нет в словаре.");
        }

        if (userAnswer.equals(secretWord)) {
            return new UserAnswerResult(CORRECT, secretWord);
        }

        String hint = checkWord(userAnswer);

        return new UserAnswerResult(INCORRECT, hint);
    }

    private String checkWord(String userAnswer) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == userAnswer.charAt(i)) {
                validLettersPosition.put(i, String.valueOf(userAnswer.charAt(i)));
                sb.append("+");
            } else if (secretWord.contains(String.valueOf(userAnswer.charAt(i)))) {
                validLetters.add(String.valueOf(userAnswer.charAt(i)));
                sb.append("^");
            } else {
                invalidLetters.add(String.valueOf(userAnswer.charAt(i)));
                sb.append("-");
            }
        }
        return sb.toString();
    }

    @Override
    public String getSecretWord() {
        return secretWord;
    }
}
