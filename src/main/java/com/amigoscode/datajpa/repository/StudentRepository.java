package com.amigoscode.datajpa.repository;

import com.amigoscode.datajpa.model.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.age >= ?2")
    List<Student> selectStudentsWhereFirstNameAndAgeGreaterOrEqual(String firstName, Integer age);

    @Query(value = "SELECT * FROM student WHERE first_name = ?1 AND age >= ?2", nativeQuery = true)
    List<Student> selectStudentsWhereFirstNameAndAgeGreaterOrEqualNative(String firstName, Integer age);

    @Query(value = "SELECT * FROM student WHERE first_name = :firstName AND age >= :age", nativeQuery = true)
    List<Student> selectStudentsWhereFirstNameAndAgeGreaterOrEqualNativeNamedParams(
            @Param("firstName") String firstName, @Param("age") Integer age);

    List<Student> findStudentsByFirstNameEqualsAndAgeEquals(String firstName, Integer age);

    List<Student> findStudentsByFirstNameEqualsAndAgeIsGreaterThanEqual(String firstName, Integer age);

    @Transactional
    @Modifying
    @Query("DELETE FROM Student u WHERE u.id = ?1")
    int deleteStudentById(Long id);
}
