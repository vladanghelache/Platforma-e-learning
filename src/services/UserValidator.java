package services;

import models.User;

public class UserValidator {


    public ValidationInfo validateWithErrorMessage(User user){
        StringBuilder errorMessage = new StringBuilder();
        boolean valid[] = {false, false, false, false};

        if(!validateEmail(user.getEmail())){
            errorMessage.append("Invalid email\n");
        }
        else {
            valid[0] = true;
        }
        if(!validateName(user.getFirstName())){
            errorMessage.append("Invalid first name\n");
        }
        else {
            valid[1] = true;
        }
        if(!validateName(user.getLastName())){
            errorMessage.append("Invalid last name\n");
        }
        else {
            valid[2] = true;
        }
        if(!validatePassword(user.getPassword())){
            errorMessage.append("Invalid password\n");
        }
        else {
            valid[3] = true;
        }

        return new ValidationInfo(valid,(errorMessage.isEmpty() ? "Registration completed\n" : errorMessage.toString()));
    }

    private boolean validateEmail(String email){
        String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        return email.matches(regex);
    }

    private boolean validateName(String name){
        String regex = "^[A-Z][a-z]+$";
        return name.matches(regex);
    }

    private boolean validatePassword(String password){
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$";
        return password.matches(regex);
    }


}
