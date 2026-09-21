package ru.yandex.practicum.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordleDictionaryImpl implements WordleDictionary{

    private List<String> words;

    public WordleDictionaryImpl(List<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        return new ArrayList<>(words);
    }

    @Override
    public int getDictionarySize() {
        return 0;
    }

    @Override
    public String getWord(int wordPosition) {
        return "";
    }

    @Override
    public String getWord(Set<String> invalidLetters, Set<String> validLetters, Map<Integer, String> validLettersPosition) {
        return "";
    }

    @Override
    public boolean isExist(String userAnswer) {
        return false;
    }
}
