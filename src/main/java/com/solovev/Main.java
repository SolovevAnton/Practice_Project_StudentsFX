package com.solovev;

import com.solovev.model.Car;
import com.solovev.model.IdHolder;
import com.solovev.model.Student;
import com.solovev.repositories.CarRepository;
import com.solovev.repositories.Repository;
import com.solovev.repositories.StudentRepository;

import java.io.IOException;
import java.time.Year;

public class Main {
    static <T extends IdHolder> void realDBTest(Repository<T> repository, T toAdd, T toReplace, T corrupted) throws IOException, InterruptedException {

        //take data test
        System.out.println(repository.takeData(1));
        System.out.println(repository.takeData());
        //test error:
        try {
            repository.takeData(-1);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        //add test
        IdHolder added = repository.add(toAdd);
        System.out.println(added); //MODIFIES ORIGINAL DB
        //test error:
        try {
            repository.add(corrupted);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        Thread.sleep(5000); //to check if it appeared in the DB

        //replace test
        toReplace.setId(added.getId());
        System.out.println(repository.replace(toReplace));
        //test error:
        try {
            repository.replace(corrupted);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        Thread.sleep(5000);//to check if it appeared in the DB


        //delete test
        System.out.println(repository.delete(toReplace.getId()));
        //test error:
        try {
            repository.delete(toReplace.getId());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            Repository<Student> studentRepository = new StudentRepository();
            Repository<Car> carRepository = new CarRepository();
            //student real db test
            realDBTest(studentRepository,
                    new Student(0, "added", 30, 200, 20.1),
                    new Student(0, "toReplace", 19, 201, 22),
                    new Student());
            Thread.sleep(5000);

            //cars real db Test
            System.out.println();
            realDBTest(carRepository,
                    new Car(0, "added", 20, Year.of(2007), 1),
                    new Car(0, "toReplace", 21, Year.of(2008), 2),
                    new Car());

        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }

    }
}