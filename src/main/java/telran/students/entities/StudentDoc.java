package telran.students.entities;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "students")
public class StudentDoc {
    @Id @Getter
    int stid;
    @Getter
    String name;

    @Getter
    List<SubjectMark> marks;

    public StudentDoc(int stid, String name) {
        this.stid = stid;
        this.name = name;
        this.marks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "StudentDoc{" +
                "stid=" + stid +
                ", name='" + name + '\'' +
                ", marks=" + marks +
                '}';
    }
}
