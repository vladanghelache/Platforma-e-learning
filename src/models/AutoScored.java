package models;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class AutoScored extends Quiz {
    private Set<MCQ> MCQs;

    public AutoScored(){
        super();
    }

    public AutoScored(String quizName, int nrQuestions, @NotNull Set<MCQ> MCQs){
        super(quizName, nrQuestions);
        this.MCQs = MCQs;
        this.setNrQuestions(MCQs.size());
        int totalPoints = 0;
        for (MCQ question:
             MCQs) {
            totalPoints += question.getPoints();
        }
        this.setTotalPoints(totalPoints);


    }

    public Set<MCQ> getMCQs() {
        return MCQs;
    }

    public void setMCQs(Set<MCQ> MCQs) {
        this.MCQs = MCQs;
    }


    public void addQuestion(MCQ question) {
        this.MCQs.add(question);
    }

}
