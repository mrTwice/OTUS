package ru.otus.basic.yampolskiy.repositories;

import ru.otus.basic.yampolskiy.entities.Answer;
import ru.otus.basic.yampolskiy.entities.Question;
import ru.otus.basic.yampolskiy.entities.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameRepository {
    private final DataBaseManager dataBaseManager = DataBaseManager.getDataBaseManager();


    public List<Subject> getSubjects() {
        List<Subject> subjects = new ArrayList<>();
        try (Connection connection = dataBaseManager.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM subjects");
            while (resultSet.next()) {
                Subject subject = new Subject(resultSet.getInt("id"), resultSet.getString("title"));
                subjects.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    public Question getRandomQuestion(int subjectId) {
        Question question = null;
        try (Connection connection = dataBaseManager.getConnection()){
            PreparedStatement questionPreparedStatement = connection.prepareStatement("SELECT * FROM questions WHERE subject_id = ? ORDER BY RANDOM() LIMIT 1");
            questionPreparedStatement.setInt(1, subjectId);
            ResultSet questionResultSet = questionPreparedStatement.executeQuery();

            int questionId;
            String questionTitle;
            if (questionResultSet.next()) {
                questionId = questionResultSet.getInt("id");
                questionTitle = questionResultSet.getString("title");
                PreparedStatement answersPreparedStatement = connection.prepareStatement("SELECT * FROM answers WHERE question_id = ?");
                answersPreparedStatement.setInt(1, questionId);
                ResultSet answerResultSet = answersPreparedStatement.executeQuery();
                List<Answer> answers = new ArrayList<>();
                while (answerResultSet.next()) {
                    Answer answer = new Answer(answerResultSet.getInt("id"), questionId, answerResultSet.getString("answer"), answerResultSet.getBoolean("is_correct"));
                    answers.add(answer);
                }
                return new Question(questionId, subjectId, questionTitle, answers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
