package repositories;

import config.DBConfiguration;
import models.OEQ;
import models.Quiz;
import models.Student;

import java.sql.*;
import java.util.Optional;

public class OEQs {
    public void insert(OEQ oeq) {
        String insertOeqSql = "call insert_oeq(?,?,?,?,?)";
        Connection connection = DBConfiguration.getDbConnection();

        try {
            CallableStatement callableStatement = connection.prepareCall(insertOeqSql);

            callableStatement.setString(1, oeq.getQuestion());
            callableStatement.setInt(2, oeq.getPoints());
            callableStatement.setString(3, oeq.getTips());
            callableStatement.setInt(4, oeq.getQuiz().getId());


            callableStatement.registerOutParameter(5, Types.INTEGER);
            callableStatement.execute();
            oeq.setId(callableStatement.getInt(5));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(int id){
        String selectSql = "delete from oeqs where Id = ?";
        Connection connection = DBConfiguration.getDbConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1,id);
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Optional<OEQ> getById(int id){
        String selectSql = "select * from oeqs where Id = ?";
        Connection connection = DBConfiguration.getDbConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToOeq(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }


    Optional<OEQ> mapToOeq(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            int Id = resultSet.getInt(1);
            String question = resultSet.getString(2);
            int points = resultSet.getInt(3);
            String tips = resultSet.getString(4);
            Quiz quiz = new Quizzes().getById(resultSet.getInt(5)).get();




            return Optional.of(new OEQ(Id,question,points,quiz,tips));
        }

        return Optional.empty();
    }
}
