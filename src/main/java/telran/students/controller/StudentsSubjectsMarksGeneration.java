package telran.students.controller;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import telran.students.dto.Mark;
import telran.students.dto.Student;
import telran.students.dto.Subject;
import telran.students.service.interfaces.StudentService;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Component
public class StudentsSubjectsMarksGeneration {
    static Logger LOG = LoggerFactory.getLogger("generation");
    @Value("${app.generation.create: false}")
    boolean needCreateBD;
    @Value("${app.generation.amount: 100}")
    int nMarks;
    @Autowired
    StudentService studentService;

    String names[] = {"Abraham", "Sarah", "Itshak", "Rahel", "Asaf", "Yacob","Rivka", "Yosef",
            "Benyanim", "Dan", "Ruben", "Moshe", "Aron", "Yehashua", "David", "Salomon", "Nefertity",
            "Naftaly", "Natan","Asher"};

    String subjects[] = {"Java core", "Java Technologies",
            "Spring Data", "Spring Security", "Spring Cloud", "CSS", "HTML", "JS", "React", "Material-UI"};
    @PostConstruct
    void createDB(){
        if(needCreateBD){
            addStudents();
            addSubjects();
            addMarks();
            LOG.info("created {} marks in DB", nMarks);
        }
    }

    private int getRandomNumber(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private void addMarks() {
        IntStream.range(0, nMarks).forEach(i->addOneMark());
    }

    private void addOneMark() {
        int stid = getRandomNumber(1, names.length);
        int suid = getRandomNumber(1, subjects.length);
        int mark = getRandomNumber(60, 100);
        studentService.addMark(new Mark(stid, suid, mark));
    }

    private void addSubjects() {
        IntStream.range(0, subjects.length).forEach(i->studentService.addSubject(new Subject(i+1, subjects[i])));
    }

    private void addStudents() {
        IntStream.range(0, names.length).forEach(i->studentService.addStudent(new Student(i+1, names[i])));
    }
}
