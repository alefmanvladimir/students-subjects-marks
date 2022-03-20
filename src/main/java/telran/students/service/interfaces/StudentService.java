package telran.students.service.interfaces;

import telran.students.dto.Mark;
import telran.students.dto.Student;
import telran.students.dto.Subject;

import java.util.List;

public interface StudentService {
    void addStudent(Student student);
    void addSubject(Subject subject);
    Mark addMark(Mark mark);
    List<StudentSubjectMark> getMarksStudentSubject(String name, String subject);
    List<String> getBestStudents(); // returns names of students having average mark greater than average mark of all students
    List<String> getTopStudents(int nStudenst);
    List<Student> getTopBestStudentsSubject(int nStudents, String subject);
    List<StudentSubjectMark> getMarksOfWorstStudents(int nStudents);
    List<IntervalMarks> marksDistribution(int interval);
    List<String> jpqlQuery(String jpql);
    List<String> nativeQuery(String query);
    List<Student> removesStudents(int avgMark, int nMarks);
}
