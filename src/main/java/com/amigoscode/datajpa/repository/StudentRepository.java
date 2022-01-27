package com.amigoscode.datajpa.repository;

import com.amigoscode.datajpa.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
