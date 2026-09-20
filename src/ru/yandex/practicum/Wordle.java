package ru.yandex.practicum;

import ru.yandex.practicum.controller.WordleController;
import ru.yandex.practicum.repository.WordleDictionary;
import ru.yandex.practicum.service.WordleDictionaryLoader;
import ru.yandex.practicum.service.WordleGame;
import ru.yandex.practicum.service.WordleGameImpl;

import java.io.InputStream;

/*
    создать лог-файл (он должен передаваться во все классы)
 */

public class Wordle {
    private static final String WORDS_PATH = "words_ru.txt";
    private static final InputStream INPUT_SOURCE = System.in;

    public static void main(String[] args) {
        try {
            WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader();
            WordleDictionary wordleDictionary = wordleDictionaryLoader.loadWordsFromFile(WORDS_PATH);
            WordleGame wordleGame = new WordleGameImpl(wordleDictionary);
            WordleController wordleController = new WordleController(INPUT_SOURCE, wordleGame);
            wordleController.startGame();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

}
