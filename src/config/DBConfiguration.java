package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfiguration {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/e_learning_platform";
    private static final String USER = "root";
    private static final String PASSWORD = "sibelius";

    private  static Connection dbConnection;

    private DBConfiguration(){

    }

    public static  Connection getDbConnection() {
        try{
            if (dbConnection == null || dbConnection.isClosed()){
                dbConnection = DriverManager.getConnection(DB_URL,USER,PASSWORD);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return dbConnection;
    }

    public static void closeDbConnection() {
        try{
            if(dbConnection != null && !dbConnection.isClosed()){
                dbConnection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
