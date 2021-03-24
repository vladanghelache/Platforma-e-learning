package models;

import java.util.List;

public class Answer {
    private static int count = 0;
    private int Id;
    private Student student;
    private List<String> answer;

    public Answer(){

    }

    public Answer(Student student, List<String> answer){

        this.student = student;
        this.answer = answer;
    }

    {
        count++;
        this.Id = count;
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

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
