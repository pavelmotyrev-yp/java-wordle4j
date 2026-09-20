package ru.yandex.practicum.repository;

import java.util.ArrayList;
import java.util.List;

public class WordleDictionary {

    private List<String> words;

    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        return new ArrayList<>(words);
    }
}
