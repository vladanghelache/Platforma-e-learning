package Validators;
import models.Course;

import java.util.Date;

public class CourseValidator {
    public ValidationInfo validateWithErrorMessage(Course course){
        StringBuilder errorMessage = new StringBuilder();
        boolean valid[] = {false, false, false};
        if(!validateName(course.getCourseName())){
            errorMessage.append("Invalid name\n");
        }
        else{
            valid[0] = true;
        }
        if (!validateDate(course.getStartDate())){
            errorMessage.append("Invalid startDate: nu puteti selecta o data din trecut\n");
        }
        else {
            valid[1] = true;
        }
        if (!validateDate(course.getStartDate(), course.getEndDate())) {
            errorMessage.append("Invalid endDate: data finala trebuie sa succeada data de inceput\n");
        }
        else{
            valid[2]=true;
        }
        return new ValidationInfo(valid,(errorMessage.isEmpty() ? "Cursul a fost adaugat cu succes\n" : errorMessage.toString()));

    }

    private boolean validateName(String name){
        String regex = "^[A-Z][a-zA-Z -]+$";
        return name.matches(regex);
    }

    private boolean validateDate(Date startDate, Date endDate){
        return startDate.before(endDate);
    }

    private boolean validateDate(Date date){
        return date.after(new Date(System.currentTimeMillis()));
    }
}
