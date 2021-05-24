package com.ncit.finder.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LogoutController {

	@PostMapping("/logout/post/{post_id}")
	public String logout(HttpServletRequest request,@PathVariable("post_id") String post_id) {
		
		System.out.println("Logged out");
		request.getSession().invalidate();
		return "redirect:/post/"+post_id;
	}
	
	@PostMapping("/logout/post/")
	public String logout(HttpServletRequest request) {
		System.out.println("Logged out");
		request.getSession().invalidate();
		return "redirect:/";
		
	}
	
	
}
