package telran.students.entities;

import javax.persistence.*;

@Entity
@Table(name="marks")
public class MarkJpa {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    int mark;
    @ManyToOne
    StudentJpa student;
    @ManyToOne
    SubjectJpa subjectJpa;

    public MarkJpa(){

    }

    public MarkJpa(int mark, StudentJpa student, SubjectJpa subjectJpa) {
        this.mark = mark;
        this.student = student;
        this.subjectJpa = subjectJpa;
    }
}
