package models;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class NormalQuiz extends Quiz {
    private Set<Question> questions;

    public NormalQuiz(){
        super();
    }

    public NormalQuiz(String quizName, int nrQuestions, @NotNull Set<Question> questions){
        super(quizName, nrQuestions);
        this.questions = questions;
        this.setNrQuestions(questions.size());
        int totalPoints = 0;
        for (Question question:
                questions) {
            totalPoints += question.getPoints();
        }
        this.setTotalPoints(totalPoints);


    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }
}
