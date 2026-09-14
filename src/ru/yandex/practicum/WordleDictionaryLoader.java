package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class WordleDictionaryLoader {
    private static final int MAX_WORD_LENGTH = 5;

    public WordleDictionary loadWordsFromFile(String filePath) {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            while (reader.ready()) {
                String newWord = reader.readLine().trim();
                if (newWord.length() == MAX_WORD_LENGTH) {
                    words.add(normalizeWord(newWord));
                }
            }
        } catch (IOException e) {
            //ignore
        }
        return new WordleDictionary(words);
    }

    private String normalizeWord(String word) {
        return word.toLowerCase().replaceAll("ё", "е");
    }
}
