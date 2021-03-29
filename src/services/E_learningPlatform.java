package services;

import models.*;

import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

public class E_learningPlatform {

    private static E_learningPlatform instance;
    private static Set<Course> Courses;
    private static Set<User> Users;
    private User currentUser;

    static {
        Courses = new HashSet<>();
        Users = new HashSet<>();
    }
    private E_learningPlatform() {
        System.out.print("Bine ati venit!\n");
    }

    public static E_learningPlatform getInstance(){
        if(instance == null){
            instance = new E_learningPlatform();
        }

        return instance;
    }

    public void register(){

        if(currentUser == null) {
            boolean isValid = false;
            Scanner scan = new Scanner(System.in);
            boolean valid[] = {false, false, false, false};
            System.out.print("Pentru a crea un cont de student apasati tasta 'S'\nPentru a crea un cont de profesor apasasati tasta 'P': ");
            String option = scan.next();
            while (!option.equalsIgnoreCase("s") && !option.equalsIgnoreCase("p")) {
                option = scan.next();
            }
            User newUser = TorS(option);


            while (!isValid) {
                if (!valid[0]) {
                    boolean unic = false;

                    while (!unic) {
                        System.out.print("Introduceti adresa de email: ");
                        String email = scan.next();
                        unic = true;
                        for (User user :
                                Users) {
                            if (user.getEmail().equals(email)) {
                                System.out.println("Adresa de email introdusa este deja asociata unui cont");
                                unic = false;
                                break;
                            }
                        }
                        if (unic) {
                            newUser.setEmail(email);
                        }

                    }

                }
                if (!valid[3]) {
                    System.out.print("Introduceti parola (intre 8 si 20 de caractere, cel putin o litera, o cifra si un simbol '@#$%^&-+=()' ): ");
                    newUser.setPassword(scan.next());
                }
                if (!valid[2]) {
                    System.out.print("Introduceti numele: ");
                    newUser.setLastName(scan.next());
                }
                if (!valid[1]) {
                    System.out.print("Introduceti prenumele: ");
                    newUser.setFirstName(scan.next());
                }

                ValidationInfo info = new UserValidator().validateWithErrorMessage(newUser);

                if (info.getErrorMessage().equals("Registration completed")) {
                    isValid = true;

                    if (newUser instanceof Student) {
                        System.out.print("Introduceti varsta dumneavoastra: ");
                        ((Student) newUser).setAge(scan.nextInt());
                    } else {
                        System.out.print("Introduceti calificarile dumneavostra: ");
                        ((Teacher) newUser).setQualification(scan.next());
                    }

                    Users.add(newUser);
                    this.currentUser = newUser;
                } else {
                    valid = info.getValid();

                }
                System.out.println(info.getErrorMessage());


            }
        }
    }

    public void login(){
        if (currentUser == null){
            Scanner scanner = new Scanner(System.in);
            boolean foundUser = false;
            User user = null;
            System.out.print("Introduceti email: ");
            String email = scanner.next();
            for (User u :
                    Users) {
            if(email.equals(u.getEmail())){
                   user = u;
                   foundUser = true;
                   break;
                }
            }
            if (!foundUser){
                System.out.println("Nu exista niciun cont asociat emailului introdus");
            }
            else{
                System.out.print("Introduceti parola: ");
                String password = scanner.next();

                if(password.equals(user.getPassword())){
                    currentUser = user;
                }
            }

        }
    }

    public void logout(){
        if (currentUser != null){
            Scanner scanner = new Scanner(System.in);
            String option = " ";

            do {
                System.out.print("Sunteti sigur ca doriti sa va deconectati? ('D' = Da, 'N' = Nu): ");
                option = scanner.next();
            }
            while (!option.toLowerCase().equals( "d") && !option.toLowerCase().equals( "n"));

            if (option.toLowerCase().equals( "d")){
                currentUser = null;
            }

        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private User TorS(String option){
        if(option.toLowerCase().equals("s")){
            return new Student();
        }
        else {
            return new Teacher();
        }
    }

}


