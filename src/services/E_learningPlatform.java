package services;

import models.*;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class E_learningPlatform {

    private static E_learningPlatform instance;
    private static Set<Course> Courses;
    private static Set<User> Users;
    private User currentUser;

    static {
        Courses = new HashSet<>();
        Users = new HashSet<>();
    }
    private E_learningPlatform() throws ParseException {
        System.out.print("Bine ati venit!\n");
        addExamples();
    }

    public static E_learningPlatform getInstance() throws ParseException {
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

                if (info.getErrorMessage().equals("Registration completed\n")) {
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

    public void createCourse() throws ParseException {
        if (currentUser instanceof Teacher){
            Scanner scanner =  new Scanner(System.in);
            Course newCourse = new Course();
            boolean[] valid = {false,false,false};
            while (true){
                if (!valid[0]){
                    System.out.print("Introduceti numele cursului: ");
                    newCourse.setCourseName(scanner.nextLine());
                }
                if (!valid[1]){
                    DateValidator dateValidator = new DateValidator();
                    String dateString = "";
                    while(!dateValidator.validateDate(dateString)){
                        System.out.print("Introuceti data de inceput (\"yyyy-MM-dd\"):  ");
                        dateString = scanner.next();
                    }


                    newCourse.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateString));
                }
                if(!valid[2]){
                    DateValidator dateValidator = new DateValidator();
                    String dateString = "";
                    while(!dateValidator.validateDate(dateString)){
                        System.out.print("Introuceti data de sfarsit (\"yyyy-MM-dd\"): ");
                        dateString = scanner.next();
                    }


                    newCourse.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateString));
                }

                ValidationInfo info = new CourseValidator().validateWithErrorMessage(newCourse);
                if (info.getErrorMessage().equals("Cursul a fost adaugat cu succes\n")){
                    List categories = Arrays.asList(Category.values());
                    while(true){
                        System.out.println("Selectati categoaria:");
                        for (int i = 0; i < categories.size() ; i++ ){
                            System.out.println(i +" - "+ categories.get(i).toString());
                        }

                        int index = scanner.nextInt();
                        if(index < categories.size() && index >= 0){
                            newCourse.setCategory((Category) categories.get(index));
                            break;
                        }
                    }
                    newCourse.setTeacher((Teacher) currentUser);
                    ((Teacher) currentUser).addCourse(newCourse);
                    Courses.add(newCourse);
                    break;
                }
                else{
                    valid = info.getValid();
                }
                System.out.println(info.getErrorMessage());
            }

        }
    }

    public void showCoursesByCategory(){
        if (currentUser != null){
            List categories = Arrays.asList(Category.values());
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Selectati categoaria:");
                for (int i = 0; i < categories.size(); i++) {
                    System.out.println(i + " - " + categories.get(i).toString());
                }
                int index = scanner.nextInt();
                if (index < categories.size() && index >= 0) {
                    for (Course course:
                         Courses) {
                        if (course.getCategory() == categories.get(index)){
                            System.out.println(course);
                        }
                    }
                    break;
                }
            }

        }

    }

    public String joinCourse(int courseId){
        if (currentUser instanceof Student) {
            boolean found = false;
            for (Course course :
                    Courses) {
                if (course.getId() == courseId) {
                    found = true;
                    course.addStudent((Student) currentUser);
                    ((Student) currentUser).addCourse(course);
                    break;
                }
            }
            if (found){
                return "Inscrierea s-a efectuat cu succes!";
            }
            else {
                return  "Inscrierea a esuat! Nu a fost gasit niciun curs cu id-ul " + courseId;
            }
        }
        return "Numai studentii se pot inscrie la un curs!";
    }

    public void showCourses(User user){
        if(user instanceof Teacher){
            Set<Course> courses =  ((Teacher) user).getCourses();
            for (Course course :
                    courses) {
                System.out.println(course);
            }
        }
        else{
            Set<Course> courses =  ((Student) user).getCourses();
            for (Course course :
                    courses) {
                System.out.println(course);
            }
        }
    }

    public void addQuiz(Course course){
        if (currentUser instanceof Teacher && currentUser == course.getTeacher()){
            Scanner scanner = new Scanner(System.in);
            System.out.print("Pentru a crea un quiz 'AutoScored' apasati tasta 'A'\nPentru a crea un quiz normal apasati tasta 'N': ");
            String option = scanner.next();
            while (!option.equalsIgnoreCase("a") && !option.equalsIgnoreCase("n")) {
                System.out.println("Comanda necunoascuta! Incercati din nou: ");
                option = scanner.next();
            }
            Quiz newQuiz = AorN(option);

            scanner.nextLine();
            System.out.print("Intrduceti un titlu pentru noul quiz: ");
            newQuiz.setQuizName(scanner.nextLine());

            System.out.print("Intrduceti numarul de intrebari pentru noul quiz: ");
            int n = scanner.nextInt();
            newQuiz.setNrQuestions(n);
            if (newQuiz instanceof AutoScored) {
                List<MCQ> questions = new ArrayList<>();
                int totalPoints = 0;
                for (int i = 0; i < n; i++) {
                    MCQ question = (MCQ) makeQuestion("1");

                    System.out.println("Introduceti raspunsul corect: ");
                    question.setCorectAnswer(scanner.next().charAt(0));
                    questions.add(question);
                    totalPoints += question.getPoints();
                }
                ((AutoScored) newQuiz).setMCQs(questions);
                 newQuiz.setTotalPoints(totalPoints);
            }
            else{
                List<Question> questions = new ArrayList<>();
                int totalPoints = 0;
                for (int i = 0; i < n; i++) {
                    System.out.print("Pentru a crea MCQ apasati tasta '1'\nPentru a crea OEQ apasati tasta '2'\n Pentru a crea MP apasati tasta '3':\n ");
                    String op = scanner.next();
                    while(!op.equals("1") && !op.equals("2") && !op.equals("3")){
                        System.out.println("Comanda necunoscuta! Incercati din nou: ");
                        op = scanner.next();
                    }
                    questions.add(makeQuestion(op));
                    totalPoints += questions.get(i).getPoints();
                }

                ((NormalQuiz) newQuiz).setQuestions(questions);
                newQuiz.setTotalPoints(totalPoints);

            }
            newQuiz.setTeacher((Teacher) currentUser);
            course.addQuiz(newQuiz);
        }
    }

    public void addQuestion(Quiz quiz){
        if (currentUser instanceof Teacher && currentUser == quiz.getTeacher()) {
            Scanner scanner = new Scanner(System.in);
            if (quiz instanceof AutoScored) {
                int totalPoints = quiz.getTotalPoints();
                MCQ question = (MCQ) makeQuestion("1");

                System.out.println("Introduceti raspunsul corect: ");
                question.setCorectAnswer(scanner.next().charAt(0));
                totalPoints += question.getPoints();

                ((AutoScored) quiz).addQuestion(question);
                quiz.setTotalPoints(totalPoints);
            } else {

                int totalPoints = quiz.getTotalPoints();

                System.out.print("Pentru a crea MCQ apasati tasta '1'\nPentru a crea OEQ apasati tasta '2'\n Pentru a crea MP apasati tasta '3':\n ");
                String op = scanner.next();
                while (!op.equals("1") && !op.equals("2") && !op.equals("3")) {
                    System.out.println("Comanda necunoscuta! Incercati din nou: ");
                    op = scanner.next();
                }
                Question question = makeQuestion(op);
                totalPoints += question.getPoints();


                ((NormalQuiz) quiz).addQuestion(question);
                quiz.setTotalPoints(totalPoints);

            }
        }
    }

    public void removeQuestions(Quiz quiz){
        if(currentUser instanceof Teacher && currentUser == quiz.getTeacher()){
            Scanner scanner = new Scanner(System.in);
            if (quiz instanceof AutoScored) {
                List<MCQ> questions = new ArrayList<>(((AutoScored) quiz).getMCQs());
                for (MCQ question :
                        questions) {
                    System.out.println(question);

                    System.out.print("Doriti sa stergeti aceasta intrebare? ('D' = Da/ 'N' = Nu): ");
                    String option = scanner.next();
                    while(!option.equalsIgnoreCase("d") && !option.equalsIgnoreCase("n")){
                        System.out.print("Comanda necunoscuta! Incercati din nou: ");
                        option = scanner.next();
                    }
                    if (option.equalsIgnoreCase("d")){
                        ((AutoScored) quiz).removeQuestion(question);
                    }

                }
            }
            else{
                List<Question> questions = new ArrayList<Question>(((NormalQuiz) quiz).getQuestions());
                for (Question question :
                        questions) {
                    System.out.println(question);

                    System.out.print("Doriti sa stergeti aceasta intrebare? ('D' = Da/ 'N' = Nu): ");
                    String option = scanner.next();
                    while(!option.equalsIgnoreCase("d") && !option.equalsIgnoreCase("n")){
                        System.out.print("Comanda necunoscuta! Incercati din nou: ");
                        option = scanner.next();
                    }
                    if (option.equalsIgnoreCase("d")){
                        ((NormalQuiz) quiz).removeQuestion(question);
                    }

                }
            }
        }
    }

    public void takeQuiz(Quiz quiz, String courseName){
        if (currentUser instanceof Student){
            Scanner scanner = new Scanner(System.in);
            if (quiz instanceof AutoScored){
                List<MCQ> questions = ((AutoScored) quiz).getMCQs();
                List<String> answer = new ArrayList<>();
                System.out.println("Acesta este un test grila. Fiecare intrebare are un singur raspuns corect.\nScrieti numai litera corespunzatoare raspunsului ales.");

                for (int i = 0; i < questions.size(); i++) {
                    System.out.println((i+1) + ". " + questions.get(i));
                    System.out.println("Raspunsul dumneavoastra: ");
                    String a = scanner.nextLine();
                    answer.add(a);

                }

                Answer a = new Answer((Student) currentUser, answer);
                quiz.addAnswer(a);
                scoreAnswer((AutoScored) quiz,a,courseName);

            }
            else{
                List<Question> questions = ((NormalQuiz) quiz).getQuestions();
                List<String> answer = new ArrayList<>();
                System.out.println("Testul contine diferite tiputi de intrebari.");

                for (int i = 0; i < questions.size(); i++) {
                    System.out.println((i+1) + ". " + questions.get(i));
                    System.out.println("Raspunsul dumneavoastra: ");
                    String a = scanner.nextLine();
                    answer.add(a);

                }

                quiz.addAnswer(new Answer((Student) currentUser, answer));
            }
        }
    }

    public void scoreAnswer(AutoScored quiz, Answer answer, String courseName){
        List<MCQ> questions = quiz.getMCQs();
        List<String> answers = answer.getAnswer();
        int grade = 0;
        for (int i = 0; i < questions.size(); i++) {
            String corect = new String(String.valueOf(questions.get(i).getCorectAnswer()));
            if(answers.get(i).equals(corect)){
                grade += questions.get(i).getPoints();
            }
        }
        answer.getStudent().addGrade(courseName+": "+quiz.getQuizName() + " -- Nota maxima posibila: "+quiz.getTotalPoints(),grade);

    }

    public void scoreAnswer(NormalQuiz quiz, Answer answer, String courseName){
        if (currentUser instanceof Teacher && quiz.getTeacher()==currentUser) {
            Scanner scanner = new Scanner(System.in);
            List<Question> questions = quiz.getQuestions();
            List<String> answers = answer.getAnswer();
            int grade = 0;
            for (int i = 0; i < questions.size(); i++) {
                System.out.println((i+1)+". "+questions.get(i)+" \nRaspuns:\n"+ answers.get(i));
                System.out.print("Introduceti punctajul acordat: ");
                int p = scanner.nextInt();
                while(p>questions.get(i).getPoints()){
                    System.out.println("Punctaj incorect.Incercati din nou");
                    p = scanner.nextInt();
                }
                grade += p;

            }
            answer.getStudent().addGrade(courseName + ": " + quiz.getQuizName() + " -- Nota maxima posibila: "+quiz.getTotalPoints(), grade);
        }
    }

    public void showGrades(){
        if (currentUser instanceof Student){
            Map<String,Integer> map = ((Student) currentUser).getGrades();
            for (Map.Entry<String, Integer> entry :
                    map.entrySet()) {
                System.out.println(entry.getKey()+" -- Nota obtinuta: " + entry.getValue());
            }            

        }
    }



    public User getCurrentUser() {
        return currentUser;
    }

    private Question makeQuestion(String option){
        Question question = questionType(option);
        Scanner scanner = new Scanner(System.in);
        if (!option.equals("3")) {
            System.out.println("Introduceti cerinta intrebarii: ");
            question.setQuestion(scanner.nextLine());
        }

        System.out.println("Introduceti punctajul intrebarii: ");
        question.setPoints(scanner.nextInt());

        switch (option){
            case "1":
                System.out.println("Introduceti numarul de raspunsuri: ");
                int nr = scanner.nextInt();
                ((MCQ) question).setNrChoices(nr);
                scanner.nextLine();
                Map<Character, String> choices = new HashMap<>();
                for (int j = 0; j < nr; j++) {
                    System.out.println("Introduceti raspunsul: ");
                    String r =  scanner.nextLine();
                    Character c = (char) ('a' + j);
                    choices.put(c ,r);
                }

                ((MCQ) question).setChoices(choices);
                break;
            case "2":
                scanner.nextLine();
                System.out.println("Introduceti observatii legate de aceasta intrebare: ");
                ((OEQ) question).setTips(scanner.nextLine());
                break;
            case "3":
                System.out.println("Introduceti nr de elemente din coloana A: ");
                int nA = scanner.nextInt();

                Map<Character, String> colA = new HashMap<>();
                scanner.nextLine();
                for (int i = 0; i < nA; i++) {
                    System.out.println("Introduceti urmatorul element in coloana A: ");
                    String v = scanner.nextLine();
                    Character k = (char) ('a' + i);

                    colA.put(k,v);
                }

                ((MP) question).setColumnA(colA);

                System.out.println("Introduceti nr de elemente din coloana B: ");
                int nB = scanner.nextInt();

                Map<Integer, String> colB = new HashMap<>();
                scanner.nextLine();
                for (int i = 0; i < nB; i++) {
                    System.out.println("Introduceti urmatorul element in coloana B: ");
                    String v = scanner.nextLine();
                    Integer k = i;

                    colB.put(k,v);
                }

                ((MP) question).setColumnB(colB);
                break;
        }

        return question;

    }
    private User TorS(String option){
        if(option.toLowerCase().equals("s")){
            return new Student();
        }
        else {
            return new Teacher();
        }
    }
    private Quiz AorN(String option){
        if(option.toLowerCase().equals("a")){
            return new AutoScored();
        }
        else {
            return new NormalQuiz();
        }
    }

    private Question questionType(String option){
        if(option.toLowerCase().equals("1")){
            return new MCQ();
        }
        else {
            if(option.toLowerCase().equals("2")){
                return new OEQ();
            }
            else {
                return new MP();
            }
        }
    }

    private void addExamples() throws ParseException {
        User teacher = new Teacher("Igor", "Stravinski","parola123#","profesor@email.com","Doctorat in muzica");
        User student = new Student("Alex", "Alexandrescu","parola123#","student@email.com",20);
        User student2 = new Student("Paul", "Paulescu","parola123#","paul@email.com",22);
        Course course1 = new Course("The Rite of Spring", (Teacher) teacher,Category.Music, new SimpleDateFormat("yyyy-MM-dd").parse("2021-04-04"),new SimpleDateFormat("yyyy-MM-dd").parse("2021-07-04"));
        Courses.add(course1);
        Courses.add(new Course("Neoclasicism", (Teacher) teacher,Category.Music, new SimpleDateFormat("yyyy-MM-dd").parse("2021-04-04"),new SimpleDateFormat("yyyy-MM-dd").parse("2021-07-04")));
        Courses.add(new Course("Pyramid scheme", (Teacher) teacher,Category.Marketing, new SimpleDateFormat("yyyy-MM-dd").parse("2021-04-04"),new SimpleDateFormat("yyyy-MM-dd").parse("2021-07-04")));
        for (Course course :
                Courses) {
            ((Teacher) teacher).addCourse(course);
        }
        Users.add(teacher);
        Users.add(student);
        Users.add(student2);
        Map<Character,String> choices= new HashMap<>();
        choices.put('a',"raspuns 1");
        choices.put('b',"raspuns 2");
        MCQ q1 = new MCQ("Intrebare 1", 4, 2,choices,'a');
        MCQ q2 = new MCQ("Intrebare 2", 4, 2,choices,'b');
        List<MCQ> l = new ArrayList<>();
        l.add(q1);
        l.add(q2);
        AutoScored quiz = new AutoScored("test",2, (Teacher) teacher,l);
        course1.addQuiz(quiz);

    }

}


