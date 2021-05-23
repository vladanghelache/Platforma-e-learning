
import models.*;

import repositories.*;
import services.*;
import java.text.ParseException;

import java.time.LocalDateTime;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws ParseException {
        E_learningPlatform sesiune = E_learningPlatform.getInstance();
        repositories.Audit audit = new repositories.Audit();
        Scanner scanner = new Scanner(System.in);
        exit:
        while (true) {
            while (sesiune.getCurrentUser() == null) {
                try {
                    System.out.println("Pentru a va crea un cont nou apasati tasta '1' ");
                    System.out.println("Pentru a va loga apasati tasta '2' ");
                    System.out.println("Pentru a inchide sesiunea apasati tasta '3'");
                    String option = scanner.next();

                    switch (option) {
                        case "1":
                            sesiune.register();
                            audit.insert("register", LocalDateTime.now());
                            break;
                        case "2":
                            sesiune.login();
                            audit.insert("login", LocalDateTime.now());
                            break;
                        case "3":
                            audit.insert("exit", LocalDateTime.now());
                            break exit;
                        default:
                            System.out.println("Comanda necunoscuta");
                    }
                }
                catch (InputMismatchException e){
                    System.out.println("Operatie esuata! Incercati din nou");
                }

            }
            while (sesiune.getCurrentUser() != null) {
                try {
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
                                audit.insert("logout", LocalDateTime.now());
                                break;
                            case "3":
                                audit.insert("exit", LocalDateTime.now());
                                break exit;
                            case "4":
                                audit.insert("showCoursesByCategory", LocalDateTime.now());
                                sesiune.showCoursesByCategory();
                                break;
                            case "5":

                                sesiune.createCourse();
                                audit.insert("createCourse", LocalDateTime.now());
                                break;
                            case "6":
                                System.out.println("\nAlegeti cursul la care doriti sa adaugati un quiz nou: \n");
                                sesiune.showCourses(sesiune.getCurrentUser());
                                System.out.print("\nIntroduceti id-ul cursului: ");
                                int id = scanner.nextInt();
                                Course c = null;
                                for (Course course :
                                        new Teachers().getCourses(sesiune.getCurrentUser().getId())) {
                                    if (course.getId() == id) {
                                        c = course;
                                        break;
                                    }
                                }
                                sesiune.addQuiz(c);
                                audit.insert("addQuiz", LocalDateTime.now());
                                break;
                            case "7":
                                System.out.println("\nAlegeti cursul in cadrul caruia se gaseste quiz-ul: \n");
                                sesiune.showCourses(sesiune.getCurrentUser());
                                System.out.print("\nIntroduceti id-ul cursului: ");
                                int idCourse = scanner.nextInt();

                                Optional<Course> c1 = new Courses().getById(idCourse);
                                Course course1 = null;
                                if (c1.isPresent() && c1.get().getTeacher().getId() == sesiune.getCurrentUser().getId()) {
                                    course1 = c1.get();

                                    System.out.println("\nAlegeti quizul la care doriti sa adaugati o intrebare noua: \n");

                                    for (Quiz quiz :
                                            new Courses().getQuizzes(course1.getId())) {
                                        System.out.println("Id: " + quiz.getId() + " ---- Quiz: " + quiz.getQuizName());

                                    }
                                    System.out.print("\nIntroduceti id-ul quiz-ului: ");
                                    int idQuiz = scanner.nextInt();
                                    Optional<Quiz> q = new Quizzes().getById(idQuiz);
                                    if (q.isPresent() && q.get().getCourse().getId() == course1.getId()) {
                                        sesiune.addQuestion(q.get());
                                    } else {
                                        System.out.println("Quiz-ul nu a fost gasit!");
                                    }
                                } else {
                                    System.out.println("Cursul nu a fost gasit!");
                                }


                                audit.insert("addQuestion", LocalDateTime.now());
                                break;
                            case "8":
                                System.out.println("\nAlegeti cursul in cadrul caruia se gaseste quiz-ul: \n");
                                sesiune.showCourses(sesiune.getCurrentUser());
                                System.out.print("\nIntroduceti id-ul cursului: ");
                                int ID = scanner.nextInt();
                                Optional<Course> c2 = new Courses().getById(ID);
                                Course course2 = null;
                                if (c2.isPresent() && c2.get().getTeacher().getId() == sesiune.getCurrentUser().getId()) {
                                    course2 = c2.get();

                                    System.out.println("\nAlegeti quizul pentru care doriti sa stergeti intrebari: \n");

                                    for (Quiz quiz :
                                            new Courses().getQuizzes(course2.getId())) {
                                        System.out.println("Id: " + quiz.getId() + " ---- Quiz: " + quiz.getQuizName());

                                    }
                                    System.out.print("\nIntroduceti id-ul quiz-ului: ");
                                    int idQuiz = scanner.nextInt();
                                    Optional<Quiz> q = new Quizzes().getById(idQuiz);
                                    if (q.isPresent() && q.get().getCourse().getId() == course2.getId()) {
                                        sesiune.removeQuestions(q.get());
                                    } else {
                                        System.out.println("Quiz-ul nu a fost gasit!");
                                    }
                                } else {
                                    System.out.println("Cursul nu a fost gasit!");
                                }
                                audit.insert("removeQuestion", LocalDateTime.now());
                                break;
                            case "9":
                                System.out.println("\nAlegeti cursul in cadrul caruia se gaseste quiz-ul: \n");
                                sesiune.showCourses(sesiune.getCurrentUser());
                                System.out.print("\nIntroduceti id-ul cursului: ");
                                int ID2 = scanner.nextInt();
                                Optional<Course> c3 = new Courses().getById(ID2);
                                Course course3 = null;
                                if (c3.isPresent() && c3.get().getTeacher().getId() == sesiune.getCurrentUser().getId()) {
                                    course3 = c3.get();

                                    System.out.println("\nAlegeti quizul: \n");

                                    for (Quiz quiz :
                                            new Courses().getQuizzes(course3.getId())) {
                                        System.out.println("Id: " + quiz.getId() + " ---- Quiz: " + quiz.getQuizName());

                                    }
                                    System.out.print("\nIntroduceti id-ul quiz-ului: ");
                                    int idQuiz = scanner.nextInt();
                                    Optional<Quiz> q = new Quizzes().getById(idQuiz);
                                    if (q.isPresent() && q.get().getCourse().getId() == course3.getId()) {
                                        System.out.println("Raspunsuri date: ");
                                        Set<Answer> ans = new Quizzes().getAnswers(idQuiz).stream().filter(a -> a.getGrade() == -1).collect(Collectors.toSet());
                                        for (Answer answer :
                                                ans) {
                                            System.out.println(answer);
                                        }

                                        System.out.print("Introduceti id-ul raspunsului pe care doriti sa il notati: ");
                                        int answerId = scanner.nextInt();
                                        for (Answer answer :
                                                ans) {
                                            if (answer.getId() == answerId) {
                                                if (q.get() instanceof NormalQuiz) {
                                                    sesiune.scoreAnswer((NormalQuiz) q.get(), answer, course3.getCourseName());
                                                }
                                                break;
                                            }
                                        }

                                    } else {
                                        System.out.println("Quiz-ul nu a fost gasit!");
                                    }
                                } else {
                                    System.out.println("Cursul nu a fost gasit!");
                                }

                                audit.insert("scoreAnswer", LocalDateTime.now());

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
                            case "1" -> System.out.println(sesiune.getCurrentUser());
                            case "2" -> {
                                sesiune.logout();
                                audit.insert("logout", LocalDateTime.now());
                            }
                            case "3" -> {
                                audit.insert("exit", LocalDateTime.now());
                                break exit;
                            }
                            case "4" -> {
                                sesiune.showCoursesByCategory();
                                audit.insert("showCoursesByCategory", LocalDateTime.now());
                            }
                            case "5" -> {
                                System.out.println("\nCautati cursul la care doriti sa va inscrieti:");
                                sesiune.showCoursesByCategory();
                                System.out.print("\nIntroduceti id-ul cursului: ");
                                System.out.println(sesiune.joinCourse(scanner.nextInt()));
                                audit.insert("joinCourse", LocalDateTime.now());
                            }
                            case "6" -> {
                                System.out.println("\nAlegeti cursul in cadrul caruia se gaseste quiz-ul: \n");
                                if (!(new Students().getCourses( sesiune.getCurrentUser().getId())).isEmpty()) {
                                    sesiune.showCourses(sesiune.getCurrentUser());
                                    System.out.print("\nIntroduceti id-ul cursului: ");
                                    int ID = scanner.nextInt();
                                    Course course2 = null;
                                    for (Course course :
                                            (new Students().getCourses(sesiune.getCurrentUser().getId()))) {
                                        if (course.getId() == ID) {
                                            course2 = course;
                                            break;
                                        }
                                    }
                                    if (course2 != null) {
                                        Set<Quiz> quizzes = new Courses().getQuizzes(course2.getId());
                                        if (!quizzes.isEmpty()) {
                                            System.out.println("\nAlegeti quizul: \n");
                                            for (Quiz quiz :
                                                    quizzes) {
                                                System.out.println("Id: " + quiz.getId() + " ---- Quiz: " + quiz.getQuizName());

                                            }
                                            System.out.print("\nIntroduceti id-ul quiz-ului: ");
                                            int IDQuiz = scanner.nextInt();
                                            for (Quiz quiz :
                                                    quizzes) {
                                                if (quiz.getId() == IDQuiz) {
                                                    sesiune.takeQuiz(quiz, course2.getCourseName());
                                                    break;
                                                }
                                            }
                                        } else {
                                            System.out.println("Cursul selectat nu are niciun quiz");
                                        }
                                    } else {
                                        System.out.println("Cursul n");
                                    }
                                } else {
                                    System.out.println("Nu sunteti inscris la niciun curs!");
                                }
                                audit.insert("takeQuiz", LocalDateTime.now());
                            }
                            case "7" -> {
                                sesiune.showGrades();
                                audit.insert("showGrades", LocalDateTime.now());
                            }
                            default -> System.out.println("Comanda necunoscuta");
                        }
                    }
                }
                catch (InputMismatchException e){
                    System.out.println("Operatie esuata! Incercati din nou");
                }

            }
        }


    }
}
