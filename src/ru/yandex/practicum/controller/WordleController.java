package ru.yandex.practicum.controller;

import ru.yandex.practicum.entity.UserAnswerResult;
import ru.yandex.practicum.exception.IncorrectUserAnswerException;
import ru.yandex.practicum.logger.Logger;
import ru.yandex.practicum.service.WordleGame;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class WordleController {
    private static final int ATTEMPTS_LIMIT = 6;
    private static final String START_MENU = """
            Вас приветствует игра WORDLE!
            Попробуйте угадать какое слово загадал компьютер.
            Слово существительное в инфинитиве из 5 букв.
            """;
    private static final String CONGRATULATION_MESSAGE = "Поздравляю, Вы выиграли игру! Вы угадали слово";
    private static final String GAME_OVER_MESSAGE = "Сожалеем, но Вы не угадали слово %s. Попробуйте еще!";

    private final Scanner scanner;
    private final WordleGame wordleGame;
    private final Logger logger;
    private boolean isGameWin;

    public WordleController(InputStream inputSource, WordleGame wordleGame, Logger logger) {
        this.scanner = new Scanner(inputSource);
        this.wordleGame = wordleGame;
        this.logger = logger;
    }

    public void startGame() {
        wordleGame.initialize();
        System.out.println(START_MENU);
        processGame();

        if (isGameWin) {
            System.out.printf(CONGRATULATION_MESSAGE);
        } else {
            System.out.printf(GAME_OVER_MESSAGE, wordleGame.getSecretWord());
        }
    }

    private void processGame() {
        for (int i = 0; i < ATTEMPTS_LIMIT; i++) {
            if (!isGameWin) {
                try {
                    runGameLoop();
                } catch (IncorrectUserAnswerException e) {
                    i--;
                    logger.log(e.getMessage() + " неверный ввод. пользователя" + Arrays.toString(e.getStackTrace()));
                    System.out.println(e.getMessage());
                }
            } else {
                break;
            }
        }
    }

    private void runGameLoop() {
        String userAnswer = scanner.nextLine();
        String normalizeAnswer = userAnswer.toLowerCase().trim();
        if (normalizeAnswer.length() != 5 && !normalizeAnswer.isEmpty()) {
            throw new IncorrectUserAnswerException(normalizeAnswer + " неверный ввод. Длина слова должна быть 5 " +
                    "символов или ввод должен быть пустым для вывода подсказки программы");
        }
        if (normalizeAnswer.isEmpty()) {
            normalizeAnswer = wordleGame.getHint();
            System.out.println(normalizeAnswer);
        }
        UserAnswerResult result = wordleGame.checkUserAnswer(normalizeAnswer);
        if (result.isAnswerCorrect()) {
            isGameWin = true;
        } else {
            System.out.println(result.feedbackString());
        }
    }
}
