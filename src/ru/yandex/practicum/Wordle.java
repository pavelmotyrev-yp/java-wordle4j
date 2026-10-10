package ru.yandex.practicum;

import ru.yandex.practicum.controller.WordleController;
import ru.yandex.practicum.fileprocessor.WordleDictionaryLoader;
import ru.yandex.practicum.logger.Logger;
import ru.yandex.practicum.repository.WordleDictionaryImpl;
import ru.yandex.practicum.service.WordleGame;
import ru.yandex.practicum.service.WordleGameImpl;

import java.io.InputStream;
import java.util.Arrays;

public class Wordle {
    private static final String WORDS_PATH = "words_ru.txt";
    private static final InputStream INPUT_SOURCE = System.in;

    public static void main(String[] args) {
        Logger logger = new Logger();
        try {
            WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader(logger);
            WordleDictionaryImpl wordleDictionary = wordleDictionaryLoader.loadWordsFromFile(WORDS_PATH);
            WordleGame wordleGame = new WordleGameImpl(wordleDictionary, logger);
            WordleController wordleController = new WordleController(INPUT_SOURCE, wordleGame, logger);
            wordleController.startGame();
        } catch (RuntimeException e) {
            logger.log(e.getMessage() + Arrays.toString(e.getStackTrace()));
            System.out.println(e.getMessage());
        }
    }

}
