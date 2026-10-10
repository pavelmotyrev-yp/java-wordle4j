package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.repository.WordleDictionaryImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("WordleDictionaryImpl tests")
class WordleDictionaryImplTest {

    private WordleDictionaryImpl dictionary;

    @BeforeEach
    void setUp() {
        List<String> words = List.of(
                "абзац",    // 0
                "аборт",    // 1
                "абрек",    // 2
                "авгит",    // 3
                "автол",    // 4
                "автор",    // 5
                "агава"     // 6
        );
        dictionary = new WordleDictionaryImpl(words);
    }


    @Test
    @DisplayName("getDictionarySize() — возвращает корректный размер словаря")
    void getDictionarySize_returnsCorrectSize() {
        assertEquals(7, dictionary.getDictionarySize());
    }

    @Test
    @DisplayName("getDictionarySize — пустой словарь возвращает 0")
    void getDictionarySize_emptyDictionary_returnsZero() {
        WordleDictionaryImpl emptyDictionary = new WordleDictionaryImpl(List.of());
        assertEquals(0, emptyDictionary.getDictionarySize());
    }

    @Test
    @DisplayName("getWord(int wordPosition) — индекс 0 возвращает первое слово")
    void getWord_withValidIndexZero_returnsFirstWord() {
        Optional<String> result = dictionary.getWord(0);

        assertTrue(result.isPresent());
        assertEquals("абзац", result.get());
    }

    @Test
    @DisplayName("getWord(int wordPosition) — индекс середины возвращает корректное слово")
    void getWord_withValidMiddleIndex_returnsMiddleWord() {
        Optional<String> result = dictionary.getWord(3);

        assertTrue(result.isPresent());
        assertEquals("авгит", result.get());
    }

    @Test
    @DisplayName("getWord(int wordPosition) — индекс последнего элемента возвращает слово")
    void getWord_withValidLastIndex_returnsLastWord() {
        Optional<String> result = dictionary.getWord(6);

        assertTrue(result.isPresent());
        assertEquals("агава", result.get());
    }

    @Test
    @DisplayName("getWord() — без фильтров возвращает первое слово")
    void getWord_withNoFilters_returnsFirstWord() {
        Optional<String> result = dictionary.getWord(Set.of(), Set.of(), Map.of(), Set.of());

        assertTrue(result.isPresent());
        assertEquals("абзац", result.get());
    }

    @Test
    @DisplayName("getWord() — invalidLetters фильтрует слова")
    void getWord_withInvalidLettersFilter_excludesWordsWithLetters() {
        Set<String> invalidLetters = Set.of("б");

        Optional<String> result = dictionary.getWord(invalidLetters, Set.of(), Map.of(), Set.of());

        assertTrue(result.isPresent());
        assertFalse(result.get().contains("б"));
    }

    @Test
    @DisplayName("getWord() — validLetters требует все буквы")
    void getWord_withValidLettersFilter_requiresAllLetters() {
        Set<String> validLetters = Set.of("в", "т");

        Optional<String> result = dictionary.getWord(Set.of(), validLetters, Map.of(), Set.of());

        assertTrue(result.isPresent());
        String word = result.get();
        assertTrue(word.contains("в"));
        assertTrue(word.contains("т"));
    }

    @Test
    @DisplayName("getWord() — позиция буквы проверена")
    void getWord_withValidLettersPositionFilter_requiresPosition() {
        Map<Integer, String> position = Map.of(1, "г");

        Optional<String> result = dictionary.getWord(Set.of(), Set.of(), position, Set.of());

        assertTrue(result.isPresent());
        assertEquals("г", result.get().substring(1, 2));
    }

    @Test
    @DisplayName("getWord() — invalidWords исключает слова")
    void getWord_withInvalidWordsFilter_excludesWords() {
        Set<String> invalidWords = Set.of("абзац", "аборт", "абрек", "авгит", "автол", "автор", "агава");

        Optional<String> result = dictionary.getWord(Set.of(), Set.of(), Map.of(), invalidWords);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("getWord() — пустой словарь возвращает Optional.empty()")
    void getWord_withEmptyDictionary_returnsEmpty() {
        WordleDictionaryImpl emptyDictionary = new WordleDictionaryImpl(List.of());

        Optional<String> result = emptyDictionary.getWord(Set.of(), Set.of(), Map.of(), Set.of());

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("isExist(String userAnswer) — слово в словаре возвращает true")
    void isExist_wordInDictionary_returnsTrue() {
        assertTrue(dictionary.isExist("абзац"));
    }

    @Test
    @DisplayName("isExist(String userAnswer) — слово не в словаре возвращает false")
    void isExist_wordNotInDictionary_returnsFalse() {
        assertFalse(dictionary.isExist("несуществующее"));
    }

    @Test
    @DisplayName("isExist(String userAnswer) — пустая строка возвращает false")
    void isExist_emptyString_returnsFalse() {
        assertFalse(dictionary.isExist(""));
    }

    @Test
    @DisplayName("isExist(String userAnswer) — слово с другим регистром возвращает false")
    void isExist_differentCase_returnsFalse() {
        assertFalse(dictionary.isExist("Абзац"));
    }
}