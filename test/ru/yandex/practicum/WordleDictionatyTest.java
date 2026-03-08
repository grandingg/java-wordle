package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.exceptions.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class WordleDictionatyTest {

    @Test
    public void ShouldBeCompleteMatch() throws WordLengthException {
        WordleDictionary wd = new WordleDictionary(new ArrayList<>());

        String result = wd.compareLetter("кошка", "кошка");
        assertEquals("+++++", result);
    }

    @Test
    public void ShouldBeCompleteMismatch() throws WordLengthException {
        WordleDictionary wd = new WordleDictionary(new ArrayList<>());

        String result = wd.compareLetter("кошка", "лимит");
        assertEquals("-----", result);
    }

    @Test
    public void ShouldBePartialMatch() throws WordLengthException {
        WordleDictionary wd = new WordleDictionary(new ArrayList<>());

        String result = wd.compareLetter("кошка", "комар");
        assertEquals("++-^-", result);
    }

    @Test
    public void CheckingForDuplicateLetters() throws WordLengthException {
        WordleDictionary wd = new WordleDictionary(new ArrayList<>());

        String result = wd.compareLetter("кошка", "кокос");
        assertEquals("++^--", result);
    }

    @Test
    public void IncorrectWordLength() throws WordLengthException {
        WordleDictionary wd = new WordleDictionary(new ArrayList<>());

        WordLengthException e = assertThrows(WordLengthException.class,
                () -> wd.compareLetter("кошка", "кот"));

        assertEquals("Слово должно состоять из 5 букв", e.getMessage());

    }

}
