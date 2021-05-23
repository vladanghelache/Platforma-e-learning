package repositories;

import config.DBConfiguration;
import models.Answer;
import models.Course;
import models.Student;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Students {
    public void insert(Student student) {
        String insertTeacherSql = "call insert_student(?,?,?,?,?,?)";
        Connection connection = DBConfiguration.getDbConnection();

        try {
            CallableStatement callableStatement = connection.prepareCall(insertTeacherSql);

            callableStatement.setString(1, student.getFirstName());
            callableStatement.setString(2, student.getLastName());
            callableStatement.setString(3, student.getPassword());
            callableStatement.setString(4, student.getEmail());
            callableStatement.setInt(5, student.getAge());

            callableStatement.registerOutParameter(6, Types.INTEGER);
            callableStatement.execute();
            student.setId(callableStatement.getInt(6));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Optional<Student> getById(int id){
        String selectSql = "select * from students where Id = ?";
        Connection connection = DBConfiguration.getDbConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToStudent(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<Student> getByEmail(String email){
        String selectSql = "select * from students where email = ?";
        Connection connection = DBConfiguration.getDbConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToStudent(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    public void joinCourse(int studentId, int courseId){
        String insertSql = "insert into student_course values (?,?)";
        Connection connection = DBConfiguration.getDbConnection();

        try {
            CallableStatement callableStatement = connection.prepareCall(insertSql);


            callableStatement.setInt(1, studentId);
            callableStatement.setInt(2, courseId);


            callableStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Set<Course> getCourses(int id){

        Set<Course> courses = new HashSet<>();
        Connection connection = DBConfiguration.getDbConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select c.Id,c.courseName,c.teacherId,c.category,c.startDate, c.endDate " +
                                                                                    "from courses c join student_course sc " +
                                                                                    "where sc.courseId = c.Id " +
                                                                                    "and sc.studentId = ?");
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

    public Set<Answer> getAnswers(int id){

        Set<Answer> answers = new HashSet<>();
        Connection connection = DBConfiguration.getDbConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from answers where studentId = ?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Answers ans = new Answers();

            Optional<Answer> answer1 = ans.mapToAnswer(resultSet);
            answer1.ifPresent(answers::add);

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

    Optional<Student> mapToStudent(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            int Id = resultSet.getInt(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String password = resultSet.getString(4);
            String email = resultSet.getString(5);
            int age = resultSet.getInt(6);



            return Optional.of(new Student(Id,firstName,lastName,password,email,age));
        }

        return Optional.empty();
    }
}
