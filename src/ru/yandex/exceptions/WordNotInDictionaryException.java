package ru.yandex.exceptions;

public class WordNotInDictionaryException extends Exception {

    public WordNotInDictionaryException(String message) {
        super(message);
    }
}
