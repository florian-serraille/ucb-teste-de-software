package com.labs.ucb.testesoftware.repository;

import com.labs.ucb.testesoftware.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
