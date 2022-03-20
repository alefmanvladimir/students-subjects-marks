package telran.students.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import telran.students.dto.*;
import telran.students.entities.MarkJpa;
import telran.students.entities.StudentJpa;
import telran.students.entities.SubjectJpa;
import telran.students.service.interfaces.*;
import telran.students.jpa.repo.*;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class StudentServiceJpaImpl implements StudentService {
    StudentsRepository studentsRepository;
    SubjectsRepository subjectsRepository;
    MarksRepository marksRepository;

    @PersistenceContext
    EntityManager em;

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
        return marksRepository.findByStudentNameAndSubjectJpaSubject(name, subject);
    }

    @Override
    public List<String> getBestStudents() {
        return marksRepository.findBestStudents();
    }

    @Override
    public List<String> getTopStudents(int nStudenst) {
        return marksRepository.findTopBestStudents(nStudenst);
    }

    @Override
    public List<Student> getTopBestStudentsSubject(int nStudents, String subject) {
        List<StudentJpa> resJpa = studentsRepository.findTopBestStudentsSubject(nStudents, subject);
        return resJpa.stream().map(s->s.getStudentDto()).toList();
    }

    @Override
    public List<StudentSubjectMark> getMarksOfWorstStudents(int nStudents) {
        return marksRepository.findWorstStudents(nStudents);
    }

    @Override
    public List<IntervalMarks> marksDistribution(int interval) {
        return marksRepository.findMarksDistribution(interval);
    }

    @Override
    public List<String> jpqlQuery(String jpql) {
        Query query = em.createQuery(jpql);
        return getResult(query);
    }

    private List<String> getResult(Query query) {
        List result = query.getResultList();
        if (result.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return result.get(0).getClass().isArray() ? multiProjectionRequest(result) :
                simpleRequest(result);
    }

    private List<String> multiProjectionRequest(List<Object[]> result) {

        return  result.stream().map(Arrays::deepToString).toList();
    }

    private List<String> simpleRequest(List<Object> result) {

        return result.stream().map(Object::toString).toList();
    }

    @Override
    public List<String> nativeQuery(String sql) {
        Query query = em.createNativeQuery(sql);
        return getResult(query);
    }

    @Override
    @Transactional
    public List<Student> removesStudents(int avgMark, int nMarks) {
        List<StudentJpa> listJpa = studentsRepository.findStudentsForDeletion(avgMark, nMarks);
        listJpa.forEach(studentsRepository::delete);
//        studentsRepository.deleteStudents(avgMark, nMarks); //with hibernate annotations
        return listJpa.stream().map(StudentJpa::getStudentDto).toList();
    }
}
