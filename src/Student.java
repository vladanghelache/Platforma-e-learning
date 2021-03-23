import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class Student extends User{
    private int age;
    private Set<Course> coursesAttended;
    private Map<String,Integer> grades;

    public Student(){
        super();
    }

    public Student(String username, String firstName, String lastName, String password, String email, int age){

        super(username,firstName,lastName,password,email);

        this.age = age;

    }

    {
        this.coursesAttended = new Collections.emptySet<Course>();
        this.grades = new  Collections.emptyMap<Course>();
    }

    public int getAge() {
        return age;
    }

    public Set<Course> getCourses() {
        return coursesAttended;
    }

    public Map<String, Integer> getGrades() {
        return grades;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void addGrade(String quiz, Integer grade){
        this.grades.put(quiz, grade);
    }

    public void addCourse(Course course){
        this.coursesAttended.add(course);
    }
}
