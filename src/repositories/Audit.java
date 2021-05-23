package repositories;

import config.DBConfiguration;


import java.sql.*;
import java.time.LocalDateTime;

public class Audit {
    public void insert(String action, LocalDateTime date) {
        String insertSql = "insert into audit values (?,?)";
        Connection connection = DBConfiguration.getDbConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);

            preparedStatement.setString(1, action);

            preparedStatement.setTimestamp(2, Timestamp.valueOf(date));

            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
