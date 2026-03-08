package ru.yandex.exceptions;

public class WordLengthException extends Exception {
    private final int wordLength;

    public WordLengthException(final String message, final int wordLength) {
        super(message);
        this.wordLength = wordLength;
    }

    public int getWordLength() {
        return wordLength;
    }
}
