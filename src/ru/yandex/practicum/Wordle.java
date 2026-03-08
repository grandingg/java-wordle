package ru.yandex.practicum;

import ru.yandex.exceptions.WordLengthException;
import ru.yandex.exceptions.WordNotInDictionaryException;
import ru.yandex.exceptions.ZeroHintsException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {


    public static void main(String[] args) {

        try (PrintWriter log = new PrintWriter(new FileWriter("wordle.log", true))) {

            GameLogger logger = new GameLogger(log);
            Scanner scanner = new Scanner(System.in);

            System.out.println("Добро пожаловать в Wordle!");

            while (true) {

                try {
                    WordleDictionaryLoader loader = new WordleDictionaryLoader();
                    WordleDictionary dictionary = loader.load();

                    if (dictionary.getWords().isEmpty()) {
                        throw new RuntimeException("Словарь пуст");
                    }

                    List<String> words = new ArrayList<>(dictionary.getWords());
                    Collections.shuffle(words);
                    String answer = words.get(0);

                    WordleGame game = new WordleGame(answer, 6, dictionary);

                    boolean hintUnlocked = false;
                    System.out.println();
                    System.out.println("Было загадано слово из 5 букв.");
                    System.out.println("У вас 6 попыток.");
                    System.out.println();

                    while (!game.isFinished()) {
                        System.out.println("Введите слово:");
                        String guess = scanner.nextLine()
                                .trim().toLowerCase().replace("ё", "е");

                        if (guess.isEmpty()) {
                            if (!hintUnlocked) {
                                System.out.println("Подсказки будут доступны после первой попытки");
                                continue;
                            }
                            try {
                                String hintWord = game.getHintWords();
                                System.out.println("Слово-подсказка: " + hintWord);
                            } catch (ZeroHintsException e) {
                                System.out.println(e.getMessage());
                            }
                            continue;
                        }
                        try {
                            String hintLetters = game.compareWords(guess);

                            System.out.println(hintLetters);
                            System.out.println("Осталось попыток: " + game.getSteps());
                            System.out.println();

                            if (!hintUnlocked) {
                                hintUnlocked = true;
                                System.out.println("Нажмите Enter, чтобы получить слово-подсказку");
                                System.out.println();
                            }

                        } catch (WordLengthException | WordNotInDictionaryException e) {
                            System.out.println(e.getMessage());
                        } catch (Exception e) {
                            logger.logError(e);
                        }
                    }

                    if (game.isWon()) {
                        System.out.println("Вы выиграли! Поздравляем!");
                    } else {
                        System.out.println("К сожалению, вы проиграли.");
                        System.out.println("Загаданное слово " + answer);
                    }

                    System.out.println();
                    System.out.println("Хотите сыграть ещё раз?");
                    System.out.println("Введите 'выход' для выхода или любое другое слово для новой игры.");

                    String decision = scanner.nextLine()
                            .trim().toLowerCase();

                    if (decision.equals("выход")) {
                        System.out.println("Спасибо за игру!");
                        break;
                    }

                } catch (Exception e) {
                    logger.logError(e);
                }
            }

        } catch (IOException e) {
            System.out.println("Всё очень плохо!");
        }

    }

}
