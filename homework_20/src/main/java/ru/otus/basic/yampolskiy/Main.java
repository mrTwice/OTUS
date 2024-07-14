package ru.otus.basic.yampolskiy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);


    public static void main(String[] args) {
        boolean fileFound = false;
        while (!fileFound) {
            String path = getInputFromUser("Введите путь до файла: ");
            String phrase = getInputFromUser("Введите строку для поиска: ");
            try {
                countIncomesInFile(path, phrase);
                fileFound = true;
            } catch (IOException e) {
                System.out.println("Указанный файл не существует");
            }
        }
    }

    private static String getInputFromUser(String message){
        System.out.print(message);
        return SCANNER.nextLine();
    }

    private static void countIncomesInFile(String path, String phrase) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
            String line;
            int count = 0;
            while ((line = bufferedReader.readLine()) != null) {
                count += countIncomesInString(phrase, line);
            }
            System.out.printf("Строка %s встречается %d раз", phrase, count);
        }
    }

    private static int countIncomesInString(String phrase, String text) {
        int count = 0;
        int index = 0;

        while ((index = text.indexOf(phrase, index)) != -1) {
            count++;
            index += phrase.length();
        }

        return count;
    }
}