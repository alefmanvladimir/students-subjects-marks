package telran.students.entities;

import lombok.*;

public class SubjectMark {
    @Getter
    String subject;
    @Getter
    int mark;

    public SubjectMark(String subject, int mark) {
        this.subject = subject;
        this.mark = mark;
    }
}
