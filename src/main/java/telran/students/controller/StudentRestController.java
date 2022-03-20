package telran.students.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import telran.students.dto.QueryDto;
import telran.students.dto.QueryType;
import telran.students.dto.Student;
import telran.students.service.interfaces.*;

import java.util.*;

@RestController
@RequestMapping("/students")
public class StudentRestController {
    @Autowired
    StudentService studentService;
    @GetMapping("/subject/mark")
    public List<StudentSubjectMark> getStudentSubjectMark(String name, String subject){
        return studentService.getMarksStudentSubject(name, subject);
    }

    @GetMapping("/best")
    public List<String> getBestStudents(@RequestParam(required = false, defaultValue = "0", name="amount") int nStudents){
        return nStudents==0?studentService.getBestStudents():studentService.getTopStudents(nStudents);
    }

    @GetMapping("/best/subject")
    public List<Student> getBestStudentsSubject(@RequestParam(name = "amount") int nStudents,
                                                @RequestParam(name = "subject") String subject){
        return studentService.getTopBestStudentsSubject(nStudents, subject);
    }

    @GetMapping("/worst")
    public List<StudentSubjectMark> getMarksOfWorstStudents(@RequestParam(name = "amount") int nStudent){
        return studentService.getMarksOfWorstStudents(nStudent);
    }

    @PostMapping("/query")
    public List<String> getQueryResult(@RequestBody QueryDto queryDto){
        return queryDto.type == QueryType.JPQL ? studentService.jpqlQuery(queryDto.query) :
                studentService.nativeQuery(queryDto.query);
    }

    @GetMapping("/interval")
    public List<IntervalMarks> getMarksDistribution(@RequestParam(name="interval") int interval){
        return studentService.marksDistribution(interval);
    }

    @DeleteMapping("/delete")
    public List<Student> deleteStudents(@RequestParam(name = "avgMark") int avgMark, @RequestParam(name="nMarks") int nMarks){
        return studentService.removesStudents(avgMark, nMarks);
    }
}
