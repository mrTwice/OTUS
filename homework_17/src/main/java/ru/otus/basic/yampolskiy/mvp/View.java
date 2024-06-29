package ru.otus.basic.yampolskiy.mvp;

import ru.otus.basic.yampolskiy.entities.Question;
import ru.otus.basic.yampolskiy.entities.Subject;

import java.util.List;
import java.util.Scanner;

public class View {

    private final Scanner input = new Scanner(System.in);

    public void showMainMenu() {
        System.out.println("""
                1. Играть
                2. Выход
                \s"""
        );
    }

    public int showSubject(List<Subject> subjects, String message) {
        for (Subject subject: subjects) {
            System.out.printf("[%d]. %s\n", subject.id(), subject.title());
        }
        System.out.print(message);
        return input.nextInt();
    }

    public int getUserChoice(String message) {
        System.out.print(message);
        return input.nextInt();
    }

    public String getUserInput(String message) {
        System.out.print(message);
        return input.next();
    }

    public int showQuestion(Question question, String message) {
        System.out.println(question.title());
        for (int i =0; i < question.answers().size(); i++){
            System.out.printf("[%d]. %s\n", i + 1, question.answers().get(i).answer());
        }
        System.out.print(message);
        return input.nextInt();
    }
}
