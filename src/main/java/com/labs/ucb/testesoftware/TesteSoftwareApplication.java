package com.labs.ucb.testesoftware;

import com.labs.ucb.testesoftware.model.Exam;
import com.labs.ucb.testesoftware.model.Student;
import com.labs.ucb.testesoftware.repository.StudentRepository;
import com.labs.ucb.testesoftware.service.ApplicationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class TesteSoftwareApplication implements CommandLineRunner {

	private final StudentRepository studentRepository;
	private final ApplicationService examRepository;
	
	public TesteSoftwareApplication(final StudentRepository studentRepository,
	                                final ApplicationService examRepository) {
		this.studentRepository = studentRepository;
		this.examRepository = examRepository;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TesteSoftwareApplication.class, args);
	}
	
	@Override
	public void run(final String... args) throws Exception {
		final Student student = new Student("James", "Bond", "007@mi6.com");
		studentRepository.save(student);
		Exam exam = new Exam();
		exam.setDate(new Date());
		exam.setWeight(1);
		exam.setDescription("Desc");
		exam.setName("Name");
		examRepository.saveExam(exam);
	}
}
