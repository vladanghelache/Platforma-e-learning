package models;



import java.util.HashSet;
import java.util.Set;

public abstract class Quiz {
    private static int count = 0;
    private int Id;
    private String quizName;
    private int nrQuestions;
    private Set<Answer> answers;
    private int totalPoints;

    public Quiz(){

    }

    public Quiz(String quizName, int nrQuestions){
        this.quizName = quizName;
        this.nrQuestions = nrQuestions;
    }

    {
        count ++;
        this.Id = count;
        this.answers = new HashSet<Answer>();

    }

    public int getId() {
        return Id;
    }

    public int getNrQuestions() {
        return nrQuestions;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public void setNrQuestions(int nrQuestions) {
        this.nrQuestions = nrQuestions;
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }


    @Override
    public String toString() {
        return "Quiz{" +
                "Id=" + Id +
                ", quizName='" + quizName + '\'' +
                ", nrQuestions=" + nrQuestions +
                ", totalPoints=" + totalPoints +
                '}';
    }
}
