package com.amigoscode.datajpa;

import com.amigoscode.datajpa.model.Book;
import com.amigoscode.datajpa.model.Student;
import com.amigoscode.datajpa.model.StudentIdCard;
import com.amigoscode.datajpa.repository.StudentIdCardRepository;
import com.amigoscode.datajpa.repository.StudentRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                        StudentIdCardRepository studentIdCardRepository) {
        return args -> {
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName).toLowerCase();
            Integer age = faker.number().numberBetween(17, 55);
            Student student = new Student(firstName, lastName,email, age);

            student.addBook(new Book("Clean Code", LocalDateTime.now().minusDays(4)));
            student.addBook(new Book("Think and Grow Rich", LocalDateTime.now()));
            student.addBook(new Book("Spring Data JPA", LocalDateTime.now().minusYears(1)));

            StudentIdCard studentIdCard = new StudentIdCard("123456789", student);

            student.setStudentIdCard(studentIdCard);

            studentRepository.save(student);

            studentRepository.findById(1L).ifPresent(s -> {
                System.out.println("fetch book lazy...");
                List<Book> books = student.getBooks();          // that's fetching the books lazyly | by demand
                books.forEach(book -> {
                    System.out.println(s.getFirstName() + " borrowed " + book.getBookName());
                });
            });

            //studentIdCardRepository.findById(1L).ifPresent(System.out::println);
        };
    }

    private void studentsListPaged(StudentRepository studentRepository) {
        PageRequest pageRequest = PageRequest.of(0,5, Sort.by("firstName").ascending());            // page 0, size 5, sort by firstName ASC
        Page<Student> page = studentRepository.findAll(pageRequest);
        System.out.println(page);
    }

    private void sortStudents(StudentRepository studentRepository) {
        // Sort sort = Sort.by(Sort.Direction.DESC, "firstName");
        Sort sort = Sort.by("firstName").ascending().and(Sort.by("age").descending());

        studentRepository.findAll(sort)
                .forEach(student -> System.out.println(student.getFirstName() + " " + student.getAge()));
    }

    private void generateRandomStudents(StudentRepository studentRepository) {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName).toLowerCase();
            Integer age = faker.number().numberBetween(17, 55);
            Student student = new Student(firstName, lastName,email, age);
            studentRepository.save(student);
        }
    }
}
 // exception -> object references an unsaved transient instance - save the transient instance before flushing -> it's missing 'cascade = {CascadeType.PERSIST, CascadeType.REMOVE}' int the @OneToOne or @ManyToMany or @OneToMany relation