package models;


import java.util.Map;

public class MP extends Question {

    private Map<Character, String> columnA;
    private Map<Integer, String> columnB;

    public MP(){
        super();
    }

    public MP(int points, Map<Character, String> columnA, Map<Integer, String> columnB){
        super("Asociati elementele din coloana A cu cele din coloana B:",points);
        this.columnA = columnA;
        this.columnB = columnB;

    }

    public Map<Character, String> getColumnA() {
        return columnA;
    }

    public Map<Integer, String> getColumnB() {
        return columnB;
    }

    public void setColumnA(Map<Character, String> columnA) {
        this.columnA = columnA;
    }

    public void setColumnB(Map<Integer, String> columnB) {
        this.columnB = columnB;
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder("A: \n");

        for (Map.Entry<Character, String> elem:
                columnA.entrySet()) {

            str.append(elem.getKey() + ") " + elem.getValue() + "\n");

        }

        str.append("B: \n");

        for (Map.Entry<Integer, String> elem:
                columnB.entrySet()) {

            str.append(elem.getKey() + ") " + elem.getValue() + "\n");

        }
        return getQuestion() + "  Punctaj: " + getPoints() + "\n" + str;
    }
}
