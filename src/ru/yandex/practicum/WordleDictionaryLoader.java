package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    public WordleDictionary load() throws IOException {
        List<String> words = new ArrayList<>();

        try (FileReader reader = new FileReader("words_ru.txt", StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(reader)) {

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim().toLowerCase().replace("ё", "е");

                if (line.length() == 5) {
                    words.add(line);
                }

            }
        }
        return new WordleDictionary(words);
    }
}
