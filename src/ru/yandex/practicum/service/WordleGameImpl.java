package ru.yandex.practicum.service;

import ru.yandex.practicum.entity.UserAnswerResult;
import ru.yandex.practicum.exception.IncorrectUserAnswerException;
import ru.yandex.practicum.logger.Logger;
import ru.yandex.practicum.repository.WordleDictionary;

import java.util.*;

public class WordleGameImpl implements WordleGame {
    private static final boolean CORRECT = true;
    private static final boolean INCORRECT = false;
    private final WordleDictionary wordleDictionary;
    private final Logger logger;
    private Set<String> invalidLetters;
    private Set<String> validLetters;
    private Map<Integer, String> validLettersPosition;
    private Set<String> invalidWords;
    private String secretWord;

    public WordleGameImpl(WordleDictionary wordleDictionary, Logger logger) {
        this.wordleDictionary = wordleDictionary;
        this.logger = logger;
        this.invalidLetters = new HashSet<>();
        this.validLetters = new HashSet<>();
        this.invalidWords = new HashSet<>();
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
            logger.log("Некорректная инициализация игры");
            throw new RuntimeException("Некорректная инициализация игры");
        }
    }

    @Override
    public String getHint() {
        Optional<String> optionalHint = wordleDictionary.getWord(invalidLetters, validLetters,
                validLettersPosition, invalidWords);
        if (optionalHint.isPresent()) {
            return optionalHint.get();
        } else {
            logger.log("Ошибка поиска подсказки");
            throw new RuntimeException("Ошибка поиска подсказки");
        }
    }

    @Override
    public UserAnswerResult checkUserAnswer(String userAnswer) {
        if (!wordleDictionary.isExist(userAnswer)) {
            logger.log("Слова " + userAnswer + " нет в словаре.");
            throw new IncorrectUserAnswerException("Слова " + userAnswer + " нет в словаре.");
        }

        if (userAnswer.equals(secretWord)) {
            return new UserAnswerResult(CORRECT, secretWord);
        }

        String hint = checkWord(userAnswer);

        invalidWords.add(hint);

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
