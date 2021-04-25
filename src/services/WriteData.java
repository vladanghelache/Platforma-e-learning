package services;

import models.*;
import models.User;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

public class WriteData {

    private static WriteData instance;

    private WriteData(){

    }

    public static WriteData getInstance(){
        if (instance == null){
            instance = new WriteData();
        }

        return instance;
    }

    public void writeData(Map<Integer, User> Users,
                          Map<Integer, Course> Courses,
                          Map<Integer, Quiz> Quizzes,
                          Map<Integer, Question> Questions,
                          Map<Integer, Answer> Ansewrs){

        try{
            writeAnsewrs(Ansewrs);
            writeTeachers(Users);
            writeStudents(Users);
            writeCourses(Courses);
            writeMCQs(Questions);
            writeOEQs(Questions);
            writeMPs(Questions);
            writeAutoScored(Quizzes);
            writeNormal(Quizzes);

        }catch(IOException e){
            System.out.println("Eroare: Salvarea datelor a esuat!");
            System.out.println(e);
        }

    }

    private void writeTeachers(Map<Integer, User> Users) throws IOException {

        FileWriter writer = new FileWriter("Data/Teachers.csv");

        writer.write("Id,firstName,lastName,password,email,qualification\n");

        for (Map.Entry<Integer, User> user :
                Users.entrySet()) {
            if (user.getValue() instanceof Teacher){
                StringBuilder line = new StringBuilder();
                line.append(user.getKey());
                line.append(',');
                line.append(user.getValue().getFirstName());
                line.append(',');
                line.append(user.getValue().getLastName());
                line.append(',');
                line.append(user.getValue().getPassword());
                line.append(',');
                line.append(user.getValue().getEmail());
                line.append(',');
                line.append(((Teacher) user.getValue()).getQualification());
                line.append("\n");

                writer.write(line.toString());

            }
        }
        writer.close();

    }

    private void writeStudents(Map<Integer, User> Users) throws IOException {

        FileWriter writer = new FileWriter("Data/Students.csv");

        writer.write("Id,firstName,lastNAme,password,email,age,coursesAttended,grades\n");

        for (Map.Entry<Integer, User> user :
                Users.entrySet()) {
            if (user.getValue() instanceof Student){
                StringBuilder line = new StringBuilder();
                line.append(user.getKey());
                line.append(',');
                line.append(user.getValue().getFirstName());
                line.append(',');
                line.append(user.getValue().getLastName());
                line.append(',');
                line.append(user.getValue().getPassword());
                line.append(',');
                line.append(user.getValue().getEmail());
                line.append(',');
                line.append(((Student) user.getValue()).getAge());
                line.append(',');
                for (Course course:
                     ((Student) user.getValue()).getCourses()) {

                    line.append(course.getId());
                    line.append(" ");

                }
                line.append(",");
                for (Map.Entry<String, Integer> grade:
                     ((Student) user.getValue()).getGrades().entrySet()) {
                    line.append(grade.getKey());
                    line.append("/");
                    line.append(grade.getValue());
                    line.append("~");
                }
                line.append("\n");

                writer.write(line.toString());

            }
        }
        writer.close();

    }

    private void writeCourses(Map<Integer, Course> Courses) throws IOException {

        FileWriter writer = new FileWriter("Data/Courses.csv");

        writer.write("Id,courseName,teacher,category,startDate,endDate,quizzes\n");

        for (Map.Entry<Integer, Course> course :
                Courses.entrySet()) {
            StringBuilder line = new StringBuilder();
            line.append(course.getKey());
            line.append(',');
            line.append(course.getValue().getCourseName());
            line.append(',');
            line.append(course.getValue().getTeacher().getId());
            line.append(',');
            line.append(course.getValue().getCategory());
            line.append(',');

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            line.append(dateFormat.format(course.getValue().getStartDate()));
            line.append(',');
            line.append(dateFormat.format(course.getValue().getEndDate()));
            line.append(',');

            for (Quiz quiz: ((Course) course.getValue()).getQuizzes()) {

                line.append(quiz.getId());
                line.append(" ");

            }


            line.append("\n");
            writer.write(line.toString());


        }
        writer.close();

    }

    private void writeAutoScored(Map<Integer, Quiz> Quizzes) throws IOException {

        FileWriter writer = new FileWriter("Data/AutoScored.csv");

        writer.write("Id,quizName,nrQuestions,answers,totalPoints,teacher,MCQs\n");

        for (Map.Entry<Integer, Quiz> quiz :
                Quizzes.entrySet()) {

            if (quiz.getValue() instanceof AutoScored) {

                StringBuilder line = new StringBuilder();
                line.append(quiz.getKey());
                line.append(',');
                line.append(quiz.getValue().getQuizName());
                line.append(',');
                line.append(quiz.getValue().getNrQuestions());
                line.append(',');
                for (Answer answer :
                        quiz.getValue().getAnswers()) {
                    line.append(answer.getId());
                    line.append(" ");
                }
                line.append(",");
                line.append(quiz.getValue().getTotalPoints());
                line.append(',');


                line.append(quiz.getValue().getTeacher().getId());
                line.append(',');
                for (MCQ mcq :
                        ((AutoScored) quiz.getValue()).getMCQs()) {
                    line.append(mcq.getId());
                    line.append(" ");
                }


                line.append("\n");
                writer.write(line.toString());
            }

        }
        writer.close();

    }

    private void writeNormal(Map<Integer, Quiz> Quizzes) throws IOException {

        FileWriter writer = new FileWriter("Data/Normal.csv");

        writer.write("Id,quizName,nrQuestions,answers,totalPoints,teacher,questions\n");

        for (Map.Entry<Integer, Quiz> quiz :
                Quizzes.entrySet()) {

            if (quiz.getValue() instanceof NormalQuiz) {

                StringBuilder line = new StringBuilder();
                line.append(quiz.getKey());
                line.append(',');
                line.append(quiz.getValue().getQuizName());
                line.append(',');
                line.append(quiz.getValue().getNrQuestions());
                line.append(',');
                for (Answer answer :
                        quiz.getValue().getAnswers()) {
                    line.append(answer.getId());
                    line.append(" ");
                }
                line.append(",");
                line.append(quiz.getValue().getTotalPoints());
                line.append(',');


                line.append(quiz.getValue().getTeacher().getId());
                line.append(',');
                for (Question question :
                        ((NormalQuiz) quiz.getValue()).getQuestions()) {
                    line.append(question.getId());
                    line.append(" ");
                }


                line.append("\n");
                writer.write(line.toString());
            }

        }
        writer.close();

    }

    private void writeMCQs(Map<Integer, Question> Questions) throws IOException {

        FileWriter writer = new FileWriter("Data/MCQ.csv");

        writer.write("Id,question,points,nrChoices,choices,correctAnswer\n");

        for (Map.Entry<Integer, Question> question :
                Questions.entrySet()) {

            if (question.getValue() instanceof MCQ) {

                StringBuilder line = new StringBuilder();
                line.append(question.getKey());
                line.append(',');
                line.append(question.getValue().getQuestion());
                line.append(',');
                line.append(question.getValue().getPoints());
                line.append(',');
                line.append(((MCQ) question.getValue()).getNrChoices());
                line.append(',');
                for (Map.Entry<Character,String> choice : ((MCQ) question.getValue()).getChoices().entrySet()) {
                    line.append(choice.getKey());
                    line.append("/");
                    line.append(choice.getValue());
                    line.append("~");
                }
                line.append(",");
                line.append(((MCQ) question.getValue()).getCorrectAnswer());


                line.append("\n");
                writer.write(line.toString());
            }

        }
        writer.close();

    }

    private void writeMPs(Map<Integer, Question> Questions) throws IOException {

        FileWriter writer = new FileWriter("Data/MP.csv");

        writer.write("Id,question,points,columnA,columnB\n");

        for (Map.Entry<Integer, Question> question :
                Questions.entrySet()) {

            if (question.getValue() instanceof MP) {

                StringBuilder line = new StringBuilder();
                line.append(question.getKey());
                line.append(',');
                line.append(question.getValue().getQuestion());
                line.append(',');
                line.append(question.getValue().getPoints());
                line.append(',');

                for (Map.Entry<Character,String> entry : ((MP) question.getValue()).getColumnA().entrySet()) {
                    line.append(entry.getKey());
                    line.append("/");
                    line.append(entry.getValue());
                    line.append("~");
                }
                line.append(",");
                for (Map.Entry<Integer,String> entry : ((MP) question.getValue()).getColumnB().entrySet()) {
                    line.append(entry.getKey());
                    line.append("/");
                    line.append(entry.getValue());
                    line.append("~");
                }

                line.append("\n");
                writer.write(line.toString());
            }

        }
        writer.close();

    }

    private void writeOEQs(Map<Integer, Question> Questions) throws IOException {

        FileWriter writer = new FileWriter("Data/OEQ.csv");

        writer.write("Id,question,points,tips\n");

        for (Map.Entry<Integer, Question> question :
                Questions.entrySet()) {

            if (question.getValue() instanceof OEQ) {

                StringBuilder line = new StringBuilder();
                line.append(question.getKey());
                line.append(',');
                line.append(question.getValue().getQuestion());
                line.append(',');
                line.append(question.getValue().getPoints());
                line.append(',');
                line.append(((OEQ) question.getValue()).getTips());


                line.append("\n");
                writer.write(line.toString());
            }

        }

        writer.close();

    }



    private void writeAnsewrs(Map<Integer,Answer>Answers) throws IOException {

        FileWriter writer = new FileWriter("Data/Answers.csv");

        writer.write("Id,student,answer\n");

        for (Map.Entry<Integer, Answer> answer :
                Answers.entrySet()) {
            StringBuilder line = new StringBuilder();

            line.append(answer.getKey());
            line.append(',');

            line.append(answer.getValue().getStudent().getId());
            line.append(",");

            for (String a :
                    answer.getValue().getAnswer()) {
                line.append(a);
                line.append("/");

            }

            line.append("\n");
            writer.write(line.toString());

        }
        writer.close();

    }

}
