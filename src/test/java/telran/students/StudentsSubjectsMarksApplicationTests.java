package telran.students;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import telran.students.dto.Mark;
import telran.students.dto.Student;
import telran.students.dto.Subject;
import telran.students.service.interfaces.StudentService;

class StSum{
    public String subjectJpaSubject;
    public String studentName;
    public int mark;
}
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentsSubjectsMarksApplicationTests {
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;
    @Autowired
    StudentService studentService;

    @Test
    void contextLoads() {
        assertNotNull(mockMvc);
    }

    @Test
//    @Sql("testDB.sql")
    @Order(1)
    void dbLoad(){
        studentService.addStudent(new Student(1, "Moshe"));
        studentService.addStudent(new Student(2, "Sara"));
        studentService.addStudent(new Student(3, "Vasya"));
        studentService.addStudent(new Student(4, "Olya"));
        studentService.addSubject(new Subject(1, "React"));
        studentService.addSubject(new Subject(2, "Java"));
        studentService.addMark(new Mark(1, 1, 90));
        studentService.addMark(new Mark(1, 2, 90));
        studentService.addMark(new Mark(2, 1, 80));
        studentService.addMark(new Mark(2, 2, 80));
        studentService.addMark(new Mark(3, 2, 40));
        studentService.addMark(new Mark(4, 2, 45));
    }
    @Test
    @Order(99)
    void worstMarks() throws Exception {
        String subject = "Java";
        String name = "Vasya";
        int mark = 40;
        testForWorstMarks(subject, name, mark);
    }

    @Test
    @Order(100)
    void deleteStudents() throws Exception{
        String resJSON = mockMvc.perform(MockMvcRequestBuilders.delete("/students/delete?avgMark=45&nMarks=2"))
                .andReturn().getResponse().getContentAsString();
        Student [] students = mapper.readValue(resJSON,
                Student [].class);
        Student[] expected = {new Student(3, "Vasya")};
        assertArrayEquals(expected, students);
    }

    private void testForWorstMarks(String subject, String name, int mark)
            throws  Exception{
        String resJSON = mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/worst?amount=1")).andReturn()
                .getResponse().getContentAsString();
        StSum [] subjectMarks = mapper.readValue(resJSON,
                StSum [].class);

        testWorstMarks(subjectMarks, subject, name, mark);
    }
    private void testWorstMarks(StSum[] subjectMarks, String subject, String name, int mark) {
        assertEquals(1, subjectMarks.length);
        assertEquals(subject, subjectMarks[0].subjectJpaSubject);
        assertEquals(name, subjectMarks[0].studentName);
        assertEquals(mark, subjectMarks[0].mark );
    }

    @Test
    void bestTopStudents() throws  Exception {
        String resJSON = mockMvc.perform(MockMvcRequestBuilders.get("/students/best?amount=1"))
                .andReturn().getResponse().getContentAsString();
        String[] res = mapper.readValue(resJSON, String[].class);
        assertEquals(1, res.length);
        assertTrue(res[0].contains("Moshe"));
    }

    @Test
    void bestStudents() throws  Exception {
        String resJSON = mockMvc.perform(MockMvcRequestBuilders.get("/students/best"))
                .andReturn().getResponse().getContentAsString();
        String[] res = mapper.readValue(resJSON, String[].class);
        assertEquals(2, res.length);
        assertTrue(res[0].contains("Moshe"));
        assertTrue(res[1].contains("Sara"));
    }

}
