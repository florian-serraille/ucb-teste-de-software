package com.labs.ucb.testesoftware.controller;

import com.labs.ucb.testesoftware.model.Exam;
import com.labs.ucb.testesoftware.model.Student;
import com.labs.ucb.testesoftware.service.ApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
	
	private final ApplicationService applicationService;
	
	public MainController(final ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@GetMapping
	public String homePage(Model model) {
		return "home";
	}
	
	@ModelAttribute("students")
	public List<Student> getStudents() {
		return applicationService.findAllStudents();
	}
	
	@ModelAttribute("exams")
	public List<Exam> getExams() {
		return applicationService.findAllExams();
	}
}
