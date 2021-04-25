package models;

import java.util.HashSet;
import java.util.Set;

public class Teacher extends User {
    Set<Course> Courses;
    String qualification;

    public Teacher(){
        super();

    }

    public Teacher ( String firstName, String lastName, String password, String email, String qualification){
        super(firstName,lastName,password,email);

        this.qualification = qualification;

    }

    {
        this.Courses = new HashSet<Course>();
    }


    public Set<Course> getCourses() {
        return Courses;
    }

    public String getQualification() {
        return qualification.toString();
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void addCourse(Course course){
        this.Courses.add(course);
    }

    @Override
    public String toString(){
        return "Teacher{"+
                "Id='"+getId()+
                "', firstName='"+getFirstName()+
                "', lastName='"+getLastName()+
                "', password='"+getPassword()+
                "', email='"+getEmail()+
                "', qualification='"+qualification+"'}";
    }
}
