package com.labs.ucb.testesoftware.repository;

import com.labs.ucb.testesoftware.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
	
}
