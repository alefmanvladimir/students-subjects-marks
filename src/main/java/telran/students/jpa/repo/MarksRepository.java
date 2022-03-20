package telran.students.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import telran.students.entities.MarkJpa;
import telran.students.service.interfaces.IntervalMarks;
import telran.students.service.interfaces.StudentSubjectMark;

import java.util.List;

public interface MarksRepository extends JpaRepository<MarkJpa, Integer> {
    List<StudentSubjectMark> findByStudentNameAndSubjectJpaSubject(String name, String subject);

    // where == having
    @Query("select s.name, round(avg(m.mark)) from MarkJpa m join m.student s group by" +
            " s.name having avg(m.mark) > (select avg(mark) from MarkJpa ) order by " +
            " avg(m.mark) desc ")
    List<String> findBestStudents();

    @Query(value="select name, round(avg(mark)) from marks join students " +
            "on student_stid = stid group by name order by avg(mark) desc limit :nStudents", nativeQuery = true)
    List<String> findTopBestStudents(@Param("nStudents") int nStudents);

    @Query(value = "select name as studentName, subject as subjectJpaSubject, mark from marks join students \n" +
            "on student_stid = stid join subjects on subject_jpa_suid=suid \n" +
            "group by studentName, subject, mark having avg(mark) < (select avg(m.mark) from marks m) \n" +
            "order by avg(mark) limit :nStudents", nativeQuery = true)
    List<StudentSubjectMark> findWorstStudents(@Param("nStudents") int nStudents);

    @Query(value="select mark/:interval * :interval as min,"
            + " :interval * (mark/:interval + 1) - 1 as max, count(*) as occurrences from "
            + " marks  group by min,  max order by min", nativeQuery=true)
    List<IntervalMarks> findMarksDistribution(int interval);
}
