package com.solovev.repositories;

import com.solovev.model.Student;
import com.solovev.util.Constants;

import java.io.IOException;
import java.net.URL;

public class StudentRepository extends AbstractRepository<Student> {
    public StudentRepository() throws IOException {
        super(Student.class, Constants.SERVER_URL,"/students");
    }
}
