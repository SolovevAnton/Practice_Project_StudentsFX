package com.solovev;

import com.solovev.model.IdHolder;
import com.solovev.model.Student;
import com.solovev.repositories.Repository;
import com.solovev.repositories.StudentRepository;

import java.io.IOException;

public class Main {
    static <T extends IdHolder> void realDBTest(Repository<T> repository, T toAdd, T toReplace, T corrupted) throws IOException, InterruptedException {

        //take data test
        System.out.println(repository.takeData(1));
        System.out.println(repository.takeData());
        //test error:
        try {
            repository.takeData(-1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        //add test
        IdHolder added = repository.add(toAdd);
        System.out.println(added); //MODIFIES ORIGINAL DB
        //test error:
        try {
            repository.add(corrupted);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        Thread.sleep(5000); //to check if it appeared in the DB

        //replace test
        toReplace.setId(added.getId());
        System.out.println(repository.replace(toReplace));
        //test error:
        try {
            repository.replace(corrupted);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        Thread.sleep(5000);//to check if it appeared in the DB


        //delete test
        System.out.println(repository.delete(toReplace.getId()));
        //test error:
        try {
            repository.delete(toReplace.getId());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException { //todo remove from main
        Repository<Student> studentRepository = new StudentRepository();

        realDBTest(studentRepository,
                new Student(0, "added", 30, 200, 20.1),
                new Student(0,"toReplace",19,201,22),
                new Student());

    }
}