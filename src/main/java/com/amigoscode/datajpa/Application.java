package com.amigoscode.datajpa;

import com.amigoscode.datajpa.model.Student;
import com.amigoscode.datajpa.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student maria = new Student(
                    "Maria",
                    "Jones",
                    "maria@gmai.com",
                    21
            );
            Student jose = new Student(
                    "Jose",
                    "Smith",
                    "jose@gmai.com",
                    27
            );
            System.out.print("adding maria and jose\n");
            studentRepository.saveAll(List.of(maria, jose));

            System.out.print("number of students\n");
            System.out.print(studentRepository.count());


            studentRepository.findById(2L).ifPresentOrElse(student -> {
                System.out.println(student);
            }, () -> {
                System.out.println("student with id 2 not found");
            });

            studentRepository.findById(2L).ifPresentOrElse(student -> {
                System.out.println(student);
            }, () -> {
                System.out.println("student with id 3 not found");
            });

            System.out.println("select all students");
            List<Student> students = studentRepository.findAll();
            students.forEach(System.out::println);

            System.out.println("delete maria");
            studentRepository.deleteById(1L);

            System.out.println("number of students");
            System.out.println(studentRepository.count());
        };
    }
}
