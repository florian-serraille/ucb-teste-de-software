package com.labs.ucb.testesoftware.controller;

import com.labs.ucb.testesoftware.model.Student;
import com.labs.ucb.testesoftware.service.ApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/students")
public class StudentController {
	
	private final ApplicationService applicationService;
	
	public StudentController(final ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@PostMapping
	public String saveStudent(@Valid @ModelAttribute("student") Student student,
	                          BindingResult bindingResult,
	                          RedirectAttributes redirectAttributes) {
		
		String nextPage;
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addAttribute("studentId", student.getId());
			nextPage = "/students/form";
			
		} else {
			
			applicationService.saveStudent(student);
			nextPage = "/";
		}
		
		return "redirect:" + nextPage;
	}
	
	@GetMapping("delete")
	public String deleteStudent(@RequestParam Long studentId) {
		
		applicationService.deleteStudentByID(studentId);
		return "redirect:/";
	}
	
	@GetMapping("form")
	public String showStudentForm(Model model, @RequestParam(required = false) Long studentId) {
		
		Student student = studentId == null ? new Student() : applicationService.findStudentById(studentId);
		model.addAttribute("student", student);
		
		return "student-form";
	}
	
}
