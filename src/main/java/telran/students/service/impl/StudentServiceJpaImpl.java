package telran.students.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import telran.students.dto.*;
import telran.students.entities.MarkJpa;
import telran.students.entities.StudentJpa;
import telran.students.entities.SubjectJpa;
import telran.students.service.interfaces.*;
import telran.students.jpa.repo.*;

import javax.transaction.Transactional;
import java.util.List;

public class StudentServiceJpaImpl implements StudentService {
    StudentsRepository studentsRepository;
    SubjectsRepository subjectsRepository;
    MarksRepository marksRepository;

    @Autowired
    public StudentServiceJpaImpl(StudentsRepository studentsRepository, SubjectsRepository subjectsRepository, MarksRepository marksRepository) {
        this.studentsRepository = studentsRepository;
        this.subjectsRepository = subjectsRepository;
        this.marksRepository = marksRepository;
    }

    @Override
    public void addStudent(Student student) {
        studentsRepository.save(StudentJpa.build(student));
    }

    @Override
    public void addSubject(Subject subject) {
        subjectsRepository.save(SubjectJpa.build(subject));
    }

    @Override
    @Transactional
    public Mark addMark(Mark mark) {
        StudentJpa studentJpa = studentsRepository.findById(mark.stid).orElse(null);
        SubjectJpa subjectJpa = subjectsRepository.findById(mark.suid).orElse(null);
        if(studentJpa != null && subjectJpa != null){
            marksRepository.save(new MarkJpa(mark.mark, studentJpa, subjectJpa));
            return mark;
        }

        return null;
    }

    @Override
    public List<StudentSubjectMark> getMarksStudentSubject(String name, String subject) {
        return null;
    }

    @Override
    public List<String> getBestStudents() {
        return null;
    }

    @Override
    public List<String> getTopStudents(int nStudenst) {
        return null;
    }

    @Override
    public List<String> getTopBestStudentsSubject(int nStudents, String subject) {
        return null;
    }

    @Override
    public List<StudentSubjectMark> getMarksOfWorstStudents(int nStudents) {
        return null;
    }

    @Override
    public List<IntervalMarks> marksDistribution(int interval) {
        return null;
    }
}
