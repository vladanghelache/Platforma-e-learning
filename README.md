# Platformă e-learning 
Proiect PAO
## Etapa 1
**Obiecte:**
* models.User
   * models.Teacher
   * models.Student
* models.Course
* models.Quiz
   * Auto-scored (only models.MCQ)
   * Normal (scored by the teacher, can have any type of question)
* models.Question
   * Multiple-Choise models.Question (models.MCQ)
   * Open-ended models.Question (models.OEQ)
   * Match-the-pair (models.MP)
* models.Answer  
  
**Acțiuni:**
* models.User:
   * Register
   * Log in
   * Log out
* models.Teacher:
   * Create models.Course
   * Create models.Quiz
   * Score models.Answer
   * Delete models.Question from a models.Quiz
   * Add models.Question to a models.Quiz
 * models.Student:
   * Join models.Course
   * Take models.Quiz
   * Show Answers/Grades
 * Show all Courses from one models.Category

### Diagramă
![Diagram](https://github.com/vladanghelache/Platforma-e-learning/blob/main/E-learning%20Platform-1.png)
