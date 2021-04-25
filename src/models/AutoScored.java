package models;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class AutoScored extends Quiz {
    private List<MCQ> MCQs;

    public AutoScored(){
        super();
    }

    public AutoScored(String quizName, int nrQuestions,Teacher teacher, @NotNull List<MCQ> MCQs){
        super(quizName, nrQuestions, teacher);
        this.MCQs = new ArrayList<MCQ>(MCQs);
        this.setNrQuestions(MCQs.size());
        int totalPoints = 0;
        for (MCQ question:
             MCQs) {
            totalPoints += question.getPoints();
        }
        this.setTotalPoints(totalPoints);


    }

    {
        this.MCQs = new ArrayList<>();
    }

    public List<MCQ> getMCQs() {
        return MCQs;
    }

    public void setMCQs(List<MCQ> MCQs) {
        this.MCQs = new ArrayList<MCQ>(MCQs);
    }


    public void addQuestion(MCQ question) {
        this.MCQs.add(question);
    }

    public  void removeQuestion(MCQ question){
        MCQs.remove(question);
    }

}
