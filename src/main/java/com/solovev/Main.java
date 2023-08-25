package com.solovev;

import com.solovev.model.Student;
import com.solovev.repositories.Repository;
import com.solovev.repositories.StudentRepository;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException { //todo remove from main
        Repository<Student> studentRepository = new StudentRepository();
        System.out.println(studentRepository.takeData(1));
        System.out.println(studentRepository.takeData());
        //test error:
        try{
            studentRepository.takeData(-1);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        Student added = studentRepository.add(new Student(1,"added",30,200,20.1));
        System.out.println(added); //MODIFIES ORIGINAL DB

        //test error:
        try{
            studentRepository.add(new Student());
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        Thread.sleep(5000);
    }
}