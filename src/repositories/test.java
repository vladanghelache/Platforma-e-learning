package repositories;

import models.Course;
import models.Teacher;

public class test {
    public static void main(String[] args) {
        Course course = new Courses().getById(1).get();

        System.out.println(course);
    }
}
