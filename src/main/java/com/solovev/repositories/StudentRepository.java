package com.solovev.repositories;

import com.solovev.model.Student;
import com.solovev.util.Constants;

import java.io.IOException;

public class StudentRepository extends AbstractRepository<Student> {
    public StudentRepository() throws IOException {
        super(Constants.SERVER_URL,"/students", Student.class);
    }
}
