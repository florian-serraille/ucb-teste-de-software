package com.labs.ucb.testesoftware.controller;

import com.labs.ucb.testesoftware.dto.MarksForExamWrapper;
import com.labs.ucb.testesoftware.model.Exam;
import com.labs.ucb.testesoftware.service.ApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/exams")
public class ExamController {
	
	private final ApplicationService applicationService;
	
	public ExamController(final ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@PostMapping
	public String saveExam(@Valid @ModelAttribute("exam") Exam exam,
	                       BindingResult bindingResult,
	                       RedirectAttributes redirectAttributes) {
		
		String nextPage;
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addAttribute("examId", exam.getId());
			nextPage = "/exams/form";
			
		} else {
			
				applicationService.saveExam(exam);
				
			nextPage = "/";
		}
		
		return "redirect:" + nextPage;
	}
	
	@GetMapping("delete")
	public String deleteExam(@RequestParam Long examId) {
		
		applicationService.deleteExamByID(examId);
		return "redirect:/";
	}
	
	@GetMapping("form")
	public String showStudentForm(Model model, @RequestParam(required = false) Long examId) {
		
		Exam exam = examId == null ? new Exam() : applicationService.findExamById(examId);
		model.addAttribute("exam", exam);
		model.addAttribute("students", applicationService.findAllStudents());
		model.addAttribute("examMarks", applicationService.buildMarksExamWrapper(exam));
		
		return "exam-form";
	}
	
	@PostMapping("marks")
	public String saveAllMarks(@RequestParam final Long examId, MarksForExamWrapper marksWrapper) {
		
		applicationService.saveMarksWrapper(examId, marksWrapper);
		
		return "redirect:/";
	}
	
}
