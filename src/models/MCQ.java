package models;


import java.util.Map;

//Multiple-choice question
public class MCQ extends Question {

    private  int nrChoices;
    private Map<Character,String> choices;
    private Character correctAnswer;

    public MCQ(){
        super();
    }

    public MCQ(String question, int points, int nrChoices, Map<Character,String> choices, Character correctAnswer){

        super(question,points);
        this.nrChoices = nrChoices;
        this.choices = choices;
        this.correctAnswer = correctAnswer;

    }

    public Character getCorrectAnswer() {
        return correctAnswer;
    }

    public int getNrChoices() {
        return nrChoices;
    }

    public Map<Character,String> getChoices() {
        return choices;
    }

    public void setNrChoices(int nrChoices) {
        this.nrChoices = nrChoices;
    }

    public void setCorrectAnswer(Character correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setChoices(Map<Character,String> choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder();

        for (Map.Entry<Character, String> choice:
             choices.entrySet()) {

            str.append(choice.getKey() + ") " + choice.getValue() + "\n");

        }

        return getQuestion() + "  Punctaj: " + getPoints() + "\n" + str ;
    }
}
