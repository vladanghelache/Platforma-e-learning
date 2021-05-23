package repositories;

import config.DBConfiguration;
import models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Answers {
    public void insert(Answer answer) {
        String insertAnswerSql = "insert into answers (studentId,answers,grade,quizId) values (?,?,?,?)";
        Connection connection = DBConfiguration.getDbConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertAnswerSql);

            preparedStatement.setInt(1, answer.getStudent().getId());
            StringBuilder str = new StringBuilder();
            for (String a :
                    answer.getAnswer()) {
                str.append(a);
                str.append("/");

            }
            preparedStatement.setString(2, str.toString());
            preparedStatement.setDouble(3, answer.getGrade());
            preparedStatement.setInt(4, answer.getQuiz().getId());


            preparedStatement.execute();
            PreparedStatement preparedStatement2 = connection.prepareStatement("select last_insert_id()");
            ResultSet resultSet = preparedStatement2.executeQuery();
            resultSet.next();
            answer.setId(resultSet.getInt(1));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Optional<Answer> getById(int id){
        String selectSql = "select * from answers where Id = ?";
        Connection connection = DBConfiguration.getDbConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToAnswer(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    public void updateGrade(int id, double grade){

        String updateSql = "UPDATE answers " +
                "SET grade = ? " +
                "WHERE Id=?";

        Connection connection = DBConfiguration.getDbConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setDouble(1,grade);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    Optional<Answer> mapToAnswer(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            int Id = resultSet.getInt(1);
            Student student = new Students().getById(resultSet.getInt(2)).get();
            String str = resultSet.getString(3);
            List<String> answer = Arrays.asList(str.split("/").clone());
            double grade = resultSet.getDouble(4);
            Quiz quiz = new Quizzes().getById(resultSet.getInt(5)).get();


            return Optional.of(new Answer(Id,student,answer,grade,quiz));

        }

        return Optional.empty();
    }
}
