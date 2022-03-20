package telran.students.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import telran.students.entities.StudentJpa;

import java.util.List;

public interface StudentsRepository extends JpaRepository<StudentJpa, Integer> {
    @Query(value="select stid, name from marks join students " +
            "on student_stid = stid join subjects on subject = :subjectName where suid=subject_jpa_suid " +
            "group by stid, name order by avg(mark) desc limit :nStudents", nativeQuery = true)
    List<StudentJpa> findTopBestStudentsSubject(@Param("nStudents") int nStudents, @Param("subjectName") String subjectName);

    @Modifying
    @Query("delete from StudentJpa where stid in " +
            "(select student.stid from MarkJpa group by student.stid " +
            "having avg(mark) < :avgMark and count(*) < :nMarks )")
    int deleteStudents(@Param("avgMark") double avgMark,@Param("nMarks") long nMarks);

    @Query("select s from StudentJpa s where s.stid in " +
            "(select student.stid from MarkJpa group by student.stid " +
            "having avg(mark) < :avgMark and count(*) < :nMarks )")
    List<StudentJpa> findStudentsForDeletion(@Param("avgMark") double avgMark,@Param("nMarks") long nMarks);
}
