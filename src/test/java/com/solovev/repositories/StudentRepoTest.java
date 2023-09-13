package com.solovev.repositories;

import com.solovev.model.Student;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class StudentRepoTest {
    @Test
    public void studentCreationTest() throws IOException {
        //test that server is working
        assume()

        StudentRepository students = new StudentRepository();
        List<Student> collectionStudents = new ArrayList<>(students.takeData());
        Student student = students.takeData(1);
        Student otherStudent =  collectionStudents.get(0);
        assertEquals


    }

}
