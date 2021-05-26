package repositories;

import config.DBConfiguration;
import models.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class Courses {
    public void insert(Course course) {
        String insertCourseSql = "insert into courses (courseName,teacherId,category,startDate,endDate) values (?,?,?,?,?)";
        Connection connection = DBConfiguration.getDbConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertCourseSql);

            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setInt(2, course.getTeacher().getId());
            preparedStatement.setString(3, course.getCategory().toString());
            preparedStatement.setDate(4, new java.sql.Date(course.getStartDate().getTime()));
            preparedStatement.setDate(5, new java.sql.Date(course.getEndDate().getTime()));

            preparedStatement.execute();
            PreparedStatement preparedStatement2 = connection.prepareStatement("select last_insert_id()");

            ResultSet resultSet = preparedStatement2.executeQuery();
            resultSet.next();
            course.setId(resultSet.getInt(1));

        } catch (SQLException throwables) {
            System.out.println(throwables.toString());
        }
    }

    public Optional<Course> getById(int id){
        String selectSql = "select * from courses where Id = ?";
        Connection connection = DBConfiguration.getDbConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToCourse(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    public Map<Integer, Course> getAll(){
        Map<Integer, Course> courses = new HashMap<>();
        Connection connection = DBConfiguration.getDbConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from courses");

            ResultSet resultSet = preparedStatement.executeQuery();

            Optional<Course> course1 = mapToCourse(resultSet);
            course1.ifPresent(value -> courses.put(value.getId(), value));

            while(true){
                Optional<Course> course = mapToCourse(resultSet);
                course.ifPresent(value -> courses.put(value.getId(), value));
                if(course.isEmpty()){
                    break;
                }
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return courses;
    }

    public Map<Integer, Course> getByCategory(String category){
        Map<Integer, Course> courses = new HashMap<>();
        Connection connection = DBConfiguration.getDbConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from courses where category = ?");
            preparedStatement.setString(1, category);
            ResultSet resultSet = preparedStatement.executeQuery();

            Optional<Course> course1 = mapToCourse(resultSet);
            course1.ifPresent(value -> courses.put(value.getId(), value));

            while(true){

                Optional<Course> course = mapToCourse(resultSet);
                course.ifPresent(value -> courses.put(value.getId(), value));
                if(course.isEmpty()){
                    break;
                }
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return courses;
    }

    public SortedSet<Student> getStudents(int id){

        SortedSet<Student> students = new TreeSet<>();
        Connection connection = DBConfiguration.getDbConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select s.Id,s.firstName,s.lastName,s.password,s.email, s.age " +
                    "from students s join student_course sc " +
                    "where sc.studentId = s.Id " +
                    "and sc.courseId = ?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Students c = new Students();
            while(true){

                Optional<Student> student = c.mapToStudent(resultSet);
                student.ifPresent(students::add);
                if(student.isEmpty()){
                    break;
                }
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return students;

    }

    public Set<Quiz> getQuizzes(int id){

        Set<Quiz> quizzes = new HashSet<>();
        Connection connection = DBConfiguration.getDbConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * " +
                    "from quizzes " +
                    "where courseId = ? "
                    );
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Quizzes c = new Quizzes();
            while(true){
                Optional<Quiz> quiz = c.mapToQuiz(resultSet);
                quiz.ifPresent(quizzes::add);
                if(quiz.isEmpty()){
                    break;
                }
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return quizzes;

    }


    Optional<Course> mapToCourse(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            int Id = resultSet.getInt(1);
            String courseName = resultSet.getString(2);
            Teacher teacher = new Teachers().getById(resultSet.getInt(3)).get();
            Category category = Category.valueOf(resultSet.getString(4));
            Date startDate = resultSet.getDate(5);
            Date endDate = resultSet.getDate(6);



            return Optional.of(new Course(Id,courseName,teacher,category,startDate,endDate));
        }

        return Optional.empty();
    }
}
