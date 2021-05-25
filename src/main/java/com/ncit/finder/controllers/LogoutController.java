package com.ncit.finder.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LogoutController {

	@GetMapping("/logout/post/{post_id}")
	public String logout(HttpServletRequest request,@PathVariable("post_id") String post_id) {
		
		System.out.println("Logged out");
		request.getSession().invalidate();
		return "redirect:/post/"+post_id;
	}
	
	@GetMapping("/logout/post/")
	public String logout(HttpServletRequest request) {
		System.out.println("Logged out");
		request.getSession().invalidate();
		return "redirect:/";
		
	}
	
	
}
