package telran.students.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import telran.students.entities.StudentJpa;

public interface StudentsRepository extends JpaRepository<StudentJpa, Integer> {
}
