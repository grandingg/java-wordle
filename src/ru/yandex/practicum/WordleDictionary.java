package ru.yandex.practicum;

import ru.yandex.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {
    private List<String> words;
    private static final int WORD_LENGTH = 5;

    public WordleDictionary(List<String> words) {
        this.words = new ArrayList<>(words);
    }

    public List<String> getWords() {
        return words;
    }

    public String compareLetter(String answer, String userGuess) throws WordLengthException {

        if (userGuess.length() != WORD_LENGTH) {
            throw new WordLengthException("Слово должно состоять из 5 букв", userGuess.length());
        }

        StringBuilder sb = new StringBuilder("-----");
        Map<Character, Integer> letterCount = new HashMap<>();

        for (char c : answer.toCharArray()) {
            letterCount.put(c, letterCount.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < WORD_LENGTH; i++) {
            char guessChar = userGuess.charAt(i);
            if (guessChar == answer.charAt(i)) {
                sb.setCharAt(i, '+');
                letterCount.put(guessChar, letterCount.get(guessChar) - 1);
            }
        }

        for (int i = 0; i < WORD_LENGTH; i++) {

            if (sb.charAt(i) == '+') {
                continue;
            }

            char guessChar = userGuess.charAt(i);

            if (letterCount.containsKey(guessChar) && letterCount.get(guessChar) > 0) {
                sb.setCharAt(i, '^');
                letterCount.put(guessChar, letterCount.get(guessChar) - 1);
            } else {
                sb.setCharAt(i, '-');
            }
        }
        return sb.toString();

    }
}
