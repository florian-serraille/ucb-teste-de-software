package com.labs.ucb.testesoftware.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Exam {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date date;
	private Integer weight;
	@OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
	private List<Mark> marks = new ArrayList<>();
	
	public List<Student> getStudent() {
		return marks.stream()
		            .filter(mark -> mark.getExam().equals(this))
		            .map(Mark::getStudent)
		            .collect(Collectors.toList());
	}
}
