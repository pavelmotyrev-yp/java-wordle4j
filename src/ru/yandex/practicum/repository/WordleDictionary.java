package ru.yandex.practicum.repository;

import java.util.Map;
import java.util.Set;

public interface WordleDictionary {
    int getDictionarySize();

    String getWord(int wordPosition);

    String getWord(Set<String> invalidLetters, Set<String> validLetters, Map<Integer, String> validLettersPosition);

    boolean isExist(String userAnswer);
}
