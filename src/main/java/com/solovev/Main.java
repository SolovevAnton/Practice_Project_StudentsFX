package com.solovev;

import com.solovev.model.Student;
import com.solovev.repositories.Repository;
import com.solovev.repositories.StudentRepository;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException { //todo remove from main
        Repository<Student> studentRepository = new StudentRepository();
        System.out.println(studentRepository.takeData(1));
        System.out.println(studentRepository.takeData());
    }
}