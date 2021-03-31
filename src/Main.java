
import models.Course;
import models.Quiz;
import models.Teacher;
import services.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException {
        E_learningPlatform sesiune = E_learningPlatform.getInstance();
        Scanner scanner = new Scanner(System.in);

        exit:
        while(true) {
            while (sesiune.getCurrentUser() == null) {
                System.out.println("Pentru a va crea un cont nou apasati tasta '1' ");
                System.out.println("Pentru a va loga apasati tasta '2' ");
                System.out.println("Pentru a inchide sesiunea apasati tasta '3'");
                String option = scanner.next();

                switch (option) {
                    case "1":
                        sesiune.register();
                        break;
                    case "2":
                        sesiune.login();
                        break;
                    case "3":
                        break exit;
                    default:
                        System.out.println("Comanda necunoscuta");
                }

            }
            while(sesiune.getCurrentUser() != null){

                System.out.println("Pentru a afisa informatiile dumneavoastra apasati tasta '1' ");
                System.out.println("Pentru a va deloga apasati tasta '2' ");
                System.out.println("Pentru a inchide sesiunea apasati tasta '3'");
                System.out.println("Pentru a afisa cusurile apasati tasta '4'");


                if (sesiune.getCurrentUser() instanceof Teacher){
                    System.out.println("Pentru a crea un curs nou apasati tasta '5'");
                    System.out.println("Pentru a crea un quiz nou apasati tasta '6'");
                    System.out.println("Pentru a adauga o intrebare la un quiz apasati tasta '7'");
                    System.out.println("Pentru a sterge una sau mai multe intrebari dintr-un quiz apasati tasta '8'");

                    String option = scanner.next();
                    switch (option) {
                        case "1":
                            System.out.println(sesiune.getCurrentUser());
                            break ;
                        case "2":
                            sesiune.logout();
                            break;
                        case "3":
                            break exit;
                        case "4":
                            sesiune.showCoursesByCategory();
                            break ;
                        case "5":
                            sesiune.createCourse();
                            break;
                        case "6":
                            System.out.println("\nAlegeti cursul la care doriti sa adaugati un quiz nou: \n");
                            sesiune.showCourses(sesiune.getCurrentUser());
                            System.out.print("\nIntroduceti id-ul cursului: ");
                            int id = scanner.nextInt();
                            Course c = null;
                            for (Course course:
                                 ((Teacher) sesiune.getCurrentUser()).getCourses()) {
                                if (course.getId() == id){
                                    c = course;
                                    break ;
                                }
                            }
                            sesiune.addQuiz(c);
                            break;
                        case "7":
                            System.out.println("\nAlegeti cursul in cadrul caruia se gaseste quiz-ul: \n");
                            sesiune.showCourses(sesiune.getCurrentUser());
                            System.out.print("\nIntroduceti id-ul cursului: ");
                            int idCourse = scanner.nextInt();
                            Course course1 = null;
                            for (Course course:
                                    ((Teacher) sesiune.getCurrentUser()).getCourses()) {
                                if (course.getId() == idCourse){
                                    course1 = course;
                                    break ;
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
                                if (quiz.getId() == idQuiz){
                                    sesiune.addQuestion(quiz);
                                    break;
                                }
                            }

                            break ;
                        case "8":
                            System.out.println("\nAlegeti cursul in cadrul caruia se gaseste quiz-ul: \n");
                            sesiune.showCourses(sesiune.getCurrentUser());
                            System.out.print("\nIntroduceti id-ul cursului: ");
                            int ID = scanner.nextInt();
                            Course course2 = null;
                            for (Course course:
                                    ((Teacher) sesiune.getCurrentUser()).getCourses()) {
                                if (course.getId() == ID){
                                    course2 = course;
                                    break ;
                                }
                            }
                            System.out.println("\nAlegeti quizul la care doriti sa adaugati o intrebare noua: \n");
                            for (Quiz quiz :
                                    course2.getQuizzes()) {
                                System.out.println("Id: " + quiz.getId() + " ---- Quiz: " + quiz.getQuizName());

                            }
                            System.out.print("\nIntroduceti id-ul quiz-ului: ");
                            int IDQuiz = scanner.nextInt();
                            for (Quiz quiz :
                                    course2.getQuizzes()) {
                                if (quiz.getId() == IDQuiz){
                                    sesiune.removeQuestions(quiz);
                                    break;
                                }
                            }

                            break ;
                        default:
                            System.out.println("Comanda necunoscuta");
                    }
                }
                else{
                    System.out.println("Pentru a va inscrie la un anumit curs apasati tasta '5'");

                    String option = scanner.next();
                    switch (option) {
                        case "1":
                            System.out.println(sesiune.getCurrentUser());
                            break ;
                        case "2":
                            sesiune.logout();
                            break;
                        case "3":
                            break exit;
                        case "4":
                            sesiune.showCoursesByCategory();
                            break ;
                        case "5":
                            System.out.println("\nCautati cursul la care doriti sa va inscrieti:");
                            sesiune.showCoursesByCategory();
                            System.out.print("\nIntroduceti id-ul cursului: ");
                            System.out.println(sesiune.joinCourse(scanner.nextInt()));
                            break ;
                        default:
                            System.out.println("Comanda necunoscuta");
                    }
                }

            }
        }
    }
}
