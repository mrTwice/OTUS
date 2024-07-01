package ru.otus.basic.yampolskiy.entities;

public record Answer(int id, int questionId, String answer, boolean isCorrect) {
}
