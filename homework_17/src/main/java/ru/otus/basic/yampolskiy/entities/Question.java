package ru.otus.basic.yampolskiy.entities;

import java.util.List;

public record Question(int id, int subjectId, String title, List<Answer> answers) { }
