package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Answer {

    private int Id;
    private Student student;
    private List<String> answer;
    private double grade;
    private Quiz quiz;

    public Answer(){
        this.grade = -1;
    }
    public Answer(Student student, List<String> answer, Quiz quiz){

        this.student = student;
        this.answer = new ArrayList<>(answer);
        this.grade = -1;
        this.quiz = quiz;
    }
    public Answer(int Id,Student student, List<String> answer, double grade, Quiz quiz){

        this.student = student;
        this.answer = new ArrayList<>(answer);
        this.Id = Id;
        this.grade = grade;
        this.quiz = quiz;

    }



    public int getId() {
        return Id;
    }

    public Student getStudent() {
        return student;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "Id=" + Id +
                "', Student= " +student.getFirstName()+" "+student.getLastName()+
                '}';
    }
}
