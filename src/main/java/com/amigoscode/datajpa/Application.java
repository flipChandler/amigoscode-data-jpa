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
                    "maria@gmail.com",
                    21
            );
            Student maria2 = new Student(
                    "Maria",
                    "Jones",
                    "maria2@gmail.com",
                    25
            );
            Student maria3 = new Student(
                    "Maria",
                    "Jones",
                    "maria3@gmail.com",
                    29
            );
                    Student jose = new Student(
                    "Jose",
                    "Smith",
                    "jose@gmail.com",
                    27
            );
            System.out.print("adding maria and jose\n");
            studentRepository.saveAll(List.of(maria, jose, maria2, maria3));

            studentRepository.findStudentByEmail("jose@gmail.com")
                    .ifPresentOrElse(System.out::println,
                            () -> System.out.println("Email not found"));

            studentRepository.findStudentsByFirstNameEqualsAndAgeEquals("Maria", 21)
                    .forEach(System.out::println);

            studentRepository.selectStudentsWhereFirstNameAndAgeGreaterOrEqual("Maria", 21)
                    .forEach(System.out::println);

            studentRepository.selectStudentsWhereFirstNameAndAgeGreaterOrEqualNative("Maria", 21)
                    .forEach(System.out::println);

            studentRepository.selectStudentsWhereFirstNameAndAgeGreaterOrEqualNativeNamedParams("Maria", 21)
                    .forEach(System.out::println);

            System.out.println(studentRepository.deleteStudentById(4L));
        };
    }
}
