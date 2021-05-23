package models;

import java.util.Optional;

public abstract class User {

    private int Id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

    public User (int Id, String firstName, String lastName, String password, String email) {

        this.Id = Id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;


    }

    public User (String firstName, String lastName, String password, String email) {


        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;

    }

    public User(){


    }


    public int getId() {
        return Id;
    }

    public String getEmail() {
        return email.toString();
    }

    public String getFirstName() {
        return firstName.toString();
    }

    public String getLastName() {
        return lastName.toString();
    }

    public String getPassword() {
        return password.toString();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
