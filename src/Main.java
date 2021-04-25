
import models.*;
import services.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException {
        E_learningPlatform sesiune = E_learningPlatform.getInstance();
        Audit audit = Audit.getInstance();
        Scanner scanner = new Scanner(System.in);
        try {
            exit:
            while (true) {
                while (sesiune.getCurrentUser() == null) {
                    System.out.println("Pentru a va crea un cont nou apasati tasta '1' ");
                    System.out.println("Pentru a va loga apasati tasta '2' ");
                    System.out.println("Pentru a inchide sesiunea apasati tasta '3'");
                    String option = scanner.next();

                    switch (option) {
                        case "1":
                            sesiune.register();
                            audit.writeAudit("register", LocalDateTime.now());
                            break;
                        case "2":
                            sesiune.login();
                            audit.writeAudit("login", LocalDateTime.now());
                            break;
                        case "3":
                            audit.writeAudit("exit", LocalDateTime.now());
                            break exit;
                        default:
                            System.out.println("Comanda necunoscuta");
                    }

                }
                while (sesiune.getCurrentUser() != null) {

                    System.out.println("Pentru a afisa informatiile dumneavoastra apasati tasta '1' ");
                    System.out.println("Pentru a va deloga apasati tasta '2' ");
                    System.out.println("Pentru a inchide sesiunea apasati tasta '3'");
                    System.out.println("Pentru a afisa cusurile apasati tasta '4'");


                    if (sesiune.getCurrentUser() instanceof Teacher) {
                        System.out.println("Pentru a crea un curs nou apasati tasta '5'");
                        System.out.println("Pentru a crea un quiz nou apasati tasta '6'");
                        System.out.println("Pentru a adauga o intrebare la un quiz apasati tasta '7'");
                        System.out.println("Pentru a sterge una sau mai multe intrebari dintr-un quiz apasati tasta '8'");
                        System.out.println("Pentru a nota un raspuns apasati tasta '9'");

                        String option = scanner.next();
                        switch (option) {
                            case "1":
                                System.out.println(sesiune.getCurrentUser());
                                break;
                            case "2":
                                sesiune.logout();
                                audit.writeAudit("logout", LocalDateTime.now());
                                break;
                            case "3":
                                audit.writeAudit("exit", LocalDateTime.now());
                                break exit;
                            case "4":
                                audit.writeAudit("showCoursesByCategory", LocalDateTime.now());
                                sesiune.showCoursesByCategory();
                                break;
                            case "5":

                                sesiune.createCourse();
                                audit.writeAudit("createCourse", LocalDateTime.now());
                                break;
                            case "6":
                                System.out.println("\nAlegeti cursul la care doriti sa adaugati un quiz nou: \n");
                                sesiune.showCourses(sesiune.getCurrentUser());
                                System.out.print("\nIntroduceti id-ul cursului: ");
                                int id = scanner.nextInt();
                                Course c = null;
                                for (Course course :
                                        ((Teacher) sesiune.getCurrentUser()).getCourses()) {
                                    if (course.getId() == id) {
                                        c = course;
                                        break;
                                    }
                                }
                                sesiune.addQuiz(c);
                                audit.writeAudit("addQuiz", LocalDateTime.now());
                                break;
                            case "7":
                                System.out.println("\nAlegeti cursul in cadrul caruia se gaseste quiz-ul: \n");
                                sesiune.showCourses(sesiune.getCurrentUser());
                                System.out.print("\nIntroduceti id-ul cursului: ");
                                int idCourse = scanner.nextInt();
                                Course course1 = null;
                                for (Course course :
                                        ((Teacher) sesiune.getCurrentUser()).getCourses()) {
                                    if (course.getId() == idCourse) {
                                        course1 = course;
                                        break;
                                    }
                                }
                                System.out.println("\nAlegeti quizul la care doriti sa adaugati o intrebare noua: \n");
                                for (Quiz quiz :
                                        course1.getQuizzes()) {
                                    System.out.println("Id: " + quiz.getId() + " ---- Quiz: " + quiz.getQuizName());

                                }
                                System.out.print("\nIntroduceti id-ul quiz-ului: ");
                                int idQuiz = scanner.nextInt();
                                for (Quiz quiz :
                                        course1.getQuizzes()) {
                                    if (quiz.getId() == idQuiz) {
                                        sesiune.addQuestion(quiz);
                                        break;
                                    }
                                }
                                audit.writeAudit("addQuestion", LocalDateTime.now());
                                break;
                            case "8":
                                System.out.println("\nAlegeti cursul in cadrul caruia se gaseste quiz-ul: \n");
                                sesiune.showCourses(sesiune.getCurrentUser());
                                System.out.print("\nIntroduceti id-ul cursului: ");
                                int ID = scanner.nextInt();
                                Course course2 = null;
                                for (Course course :
                                        ((Teacher) sesiune.getCurrentUser()).getCourses()) {
                                    if (course.getId() == ID) {
                                        course2 = course;
                                        break;
                                    }
                                }
                                System.out.println("\nAlegeti quizul: \n");
                                for (Quiz quiz :
                                        course2.getQuizzes()) {
                                    System.out.println("Id: " + quiz.getId() + " ---- Quiz: " + quiz.getQuizName());

                                }
                                System.out.print("\nIntroduceti id-ul quiz-ului: ");
                                int IDQuiz = scanner.nextInt();
                                for (Quiz quiz :
                                        course2.getQuizzes()) {
                                    if (quiz.getId() == IDQuiz) {
                                        sesiune.removeQuestions(quiz);
                                        break;
                                    }
                                }
                                audit.writeAudit("removeQuestion", LocalDateTime.now());
                                break;
                            case "9":
                                System.out.println("\nAlegeti cursul in cadrul caruia se gaseste quiz-ul: \n");
                                sesiune.showCourses(sesiune.getCurrentUser());
                                System.out.print("\nIntroduceti id-ul cursului: ");
                                int ID2 = scanner.nextInt();
                                Course course3 = null;
                                for (Course course :
                                        ((Teacher) sesiune.getCurrentUser()).getCourses()) {
                                    if (course.getId() == ID2) {
                                        course3 = course;
                                        break;
                                    }
                                }
                                System.out.println("\nAlegeti quizul: \n");
                                for (Quiz quiz :
                                        course3.getQuizzes()) {
                                    System.out.println("Id: " + quiz.getId() + " ---- Quiz: " + quiz.getQuizName());

                                }
                                System.out.print("\nIntroduceti id-ul quiz-ului: ");
                                int IDQuiz2 = scanner.nextInt();

                                for (Quiz quiz :
                                        course3.getQuizzes()) {
                                    if (quiz.getId() == IDQuiz2) {
                                        System.out.println("Raspunsuri date: ");
                                        for (Answer answer :
                                                quiz.getAnswers()) {
                                            System.out.println(answer);
                                        }

                                        System.out.print("Introduceti id-ul raspunsului pe care doriti sa il notati: ");
                                        int answerId = scanner.nextInt();
                                        for (Answer answer :
                                                quiz.getAnswers()) {
                                            if (answer.getId() == answerId) {
                                                if (quiz instanceof NormalQuiz) {
                                                    sesiune.scoreAnswer((NormalQuiz) quiz, answer, course3.getCourseName());
                                                } else {
                                                    System.out.println("Raspunsul a fost deja notat!");
                                                }
                                                break;
                                            }
                                        }

                                        break;
                                    }
                                }
                                audit.writeAudit("scoreAnswer", LocalDateTime.now());

                                break;
                            default:
                                System.out.println("Comanda necunoscuta");
                        }
                    } else {
                        System.out.println("Pentru a va inscrie la un anumit curs apasati tasta '5'");
                        System.out.println("Pentru a incepe un test apasati tasta '6'");
                        System.out.println("Pentru a va vizualiza notele apasati tasta '7'");

                        String option = scanner.next();
                        switch (option) {
                            case "1":
                                System.out.println(sesiune.getCurrentUser());
                                break;
                            case "2":
                                sesiune.logout();
                                audit.writeAudit("logout", LocalDateTime.now());
                                break;
                            case "3":
                                audit.writeAudit("exit", LocalDateTime.now());
                                break exit;
                            case "4":
                                sesiune.showCoursesByCategory();
                                audit.writeAudit("showCoursesByCategory", LocalDateTime.now());
                                break;
                            case "5":
                                System.out.println("\nCautati cursul la care doriti sa va inscrieti:");
                                sesiune.showCoursesByCategory();
                                System.out.print("\nIntroduceti id-ul cursului: ");
                                System.out.println(sesiune.joinCourse(scanner.nextInt()));
                                audit.writeAudit("joinCourse", LocalDateTime.now());
                                break;
                            case "6":
                                System.out.println("\nAlegeti cursul in cadrul caruia se gaseste quiz-ul: \n");
                                if (!((Student) sesiune.getCurrentUser()).getCourses().isEmpty()) {
                                    sesiune.showCourses(sesiune.getCurrentUser());
                                    System.out.print("\nIntroduceti id-ul cursului: ");
                                    int ID = scanner.nextInt();
                                    Course course2 = null;
                                    for (Course course :
                                            ((Student) sesiune.getCurrentUser()).getCourses()) {
                                        if (course.getId() == ID) {
                                            course2 = course;
                                            break;
                                        }
                                    }
                                    if (!course2.getQuizzes().isEmpty()) {
                                        System.out.println("\nAlegeti quizul: \n");
                                        for (Quiz quiz :
                                                course2.getQuizzes()) {
                                            System.out.println("Id: " + quiz.getId() + " ---- Quiz: " + quiz.getQuizName());

                                        }
                                        System.out.print("\nIntroduceti id-ul quiz-ului: ");
                                        int IDQuiz = scanner.nextInt();
                                        for (Quiz quiz :
                                                course2.getQuizzes()) {
                                            if (quiz.getId() == IDQuiz) {
                                                sesiune.takeQuiz(quiz, course2.getCourseName());
                                                break;
                                            }
                                        }
                                    } else {
                                        System.out.println("Cursul selectat nu are niciun quiz");
                                    }
                                } else {
                                    System.out.println("Nu sunteti inscris la niciun curs!");
                                }
                                audit.writeAudit("takeQuiz", LocalDateTime.now());
                                break;
                            case "7":
                                sesiune.showGrades();
                                audit.writeAudit("showGrades", LocalDateTime.now());
                                break;
                            default:
                                System.out.println("Comanda necunoscuta");
                        }
                    }

                }
            }
        }catch(IOException e){
            System.out.println(e);
        }

        sesiune.saveData();
    }
}
