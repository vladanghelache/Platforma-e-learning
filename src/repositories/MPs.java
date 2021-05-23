package repositories;

import config.DBConfiguration;
import models.MP;
import models.Quiz;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MPs {
    public void insert(MP mp) {
        String insertMpSql = "call insert_mp(?,?,?,?,?,?)";
        Connection connection = DBConfiguration.getDbConnection();

        try {
            CallableStatement callableStatement = connection.prepareCall(insertMpSql);

            callableStatement.setString(1, mp.getQuestion());
            callableStatement.setInt(2, mp.getPoints());
            StringBuilder str1 = new StringBuilder();
            for (Map.Entry<Character,String> entry : mp.getColumnA().entrySet()) {
                str1.append(entry.getKey());
                str1.append("/");
                str1.append(entry.getValue());
                str1.append("~");
            }

            StringBuilder str2 = new StringBuilder();

            for (Map.Entry<Integer,String> entry : mp.getColumnB().entrySet()) {
                str2.append(entry.getKey());
                str2.append("/");
                str2.append(entry.getValue());
                str2.append("~");
            }
            callableStatement.setString(3, str1.toString());
            callableStatement.setString(4, str2.toString());
            callableStatement.setInt(5, mp.getQuiz().getId());


            callableStatement.registerOutParameter(6, Types.INTEGER);
            callableStatement.execute();
            mp.setId(callableStatement.getInt(6));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Optional<MP> getById(int id){
        String selectSql = "select * from mps where Id = ?";
        Connection connection = DBConfiguration.getDbConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToMp(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    public void delete(int id){
        String selectSql = "delete from mps where Id = ?";
        Connection connection = DBConfiguration.getDbConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1,id);
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    Optional<MP> mapToMp(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            int Id = resultSet.getInt(1);
            String question = resultSet.getString(2);
            int points = resultSet.getInt(3);
            String A = resultSet.getString(4);
            String B = resultSet.getString(5);
            Quiz quiz = new Quizzes().getById(resultSet.getInt(6)).get();

            Map<Character, String> columnA = new HashMap<>();

            for (String grade : A.split("~")){

                String[] gr = grade.split("/");

                columnA.put(gr[0].toCharArray()[0], gr[1]);

            }


            Map<Integer, String> columnB = new HashMap<>();

            for (String grade : B.split("~")){

                String[] gr = grade.split("/");

                columnB.put(Integer.parseInt(gr[0]), gr[1]);

            }


            return Optional.of(new MP(Id,question,points,quiz,columnA,columnB));
        }

        return Optional.empty();
    }
}
