package telran.students.entities;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subjects")
public class SubjectDoc {
    @Id @Getter
    int suid;
    @Getter
    String subject;

    public SubjectDoc(int suid, String subject) {
        this.suid = suid;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "SubjectDoc{" +
                "suid=" + suid +
                ", subject='" + subject + '\'' +
                '}';
    }
}
