package ru.otus.basic.yampolskiy.mvp;

import ru.otus.basic.yampolskiy.entities.Question;
import ru.otus.basic.yampolskiy.entities.Subject;

import java.util.List;

public class Presenter {
    private View view;
    private Model model;
    private int attempt = 3;

    public Presenter() {
        this.view = new View();
        this.model = new Model();
    }

    public void start() {
        List<Subject> subjects = model.getSubjects();

        while (attempt > 0) {
            view.showMainMenu();
            if (view.getUserChoice("Выберете пункт меню: ") == 1) {
                int userChoice = view.showSubject(subjects, "Выберете тему для вопроса: ");
                Subject subject = subjects.get(userChoice - 1);
                boolean isAbsent = false;
                while (!isAbsent && attempt > 0) {
                    Question question = model.getQuestion(subject.id());
                    if(question != null) {

                        int answer = view.showQuestion(question, "Выберете ответ: ");
                        if (!question.answers().get(answer - 1).isCorrect()) {
                            attempt--;
                            System.out.println("Попыток осталось: " + attempt);
                        } else {
                            System.out.println("Верно");
                        }
                    } else {
                        isAbsent = true;
                        showMessage("Вопросы по теме закончились.");
                    }
                }
            } else {
                System.out.println("Такого пункта нет.");
            }
        }
        showMessage("Вы исчерпали все попытки");
    }

    private void showMessage(String message){
        System.out.println(message);
    }
}
