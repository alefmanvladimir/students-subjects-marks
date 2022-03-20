package telran.students.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import telran.students.entities.StudentJpa;
import telran.students.entities.SubjectJpa;

import java.util.List;

public interface SubjectsRepository extends JpaRepository<SubjectJpa, Integer> {


}
