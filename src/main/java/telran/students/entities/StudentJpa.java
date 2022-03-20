package telran.students.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import telran.students.dto.Student;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
public class StudentJpa {
    @Id
    int stid;
    @Column(nullable = false, unique = true)
    String name;
    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
//    @OnDelete(action = OnDeleteAction.CASCADE) // adding the cascade deletion constrains to DB (hibernate)
    List<MarkJpa> marks;
    public static StudentJpa build(Student student) {
        StudentJpa res = new StudentJpa();
        res.name = student.name;
        res.stid = student.stid;
        return res;
    }
    public Student getStudentDto(){
        Student res = new Student();
        res.name = name;
        res.stid = stid;
        return res;
    }
}
