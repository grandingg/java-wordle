package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.exceptions.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WordleGameTest {

    private WordleDictionary createFakeDictionary() {
        List<String> words = new ArrayList<>();
        words.add("кошка");
        words.add("лимит");
        return new WordleDictionary(words);
    }

    @Test
    public void ShouldBeVictoryAndPositive()
            throws WordLengthException, WordNotInDictionaryException, FinishedGameException {

        WordleGame game = new WordleGame("кошка", 6, createFakeDictionary());

        game.compareWords("кошка");

        assertTrue(game.isWon());
        assertTrue(game.isFinished());
    }

    @Test
    public void ShouldDecreaseStepsWhenWrongGuess()
            throws WordLengthException, WordNotInDictionaryException, FinishedGameException {

        WordleGame game = new WordleGame("кошка", 6, createFakeDictionary());

        game.compareWords("лимит");

        assertFalse(game.isWon());
        assertFalse(game.isFinished());
        assertEquals(5, game.getSteps());
    }

    @Test
    public void ShouldLoseWhenStepsAreZero()
            throws WordLengthException, WordNotInDictionaryException, FinishedGameException {

        WordleGame game = new WordleGame("кошка", 1, createFakeDictionary());

        game.compareWords("лимит");

        assertTrue(game.isFinished());
        assertFalse(game.isWon());
        assertEquals(0, game.getSteps());
    }

    @Test
    public void shouldThrowExceptionIfGameAlreadyFinished()
            throws WordLengthException, WordNotInDictionaryException, FinishedGameException {

        WordleGame game = new WordleGame("кошка", 1, createFakeDictionary());

        game.compareWords("кошка");

        assertThrows(FinishedGameException.class,
                () -> game.compareWords("лимит"));
    }
}
