package models;


public class OEQ extends Question {
    private String tips;

    public OEQ() {
        super();
    }
    public OEQ(String question, int points, String tips){
        super(question, points);
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    @Override
    public String toString() {
        return getQuestion() + "  Punctaj: " + getPoints() + "\n" + "Tips: " + tips + "\n";
    }
}
