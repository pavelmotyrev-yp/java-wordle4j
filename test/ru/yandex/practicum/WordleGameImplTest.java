package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.entity.UserAnswerResult;
import ru.yandex.practicum.exception.IncorrectUserAnswerException;
import ru.yandex.practicum.logger.Logger;
import ru.yandex.practicum.repository.WordleDictionary;
import ru.yandex.practicum.repository.WordleDictionaryImpl;
import ru.yandex.practicum.service.WordleGame;
import ru.yandex.practicum.service.WordleGameImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("WordleGameImpl tests")
class WordleGameImplTest {

    private WordleGame wordleGame;
    private Logger logger;
    private List<String> words;

    @BeforeEach
    void setUp() {
        logger = new Logger();
        words = List.of("абзац", "аборт", "абрек", "авгит", "автол");
        WordleDictionary dictionary = new WordleDictionaryImpl(words);
        wordleGame = new WordleGameImpl(dictionary, logger);
    }

    @Test
    @DisplayName("initialize() — после инициализации secretWord не null и из словаря")
    void initialize_secretWordIsNotNullAndFromDictionary() {
        wordleGame.initialize();

        String secretWord = wordleGame.getSecretWord();
        assertNotNull(secretWord);
        assertEquals(5, secretWord.length());
    }

    @Test
    @DisplayName("initialize() — пустой словарь выбрасывает RuntimeException")
    void initialize_emptyDictionaryThrowsRuntimeException() {
        WordleDictionary emptyDictionary = new WordleDictionaryImpl(List.of());
        WordleGame game = new WordleGameImpl(emptyDictionary, logger);

        assertThrows(RuntimeException.class, game::initialize);
    }

    @Test
    @DisplayName("getHint() — возвращает подсказку")
    void getHint_returnsHintAfterWrongAnswers() {
        wordleGame.initialize();

        wordleGame.checkUserAnswer("абзац");
        wordleGame.checkUserAnswer("аборт");

        String hint = wordleGame.getHint();
        assertNotNull(hint);
        assertEquals(5, hint.length());
    }

    @Test
    @DisplayName("checkUserAnswer() — правильный ответ содержит secretWord в feedbackString")
    void checkUserAnswer_correctAnswerContainsSecretWord() {
        wordleGame.initialize();
        String secretWord = wordleGame.getSecretWord();

        UserAnswerResult result = wordleGame.checkUserAnswer(secretWord);

        assertTrue(result.isAnswerCorrect());
        assertEquals(secretWord, result.feedbackString());
    }

    @Test
    @DisplayName("checkUserAnswer() — неверное слово возвращает isAnswerCorrect = false и строку с +, ^, -")
    void checkUserAnswer_wrongWordReturnsFeedbackWithSymbols() {
        wordleGame.initialize();
        String secretWord = wordleGame.getSecretWord();

        String anotherWord = words.getFirst().equals(secretWord) ? words.getLast() : words.getFirst();

        UserAnswerResult result = wordleGame.checkUserAnswer(anotherWord);

        assertFalse(result.isAnswerCorrect());
        String feedback = result.feedbackString();
        assertNotNull(feedback);
        assertTrue(feedback.matches("[+\\^-]+"));
    }

    @Test
    @DisplayName("checkUserAnswer() — слово не в словаре выбрасывает IncorrectUserAnswerException")
    void checkUserAnswer_wordNotInDictionaryThrowsException() {
        wordleGame.initialize();

        assertThrows(IncorrectUserAnswerException.class, () -> wordleGame.checkUserAnswer("несущ"));
    }

    @Test
    @DisplayName("checkUserAnswer() — пустая строка выбрасывает IncorrectUserAnswerException")
    void checkUserAnswer_emptyStringThrowsException() {
        wordleGame.initialize();

        assertThrows(IncorrectUserAnswerException.class, () -> wordleGame.checkUserAnswer(""));
    }

    @Test
    @DisplayName("checkUserAnswer() — слово слишком короткое выбрасывает IncorrectUserAnswerException")
    void checkUserAnswer_tooShortWordThrowsException() {
        wordleGame.initialize();

        assertThrows(IncorrectUserAnswerException.class, () -> wordleGame.checkUserAnswer("дом"));
    }

    @Test
    @DisplayName("checkUserAnswer() — слово слишком длинное выбрасывает IncorrectUserAnswerException")
    void checkUserAnswer_tooLongWordThrowsException() {
        wordleGame.initialize();

        assertThrows(IncorrectUserAnswerException.class, () -> wordleGame.checkUserAnswer("перечитать"));
    }

    @Test
    @DisplayName("getSecretWord() — до initialize() возвращает null")
    void getSecretWord_beforeInitializeReturnsNull() {
        assertNull(wordleGame.getSecretWord());
    }

    @Test
    @DisplayName("getSecretWord() — после initialize() возвращает непустую строку")
    void getSecretWord_afterInitializeReturnsNonEmpty() {
        wordleGame.initialize();

        String secretWord = wordleGame.getSecretWord();
        assertNotNull(secretWord);
        assertFalse(secretWord.isEmpty());
    }
}
