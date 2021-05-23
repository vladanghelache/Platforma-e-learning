package models;



import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class Quiz {

    private int Id;
    private String quizName;
    private int nrQuestions;
    private Set<Answer> answers;
    private int totalPoints;
    private Teacher teacher;
    private Course course;

    public Quiz(){

    }

    public Quiz(String quizName, int nrQuestions, Teacher teacher, Course course){
        this.quizName = quizName;
        this.nrQuestions = nrQuestions;
        this.teacher = teacher;
        this.course = course;
    }

    public Quiz(int Id, String quizName, int nrQuestions, Teacher teacher, Course course,int totalPoints){
        this.quizName = quizName;
        this.nrQuestions = nrQuestions;
        this.teacher = teacher;
        this.course = course;
        this.Id = Id;
        this.totalPoints = totalPoints;
    }


    {
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public String getQuizName() {
        return quizName;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setId(int id) {
        Id = id;
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
                "teacher='" + teacher.getFirstName() + " " + teacher.getLastName() +
                "', quizName='" + quizName + '\'' +
                ", nrQuestions=" + nrQuestions +
                ", totalPoints=" + totalPoints +
                '}';
    }
}
