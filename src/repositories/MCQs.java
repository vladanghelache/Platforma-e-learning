package repositories;

import config.DBConfiguration;
import models.MCQ;
import models.MP;
import models.Quiz;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MCQs {
    public void insert(MCQ mcq) {
        String insertMcqSql = "call insert_mcq(?,?,?,?,?,?,?)";
        Connection connection = DBConfiguration.getDbConnection();

        try {
            CallableStatement callableStatement = connection.prepareCall(insertMcqSql);

            callableStatement.setString(1, mcq.getQuestion());
            callableStatement.setInt(2, mcq.getPoints());
            callableStatement.setInt(3, mcq.getNrChoices());
            StringBuilder str1 = new StringBuilder();
            for (Map.Entry<Character,String> choice : mcq.getChoices().entrySet()) {
                str1.append(choice.getKey());
                str1.append("/");
                str1.append(choice.getValue());
                str1.append("~");
            }
            callableStatement.setString(4, str1.toString());
            callableStatement.setString(5, mcq.getCorrectAnswer().toString());
            callableStatement.setInt(6, mcq.getQuiz().getId());


            callableStatement.registerOutParameter(7, Types.INTEGER);
            callableStatement.execute();
            mcq.setId(callableStatement.getInt(7));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Optional<MCQ> getById(int id){
        String selectSql = "select * from mcqs where Id = ?";
        Connection connection = DBConfiguration.getDbConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToMcq(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    public void delete(int id){
        String selectSql = "delete from mcqs where Id = ?";
        Connection connection = DBConfiguration.getDbConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1,id);
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    Optional<MCQ> mapToMcq(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            int Id = resultSet.getInt(1);
            String question = resultSet.getString(2);
            int points = resultSet.getInt(3);
            int nrChoices = resultSet.getInt(4);
            String A = resultSet.getString(5);
            String correctAnswer = resultSet.getString(6);
            Quiz quiz = new Quizzes().getById(resultSet.getInt(7)).get();

            Map<Character, String> choices = new HashMap<>();

            for (String grade : A.split("~")){

                String[] gr = grade.split("/");

                choices.put(gr[0].toCharArray()[0], gr[1]);

            }

            return Optional.of(new MCQ(Id,question,points,quiz,nrChoices,choices,correctAnswer.charAt(0)));
        }

        return Optional.empty();
    }
}
