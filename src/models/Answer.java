package models;

import java.util.ArrayList;
import java.util.List;

public final class Answer {
    private static int count = 0;
    private final int Id;
    private final Student student;
    private final List<String> answer;


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
}
