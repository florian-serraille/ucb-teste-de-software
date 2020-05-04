package com.labs.ucb.testesoftware.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MarksForExamWrapper {
	
	List<MarkDto> marks = new ArrayList<>();
	
	public void addMark(final Long markId, final Double mark) {
		marks.add(new MarkDto(mark, markId));
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class MarkDto {
		
		Double mark;
		Long markId;
	}
}
