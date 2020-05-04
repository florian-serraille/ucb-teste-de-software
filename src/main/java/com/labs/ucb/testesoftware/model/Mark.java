package com.labs.ucb.testesoftware.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Mark {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;
	@ManyToOne
	@JoinColumn(name = "exam_id")
	private Exam exam;
	private Double mark;
	
	public Mark(final Student student, final Exam exam) {
		this.student = student;
		this.exam = exam;
		
		this.student.getMarks().add(this);
		this.exam.getMarks().add(this);
	}
	
	public Integer getWeight(){
		return this.exam.getWeight();
	}
}
