package models;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NormalQuiz extends Quiz {
    private List<Question> questions;

    public NormalQuiz(){
        super();
    }

    public NormalQuiz(String quizName, int nrQuestions, Teacher teacher,Course course, @NotNull List<Question> questions){
        super(quizName, nrQuestions, teacher,course);
        this.questions = new ArrayList<Question>(questions);
        this.setNrQuestions(questions.size());
        int totalPoints = 0;
        for (Question question:
                questions) {
            totalPoints += question.getPoints();
        }
        this.setTotalPoints(totalPoints);


    }

    public NormalQuiz(int id, String quizName, int nrQuestions, int totalPoints, Teacher teacher, Course course) {
        super(id,quizName, nrQuestions, teacher,course, totalPoints);


    }

    {
        this.questions = new ArrayList<>();
    }



    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = new ArrayList<Question>(questions);
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
    }


}
