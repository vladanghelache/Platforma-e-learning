package repositories;

import config.DBConfiguration;
import models.Course;
import models.Teacher;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Teachers {
    public void insert(Teacher teacher) {
        String insertTeacherSql = "call insert_teacher(?,?,?,?,?,?)";
        Connection connection = DBConfiguration.getDbConnection();

        try {
            CallableStatement callableStatement = connection.prepareCall(insertTeacherSql);

            callableStatement.setString(1, teacher.getFirstName());
            callableStatement.setString(2, teacher.getLastName());
            callableStatement.setString(3, teacher.getPassword());
            callableStatement.setString(4, teacher.getEmail());
            callableStatement.setString(5, teacher.getQualification());

            callableStatement.registerOutParameter(6,Types.INTEGER);
            callableStatement.execute();
            teacher.setId(callableStatement.getInt(6));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Optional<Teacher> getByEmail(String email){
        String selectSql = "select * from teachers where email = ?";
        Connection connection = DBConfiguration.getDbConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToTeacher(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<Teacher> getById(int id){
        String selectSql = "select * from teachers where Id = ?";
        Connection connection = DBConfiguration.getDbConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToTeacher(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    public Set<Course> getCourses(int id){

        Set<Course> courses = new HashSet<>();
        Connection connection = DBConfiguration.getDbConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * " +
                                                                                    "from courses " +
                                                                                    "where teacherId = ?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Courses c = new Courses();

            Optional<Course> course1 = c.mapToCourse(resultSet);
            course1.ifPresent(courses::add);

            while(true){
                Optional<Course> course = c.mapToCourse(resultSet);
                course.ifPresent(courses::add);

                if(course.isEmpty()){
                    break;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courses;

    }


    private Optional<Teacher> mapToTeacher(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            int Id = resultSet.getInt(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String password = resultSet.getString(4);
            String email = resultSet.getString(5);
            String qualifications = resultSet.getString(6);



            return Optional.of(new Teacher(Id,firstName,lastName,password,email,qualifications));
        }

        return Optional.empty();
    }
}
