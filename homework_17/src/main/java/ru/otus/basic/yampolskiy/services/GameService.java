package ru.otus.basic.yampolskiy.services;

import ru.otus.basic.yampolskiy.entities.Question;
import ru.otus.basic.yampolskiy.entities.Subject;
import ru.otus.basic.yampolskiy.repositories.GameRepository;

import java.util.List;

public class GameService {

    private final GameRepository gameRepository = new GameRepository();

    public List<Subject> getAll() {
        return gameRepository.getSubjects();
    }

    public Question getNewQuestion(int subjectId) {
        return gameRepository.getRandomQuestion(subjectId);
    }
}
