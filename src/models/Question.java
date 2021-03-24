package models;

public abstract class Question {
    private static int count = 0;
    private int Id;
    private String question;
    private int points;

    public Question(){

    }
    public Question(String question, int points){
        this.question = question;
        this.points = points;

    }

    {
        count ++;
        this.Id = count;

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

    public void setPoints(int points) {
        this.points = points;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
