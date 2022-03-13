package telran.students.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import telran.students.entities.MarkJpa;

public interface MarksRepository extends JpaRepository<MarkJpa, Integer> {
}
