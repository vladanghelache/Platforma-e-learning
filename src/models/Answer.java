package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Answer {
    private static int count = 0;
    private int Id;
    private Student student;
    private List<String> answer;

    public Answer(){

    }
    public Answer(Student student, List<String> answer){

        this.student = student;
        this.answer = new ArrayList<>(answer);
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

    public void setId(int id) {
        Id = id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public static void setCount(Integer count) {
        Answer.count = count;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "Id=" + Id +
                "', Student= " +student.getFirstName()+" "+student.getLastName()+
                '}';
    }
}
