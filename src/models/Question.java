package models;

import java.util.Optional;

public abstract class Question {

    private int Id;
    private String question;
    private int points;
    private Quiz quiz;

    public Question(){

    }
    public Question(String question, int points, Quiz quiz){
        this.question = question;
        this.points = points;
        this.quiz = quiz;

    }

    public Question(int Id,String question, int points, Quiz quiz){
        this.question = question;
        this.points = points;
        this.quiz = quiz;
        this.Id = Id;

    }


    public int getId() {
        return Id;
    }

    public String getQuestion() {
        return question;
    }

    public int getPoints() {
        return points;
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



    public void setPoints(int points) {
        this.points = points;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
