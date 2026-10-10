package ru.yandex.practicum.entity;

/**
 * Результат проверки ответа пользователя в игре Wordle.
 *
 * <p>Содержит флаг успешности угадывания и строку обратной связи:
 * {@code +} — буква на правильном месте,
 * {@code ^} — буква есть в слове, но не на той позиции,
 * {@code -} — буквы нет в слове.</p>
 */
public record UserAnswerResult(boolean isAnswerCorrect, String feedbackString) {
}
