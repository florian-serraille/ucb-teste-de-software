package com.labs.ucb.testesoftware.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FallBackController implements ErrorController {
	
	@GetMapping("/error")
	public String fallBack(RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("error", true);
		return "redirect:/";
	}
	
	@Override
	public String getErrorPath() {
		return "/error";
	}
}
