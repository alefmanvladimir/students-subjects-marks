package telran.students.entities;

import telran.students.dto.Subject;

import javax.persistence.*;

@Entity
@Table(name="subjects")
public class SubjectJpa {
    @Id
    int suid;
    @Column(nullable = false, unique = true)
    String subject;

    public static SubjectJpa build(Subject subject){
        SubjectJpa res = new SubjectJpa();
        res.suid = subject.suid;
        res.subject = subject.subject;
        return res;
    }

    public Subject getSubjectDto(){
        Subject res = new Subject();
        res.suid = suid;
        res.subject = subject;
        return res;
    }
}
