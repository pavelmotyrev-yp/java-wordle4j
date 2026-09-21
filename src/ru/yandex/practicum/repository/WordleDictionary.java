package ru.yandex.practicum.repository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface WordleDictionary {
    int getDictionarySize();

    Optional<String> getWord(int wordPosition);

    Optional<String> getWord(Set<String> invalidLetters, Set<String> validLetters, Map<Integer, String> validLettersPosition);

    boolean isExist(String userAnswer);
}
