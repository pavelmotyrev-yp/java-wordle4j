package ru.yandex.practicum.service;

import ru.yandex.practicum.entity.UserAnswerResult;

/**
 * Интерфейс игры Wordle.
 *
 * <p>Определяет основные методы для управления игровым процессом:
 * инициализации, проверки ответов пользователя и получения подсказок.</p>
 */
public interface WordleGame {

    /**
     * Инициализирует новую игру: выбирает случайное слово из словаря.
     *
     * @throws RuntimeException, если не удалось выбрать слово
     */
    void initialize();

    /**
     * Возвращает подсказку — слово из словаря, соответствующее
     * накопленной информации о допустимых и недопустимых буквах.
     * Используется при пустом вводе пользователя.
     *
     * @return подсказка в виде строки
     * @throws RuntimeException, если подходящее слово не найдено
     */
    String getHint();

    /**
     * Проверяет ответ пользователя.
     *
     * @param userAnswer введённое пользователем слово
     * @return результат проверки, содержащий флаг успеха и строку обратной связи
     *         ({@code +} — буква на месте, {@code ^} — буква есть, {@code -} — буквы нет)
     * @throws ru.yandex.practicum.exception.IncorrectUserAnswerException,
     *         если слово не существует в словаре
     */
    UserAnswerResult checkUserAnswer(String userAnswer);

    /**
     * Возвращает загаданное слово.
     * Используется для отображения при завершении игры.
     *
     * @return загаданное слово
     */
    String getSecretWord();
}
