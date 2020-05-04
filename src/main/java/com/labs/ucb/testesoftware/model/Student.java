package com.labs.ucb.testesoftware.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Student implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String lastName;
	@Email
	@NotEmpty
	private String email;
	private String password;
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	private List<Mark> marks = new ArrayList<>();
	@Transient
	private Double averageMark;
	
	public Student(String name, String lastName, String email) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		
	}
	
	public Mark getMarkByExam(Exam exam) {
		return marks.stream()
		            .filter(mark -> mark.getExam().equals(exam))
		            .findFirst()
		            .orElse(null);
	}
	
	public void calculateAverageMark() {
		
		Double average = marks.stream()
		                      .filter(mark -> mark.getMark() != null)
		                      .collect(averagingWeighted(
				                      Mark::getMark,
				                      Mark::getWeight));
		
		setAverageMark(roundUp(average));
	}
	
	private Double roundUp(Double average) {
		average = Double.isNaN(average) ? 0.0 : average;
		return new BigDecimal(average).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
	
	static <T> Collector<T, ?, Double> averagingWeighted(ToDoubleFunction<T> valueFunction,
	                                                     ToIntFunction<T> weightFunction) {
		class Box {
			
			double num = 0;
			long denominator = 0;
		}
		return Collector.of(
				Box::new,
				(b, e) -> {
					b.num += valueFunction.applyAsDouble(e) * weightFunction.applyAsInt(e);
					b.denominator += weightFunction.applyAsInt(e);
				},
				(b1, b2) -> {
					b1.num += b2.num;
					b1.denominator += b2.denominator;
					return b1;
				},
				b -> b.num / b.denominator
		);
	}
}

