# Platformă e-learning 
Proiect PAO
## Etapa 1
**Obiecte:**
* [User](/src/models/User.java)
   * [Teacher](/src/models/Teacher.java)
   * [Student](/src/models/Student.java)
* [Course](/src/models/Course.java)
* [Quiz](/src/models/Quiz.java)
   * [Auto-scored (only MCQ)](/src/models/AutoScored.java)
   * [Normal (scored by the teacher, can have any type of question)](/src/models/NormalQuiz.java)
* [Question](/src/models/Question.java)
   * [Multiple-Choise Question (MCQ)](/src/models/MCQ.java)
   * [Open-ended Question (OEQ)](/src/models/OEQ.java)
   * [Match-the-pair (MP)](/src/models/MP.java)
* [Answer](/src/models/Answer.java)  
  
**Acțiuni:**
* User:
   * Register
   * Log in
   * Log out
* Teacher:
   * Create Course
   * Create Quiz
   * Score Answer
   * Delete a Question from a Quiz
   * Add a Question to a Quiz
 * Student:
   * Join Course
   * Take Quiz
   * Show Answers/Grades
 * Show all Courses from one Category

### Diagramă
![Diagram](https://github.com/vladanghelache/Platforma-e-learning/blob/main/E-learning%20Platform-1.png)
