import java.util.Collections;
import java.util.Set;

public class Teacher extends User {
    Set<Course> Courses;
    String qualification;

    public Teacher(){
        super();

    }

    public Teacher (String username, String firstName, String lastName, String password, String email, String qualification){
        super(username,firstName,lastName,password,email);

        this.qualification = qualification;

    }

    {
        this.Courses = new Collections.emptySet<Course>();
    }

    public Set<Course> getCourses() {
        return Courses;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void addCourse(Course course){
        this.Courses.add(course);
    }

}
