package ru.yandex.practicum.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class WordleDictionaryImpl implements WordleDictionary {

    private final List<String> words;

    public WordleDictionaryImpl(List<String> words) {
        this.words = words;
    }

    @Override
    public int getDictionarySize() {
        return words.size();
    }

    @Override
    public Optional<String> getWord(int wordPosition) {
        return Optional.ofNullable(words.get(wordPosition));
    }

    @Override
    public Optional<String> getWord(Set<String> invalidLetters, Set<String> validLetters,
                                    Map<Integer, String> validLettersPosition, Set<String> invalidWords) {
        for (String word : words) {

            if (invalidWords.contains(word)) continue;

            if (hasInvalid(invalidLetters, word)) continue;

            if (!containsAllValidLetters(validLetters, word)) continue;

            if (!positionsMatch(validLettersPosition, word)) continue;

            return Optional.of(word);
        }
        return Optional.empty();
    }

    @Override
    public boolean isExist(String userAnswer) {
        return words.contains(userAnswer);
    }

    private boolean positionsMatch(Map<Integer, String> validLettersPosition, String word) {
        if (validLettersPosition != null && !validLettersPosition.isEmpty()) {
            for (Map.Entry<Integer, String> entry : validLettersPosition.entrySet()) {
                int pos = entry.getKey();
                String letter = entry.getValue();

                if (word.charAt(pos) != letter.charAt(0)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean containsAllValidLetters(Set<String> validLetters, String word) {
        if (validLetters != null && !validLetters.isEmpty()) {
            return validLetters.stream()
                    .allMatch(word::contains);
        } else {
            return true;
        }
    }

    private boolean hasInvalid(Set<String> invalidLetters, String word) {
        if (invalidLetters != null && !invalidLetters.isEmpty()) {
            return invalidLetters.stream()
                    .anyMatch(word::contains);
        } else {
            return false;
        }
    }
}
