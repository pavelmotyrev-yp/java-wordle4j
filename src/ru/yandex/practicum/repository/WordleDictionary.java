package ru.yandex.practicum.repository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Интерфейс словаря слов для игры Wordle.
 *
 * <p>Обеспечивает доступ к списку слов, поиск по индексу,
 * фильтрацию по буквам и позициям, а также проверку наличия слова.</p>
 */
public interface WordleDictionary {

    /**
     * Возвращает количество слов в словаре.
     *
     * @return размер словаря
     */
    int getDictionarySize();

    /**
     * Возвращает слово по его индексу в словаре.
     *
     * @param wordPosition индекс слова
     * @return слово по указанному индексу
     */
    Optional<String> getWord(int wordPosition);

    /**
     * Возвращает слово, соответствующее набору фильтров, накопленных
     * в процессе игры: исключённые буквы, допустимые буквы, буквы
     * на конкретных позициях и уже отфильтрованные слова.
     *
     * @param invalidLetters        буквы, которые не должны встречаться
     * @param validLetters          буквы, которые обязательно должны быть
     * @param validLettersPosition  карта «позиция → буква» для точного совпадения
     * @param invalidWords          слова, которые уже были проверены
     * @return первое подходящее слово, либо {@code Optional.empty()},
     *         если таких слов нет
     */
    Optional<String> getWord(Set<String> invalidLetters, Set<String> validLetters,
                             Map<Integer, String> validLettersPosition, Set<String> invalidWords);

    /**
     * Проверяет, присутствует ли слово в словаре.
     *
     * @param userAnswer проверяемое слово
     * @return {@code true}, если слово есть в словаре
     */
    boolean isExist(String userAnswer);
}
