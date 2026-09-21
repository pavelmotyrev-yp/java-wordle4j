package ru.yandex.practicum.service;

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
