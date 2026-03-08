package ru.yandex.practicum;

import ru.yandex.exceptions.FinishedGameException;
import ru.yandex.exceptions.WordLengthException;
import ru.yandex.exceptions.WordNotInDictionaryException;
import ru.yandex.exceptions.ZeroHintsException;

import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {
    private String answer;
    private int steps;
    private WordleDictionary dictionary;
    private boolean isFinished = false;
    private boolean isWon = false;
    private final LinkedHashMap<String, String> history = new LinkedHashMap<>();

    public WordleGame(String answer, int steps, WordleDictionary dictionary) {
        this.answer = answer;
        this.steps = steps;
        this.dictionary = dictionary;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public boolean isWon() {
        return isWon;
    }

    public int getSteps() {
        return steps;
    }

    public String compareWords(String userGuess) throws FinishedGameException, WordLengthException, WordNotInDictionaryException {
        if (isFinished) {
            throw new FinishedGameException("Игра уже завершена");
        }

        if (userGuess.length() != 5) {
            throw new WordLengthException("Слово должно состоять из 5 букв", userGuess.length());
        }

        if (!dictionary.getWords().contains(userGuess)) {
            throw new WordNotInDictionaryException("Слова не в словаре");
        }

        String hint = dictionary.compareLetter(answer, userGuess);
        history.put(userGuess, hint);

        if (userGuess.equals(answer)) {
            isWon = true;
            isFinished = true;
        } else {
            steps--;
            if (steps == 0) {
                isFinished = true;
            }
        }
        return hint;
    }

    public String getHintWords() throws WordLengthException, ZeroHintsException {
        List<String> hintWords = new ArrayList<>(dictionary.getWords());

        hintWords.removeAll(history.keySet());

        for (Map.Entry<String, String> entry : history.entrySet()) {
            String guess = entry.getKey();
            String hint = entry.getValue();


            Iterator<String> iterator = hintWords.iterator();

            while (iterator.hasNext()) {
                String word = iterator.next();
                String result = dictionary.compareLetter(word, guess);

                if (!result.equals(hint)) {
                    iterator.remove();
                }
            }
        }

        if (hintWords.isEmpty()) {
            throw new ZeroHintsException("Нет подходящих слов");
        }

        Collections.shuffle(hintWords);
        return hintWords.get(0);

    }

}
