package models;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Student extends User implements Comparable<Student> {
    private int age;
    private Set<Course> coursesAttended;
    private Map<String,Integer> grades;

    public Student(){
        super();
    }

    public Student(String firstName, String lastName, String password, String email, int age){

        super(firstName,lastName,password,email);

        this.age = age;

    }

    public Student( int Id, String firstName, String lastName, String password, String email, int age){

        super(Id,firstName,lastName,password,email);

        this.age = age;

    }

    {
        this.coursesAttended = new HashSet<Course>();
        this.grades = new HashMap<String,Integer>();
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

    @Override
    public String toString(){
        return "Student{"+
                "Id='"+getId()+
                "', firstName='"+getFirstName()+
                "', lastName='"+getLastName()+
                "', password='"+getPassword()+
                "', email='"+getEmail()+
                "', age='"+age+"'}";
    }

    @Override
    public int compareTo(@NotNull Student o) {
        return this.getLastName().compareTo(o.getLastName());
    }
}
