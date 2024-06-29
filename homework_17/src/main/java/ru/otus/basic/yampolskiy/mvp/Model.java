package ru.otus.basic.yampolskiy.mvp;

import ru.otus.basic.yampolskiy.entities.Question;
import ru.otus.basic.yampolskiy.entities.Subject;
import ru.otus.basic.yampolskiy.services.GameService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Model {

    private final GameService gameService = new GameService();
    private final Set<String> questionAlreadyAsked = new HashSet<>();

    public List<Subject> getSubjects(){
        return gameService.getAll();
    }

    public Question getQuestion(int subjectId){
        int requests = 100;
        while (requests > 0) {
            Question question = gameService.getNewQuestion(subjectId);
            if (question!= null && !questionAlreadyAsked.contains(question.title())) {
                questionAlreadyAsked.add(question.title());
                return question;
            }
            requests--;
        }
        return null;
    }


}
