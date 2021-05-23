package repositories;

import config.DBConfiguration;

public class Database {

    public Answers answers;
    public Courses courses;
    public MCQs mcqs;
    public MPs mps;
    public OEQs oeqs;
    public Quizzes quizzes;
    public Students students;
    public Teachers teachers;
    public Audit audit;

    public Database(){
        answers = new Answers();
        courses = new Courses();
        mcqs = new MCQs();
        mps = new MPs();
        oeqs = new OEQs();
        quizzes = new Quizzes();
        students = new Students();
        teachers = new Teachers();
        audit = new Audit();
    }

    public void closeConnection(){
        DBConfiguration.closeDbConnection();
    }

}
