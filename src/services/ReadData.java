package services;

import models.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;


public class ReadData {
    private static ReadData instance;


    private ReadData(){

    }

    public static ReadData getInstance(){
        if (instance ==  null){
            instance = new ReadData();
        }

        return instance;
    }

    public void readData(Map<Integer, User> Users,
                         Map<Integer, Course> Courses,
                         Map<Integer, Quiz> Quizzes,
                         Map<Integer, Question> Questions,
                         Map<Integer, Answer> Ansewrs){

        try{
            
            readTeachers(Users);
            readMCQs(Questions);
            readOEQs(Questions);
            readMPs(Questions);
            Map<Integer,Integer> Students = readAnswers(Ansewrs);
            readAutoScored(Quizzes, Questions, Ansewrs, Users);
            readNormal(Quizzes, Questions, Ansewrs, Users);
            readCourses(Courses,Users,Quizzes);
            readStudents(Users, Courses);

            for (Map.Entry<Integer, Integer> entry :
                Students.entrySet()){

                Ansewrs.get(entry.getKey()).setStudent((Student) Users.get(entry.getValue()));

            }

        }catch (FileNotFoundException | ParseException e){
            System.out.println("Eroare: Citirea datelor a esuat!\n" + e);
        }

    }

    private void readTeachers(Map<Integer, User> Users) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("Data/Teachers.csv"));
        scanner.nextLine();
        scanner.useDelimiter("\n");


        while (scanner.hasNext()){
            String line = scanner.next();
            String[] attributes = line.split(",");

            Teacher teacher = new Teacher();

            teacher.setId(Integer.parseInt(attributes[0]));
            teacher.setFirstName(attributes[1]);
            teacher.setLastName(attributes[2]);
            teacher.setPassword(attributes[3]);
            teacher.setEmail(attributes[4]);
            teacher.setQualification(attributes[5]);



            Users.put(teacher.getId(), teacher);


        }
        scanner.close();
    }

    private void readStudents(Map<Integer, User> Users, Map<Integer, Course> Courses) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("Data/Students.csv"));
        scanner.nextLine();
        scanner.useDelimiter("\n");


        while (scanner.hasNext()){
            String line = scanner.next();
            String[] attributes = line.split(",");

            Student student = new Student();

            student.setId(Integer.parseInt(attributes[0]));
            student.setFirstName(attributes[1]);
            student.setLastName(attributes[2]);
            student.setPassword(attributes[3]);
            student.setEmail(attributes[4]);
            student.setAge(Integer.parseInt(attributes[5].strip()));

            try {
                int[] courseIds = Arrays.stream(attributes[6].split(" ")).mapToInt(Integer::parseInt).toArray();

                for (int id :
                        courseIds) {
                    if (Courses.containsKey(id)) {
                        student.addCourse(Courses.get(id));
                        Courses.get(id).addStudent(student);
                    }

                }
            } catch(Exception e){

            }

            try {
                String[] grades = attributes[7].split("~");

                for (String grade : grades) {

                    String[] gr = grade.split("/");

                    System.out.println();

                    student.addGrade(gr[0], Integer.parseInt(gr[1].strip()));


                }
            } catch(Exception e){

            }


            Users.put(student.getId(), student);

        }
        scanner.close();
    }

    private void readCourses(Map<Integer, Course> Courses, Map<Integer, User> Users, Map<Integer, Quiz> Quizzes) throws FileNotFoundException, ParseException {

        Scanner scanner = new Scanner(new File("Data/Courses.csv"));
        scanner.nextLine();
        scanner.useDelimiter("\n");


        while (scanner.hasNext()) {
            String line = scanner.next();
            String[] attributes = line.split(",");

            Course course = new Course();

            course.setId(Integer.parseInt(attributes[0]));
            course.setCourseName(attributes[1]);
            int teacherId = Integer.parseInt(attributes[2]);
            if (Users.containsKey(teacherId)) {
                course.setTeacher((Teacher) Users.get(teacherId));
                ((Teacher) Users.get(teacherId)).addCourse(course);
            }

            course.setCategory(Category.valueOf(attributes[3]));
            course.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(attributes[4]));
            course.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(attributes[5]));

            try {
                int[] quizIds = Arrays.stream(attributes[6].split(" ")).mapToInt(Integer::parseInt).toArray();

                for (int id :
                        quizIds) {
                    if (Quizzes.containsKey(id)) {
                        course.addQuiz(Quizzes.get(id));

                    }

                }
            } catch(Exception e){

            }

            Courses.put(course.getId(), course);


        }
        scanner.close();
    }

    private void readAutoScored(Map<Integer, Quiz> Quizzes, Map<Integer, Question> Questions, Map<Integer, Answer> Answers, Map<Integer, User> Users) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("Data/AutoScored.csv"));
        scanner.nextLine();
        scanner.useDelimiter("\n");


        while (scanner.hasNext()) {
            String line = scanner.next();
            String[] attributes = line.split(",");

            AutoScored autoScored = new AutoScored();

            autoScored.setId(Integer.parseInt(attributes[0]));
            autoScored.setQuizName(attributes[1]);
            autoScored.setNrQuestions(Integer.parseInt(attributes[2]));

            int[] answerIds = Arrays.stream(attributes[3].split(" ")).mapToInt(Integer::parseInt).toArray();

            for (int id :
                    answerIds) {
                if(Answers.containsKey(id)) {
                    autoScored.addAnswer(Answers.get(id));

                }

            }


            autoScored.setTotalPoints(Integer.parseInt(attributes[4]));

            int teacherId = Integer.parseInt(attributes[5]);

            if(Users.containsKey(teacherId)){
                autoScored.setTeacher((Teacher) Users.get(teacherId));
            }

            int[] mcqIds = Arrays.stream(attributes[6].split(" ")).mapToInt(Integer::parseInt).toArray();

            for (int id :
                    mcqIds) {
                if(Questions.containsKey(id)) {
                    autoScored.addQuestion((MCQ) Questions.get(id));

                }

            }

            Quizzes.put(autoScored.getId(), autoScored);

        }
        scanner.close();
    }

    private void readNormal(Map<Integer, Quiz> Quizzes, Map<Integer, Question> Questions, Map<Integer, Answer> Answers, Map<Integer, User> Users) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("Data/Normal.csv"));
        scanner.nextLine();
        scanner.useDelimiter("\n");


        while (scanner.hasNext()) {
            String line = scanner.next();
            String[] attributes = line.split(",");

            NormalQuiz normalQuiz = new NormalQuiz();

            normalQuiz.setId(Integer.parseInt(attributes[0]));
            normalQuiz.setQuizName(attributes[1]);
            normalQuiz.setNrQuestions(Integer.parseInt(attributes[2]));

            int[] answerIds = Arrays.stream(attributes[3].split(" ")).mapToInt(Integer::parseInt).toArray();

            for (int id :
                    answerIds) {
                if(Answers.containsKey(id)) {
                    normalQuiz.addAnswer(Answers.get(id));

                }

            }


            normalQuiz.setTotalPoints(Integer.parseInt(attributes[4]));

            int teacherId = Integer.parseInt(attributes[5]);

            if(Users.containsKey(teacherId)){
                normalQuiz.setTeacher((Teacher) Users.get(teacherId));
            }

            int[] questionIds = Arrays.stream(attributes[6].split(" ")).mapToInt(Integer::parseInt).toArray();

            for (int id :
                    questionIds) {
                if(Questions.containsKey(id)) {
                    normalQuiz.addQuestion(Questions.get(id));

                }

            }

            Quizzes.put(normalQuiz.getId(), normalQuiz);

        }
        scanner.close();
    }

    private void readMCQs(Map<Integer, Question> Questions) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("Data/MCQ.csv"));
        scanner.nextLine();
        scanner.useDelimiter("\n");


        while (scanner.hasNext()){
            String line = scanner.next();
            String[] attributes = line.split(",");

            MCQ mcq = new MCQ();

            mcq.setId(Integer.parseInt(attributes[0]));
            mcq.setQuestion(attributes[1]);
            mcq.setPoints(Integer.parseInt(attributes[2]));
            mcq.setNrChoices(Integer.parseInt(attributes[3]));
            mcq.setCorrectAnswer(attributes[5].toCharArray()[0]);

            Map<Character, String> choices = new HashMap<>();

            for (String choice : attributes[4].split("~")){

                String[] gr = choice.split("/");

                choices.put(gr[0].toCharArray()[0], gr[1]);

            }

            mcq.setChoices(choices);




            Questions.put(mcq.getId(), mcq);


        }
        scanner.close();

    }

    private void readMPs(Map<Integer, Question> Questions) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("Data/MP.csv"));
        scanner.nextLine();
        scanner.useDelimiter("\n");


        while (scanner.hasNext()){
            String line = scanner.next();
            String[] attributes = line.split(",");

            MP mp = new MP();

            mp.setId(Integer.parseInt(attributes[0]));
            mp.setQuestion(attributes[1]);
            mp.setPoints(Integer.parseInt(attributes[2]));


            Map<Character, String> columnA = new HashMap<>();

            for (String grade : attributes[3].split("~")){

                String[] gr = grade.split("/");

                columnA.put(gr[0].toCharArray()[0], gr[1]);

            }

            mp.setColumnA(columnA);


            Map<Integer, String> columnB = new HashMap<>();

            for (String grade : attributes[4].split("~")){

                String[] gr = grade.split("/");

                columnB.put(Integer.parseInt(gr[0]), gr[1]);

            }

            mp.setColumnB(columnB);




            Questions.put(mp.getId(), mp);


        }
        scanner.close();

    }

    private void readOEQs(Map<Integer, Question> Questions) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("Data/OEQ.csv"));
        scanner.nextLine();
        scanner.useDelimiter("\n");


        while (scanner.hasNext()){
            String line = scanner.next();
            String[] attributes = line.split(",");

            OEQ oeq = new OEQ();

            oeq.setId(Integer.parseInt(attributes[0]));
            oeq.setQuestion(attributes[1]);
            oeq.setPoints(Integer.parseInt(attributes[2]));
            oeq.setTips(attributes[3]);

            Questions.put(oeq.getId(), oeq);


        }
        scanner.close();

    }

    private Map<Integer, Integer> readAnswers(Map<Integer, Answer> Answers) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("Data/Answers.csv"));
        scanner.nextLine();
        scanner.useDelimiter("\n");
        
        Map<Integer, Integer> Students = new HashMap<>();

        while (scanner.hasNext()){
            String line = scanner.next();
            String[] attributes = line.split(",");

            Answer answer = new Answer();

            answer.setId(Integer.parseInt(attributes[0]));

            int userId = Integer.parseInt(attributes[1]);
            
            Students.put(answer.getId(), userId);
            

            List<String> answerList = Arrays.asList(attributes[2].split("/").clone());
            answer.setAnswer(answerList);


            Answers.put(answer.getId(), answer);


        }
        scanner.close();
        return Students;

    }


}
