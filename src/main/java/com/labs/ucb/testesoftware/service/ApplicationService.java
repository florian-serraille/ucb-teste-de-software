package com.labs.ucb.testesoftware.service;

import com.labs.ucb.testesoftware.dto.MarksForExamWrapper;
import com.labs.ucb.testesoftware.model.Exam;
import com.labs.ucb.testesoftware.model.Mark;
import com.labs.ucb.testesoftware.model.Student;
import com.labs.ucb.testesoftware.repository.ExamRepository;
import com.labs.ucb.testesoftware.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.peer.CanvasPeer;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class ApplicationService {
	
	private final ExamRepository examRepository;
	private final StudentRepository studentRepository;
	
	public ApplicationService(final ExamRepository examRepository,
	                          final StudentRepository repository) {
		this.examRepository = examRepository;
		this.studentRepository = repository;
	}
	
	public List<Exam> findAllExams() {
		return examRepository.findAll();
	}
	
	public void saveExam(final Exam exam) {
		if (exam.getId() == null) {
			findAllStudents().forEach(student -> new Mark(student, exam));
		}
		examRepository.save(exam);
	}
	
	public Exam findExamById(final Long examId) {
		return examRepository.findById(examId).orElseGet(Exam::new);
	}
	
	public List<Student> findAllStudents() {
		
		List<Student> students = studentRepository.findAll();
		students.forEach(student ->
				                 student.setMarks(student.getMarks()
				                                         .stream()
				                                         .sorted(Comparator.comparingLong(Mark::getId))
				                                         .collect(Collectors.toList())));
		students.forEach(Student::calculateAverageMark);
		return students;
	}
	
	public void saveStudent(final Student student) {
		if (student.getId() == null) {
			findAllExams().forEach(exam -> new Mark(student, exam));
		}
		studentRepository.save(student);
	}
	
	public void deleteStudentByID(final Long studentId) {
		studentRepository.deleteById(studentId);
	}
	
	public Student findStudentById(final Long studentId) {
		return studentRepository.findById(studentId).orElseGet((Student::new));
	}
	
	public void deleteExamByID(final Long examId) {
		examRepository.deleteById(examId);
	}
	
	public MarksForExamWrapper buildMarksExamWrapper(final Exam exam) {
		
		MarksForExamWrapper marksForExamWrapper = new MarksForExamWrapper();
		exam.getMarks().forEach(mark -> marksForExamWrapper.addMark(mark.getId(), mark.getMark()));
		
		return marksForExamWrapper;
	}
	
	public void saveMarksWrapper(final Long examId, final MarksForExamWrapper marksWrapper) {
		
		final Optional<Exam> exam = examRepository.findById(examId);
		exam.ifPresent(e -> {
			marksWrapper.getMarks().forEach(
					markDto -> e.getMarks()
					            .stream()
					            .filter(mark -> mark.getId().equals(markDto.getMarkId()))
					            .forEach(mark -> mark.setMark(markDto.getMark()))
			);
		});
		
	}
}
