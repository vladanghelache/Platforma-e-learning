package repositories;

import config.DBConfiguration;
import models.*;

import java.sql.*;
import java.util.*;

public class Quizzes {

    public void insert(Quiz quiz) {
        String insertQuizSql = "insert into quizzes (quizName,nrQuestions,totalPoints,teacherId,courseId,autoscored) values (?,?,?,?,?,?)";
        Connection connection = DBConfiguration.getDbConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuizSql);

            preparedStatement.setString(1, quiz.getQuizName());
            preparedStatement.setInt(2, quiz.getNrQuestions());
            preparedStatement.setInt(3, quiz.getTotalPoints());
            preparedStatement.setInt(4, quiz.getTeacher().getId());
            preparedStatement.setInt(5, quiz.getCourse().getId());
            if(quiz instanceof AutoScored){
                preparedStatement.setBoolean(6, true);
            }else{
                preparedStatement.setBoolean(6, false);
            }

            preparedStatement.execute();
            PreparedStatement preparedStatement2 = connection.prepareStatement("select last_insert_id()");
            ResultSet resultSet = preparedStatement2.executeQuery();
            resultSet.next();
            quiz.setId(resultSet.getInt(1));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Optional<Quiz> getById(int id){
        String selectSql = "select * from quizzes where Id = ?";
        Connection connection = DBConfiguration.getDbConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToQuiz(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    public void update(Quiz quiz){

        String updateSql = "UPDATE quizzes " +
                                "SET quizName = ?, " +
                                " nrQuestions = ?, " +
                                " totalPoints = ? " +
                                "WHERE Id=?";

        Connection connection = DBConfiguration.getDbConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setString(1,quiz.getQuizName());
            preparedStatement.setInt(2,quiz.getNrQuestions());
            preparedStatement.setInt(3,quiz.getTotalPoints());
            preparedStatement.setInt(4,quiz.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Question> getQuestions(int id){

        List<Question> questions = new ArrayList<>();
        Connection connection = DBConfiguration.getDbConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from mcqs where quizId = ?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            MCQs mcqs = new MCQs();
            while(true){
                Optional<MCQ> question = mcqs.mapToMcq(resultSet);
                question.ifPresent(questions::add);
                if(question.isEmpty()){
                    break;
                }
            }

            preparedStatement = connection.prepareStatement("select * from mps where quizId = ?");
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            MPs mps = new MPs();
            while(true){
                Optional<MP> question = mps.mapToMp(resultSet);
                question.ifPresent(questions::add);

                if(question.isEmpty()){
                    break;
                }
            }

            preparedStatement = connection.prepareStatement("select * from oeqs where quizId = ?");
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            OEQs oeqs = new OEQs();
            while(true){
                Optional<OEQ> question = oeqs.mapToOeq(resultSet);
                question.ifPresent(questions::add);

                if(question.isEmpty()){
                    break;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return questions;

    }

    public List<MCQ> getMCQs(int id){

        List<MCQ> MCQs = new ArrayList<>();
        Connection connection = DBConfiguration.getDbConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from mcqs where quizId = ?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            MCQs mcqs = new MCQs();
            while(true){
                Optional<MCQ> mcq = mcqs.mapToMcq(resultSet);
                mcq.ifPresent(MCQs::add);

                if(mcq.isEmpty()){
                    break;
                }
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return MCQs;

    }

    public Set<Answer> getAnswers(int id){

        Set<Answer> answers = new HashSet<>();
        Connection connection = DBConfiguration.getDbConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from answers where quizId = ?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Answers ans = new Answers();
            while(true){
                Optional<Answer> answer = ans.mapToAnswer(resultSet);
                answer.ifPresent(answers::add);

                if(answer.isEmpty()){
                    break;
                }
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return answers;

    }


    Optional<Quiz> mapToQuiz(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            int Id = resultSet.getInt(1);
            String quizName = resultSet.getString(2);
            int nrQuestions = resultSet.getInt(3);
            int totalPoints = resultSet.getInt(4);

            Teacher teacher = new Teachers().getById(resultSet.getInt(5)).get();
            Course course = new Courses().getById(resultSet.getInt(6)).get();

            boolean autoscored = resultSet.getBoolean(7);

            if (autoscored){

                Quiz quiz = new AutoScored(Id,quizName,nrQuestions,totalPoints,teacher,course);
                return Optional.of(quiz);
            }
            else{

                Quiz quiz = new NormalQuiz(Id,quizName,nrQuestions,totalPoints,teacher,course);
                return Optional.of(quiz);
            }

        }

        return Optional.empty();
    }
}
