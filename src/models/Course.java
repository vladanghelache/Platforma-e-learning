package models;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Course {
    private static int count = 0;
    private int Id;
    private String courseName;
    private Teacher teacher;
    private Category category;
    private Date startDate;
    private Date endDate;
    private Set<Quiz> quizzes;
    private Set<Student> students;

    public Course(){

    }

    public Course(String courseName, Teacher teacher, Category category, Date startDate, Date endDate){
        this.courseName = courseName;
        this.teacher = teacher;
        this.category = category;
        this.endDate = endDate;
        this.startDate = startDate;
    }

    {
        count ++;
        Id = count;
        this.quizzes =  new HashSet<Quiz>();
        this.students = new HashSet<Student>();
    }

    public int getId() {
        return Id;
    }

    public Category getCategory() {
        return category;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Set<Quiz> getQuizzes() {
        return quizzes;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void addQuiz(Quiz quiz){
        this.quizzes.add(quiz);
    }

    public void addStudent(Student student){
        this.students.add(student);
    }

    @Override
    public String toString() {
        return "models.Course{" +
                "Id=" + Id +
                ", courseName='" + courseName + '\'' +
                ", teacher=" + teacher.getFirstName() + " " + teacher.getLastName() +
                ", category=" + category +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
