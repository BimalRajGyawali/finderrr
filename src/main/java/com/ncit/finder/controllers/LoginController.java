package com.ncit.finder.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ncit.finder.models.User;
import com.ncit.finder.repository.UserRepository;

@Controller
public class LoginController {
	@PostMapping("/loginsubmit")
	public String loginUser(HttpServletRequest request,RedirectAttributes redirectAttributes) {
   
		String email = request.getParameter("email");
		String password=request.getParameter("password");
		byte[] passwordByte = DigestUtils.md5Digest(password.getBytes());
		String passwordHashed=new String(passwordByte);
		UserRepository repository= new UserRepository(); 
		User user=repository.getUser(email, passwordHashed);
		if(user.getId()==0) {
			redirectAttributes.addFlashAttribute("email",email);
			redirectAttributes.addFlashAttribute("password",password);
			redirectAttributes.addFlashAttribute("error",true);
			return "redirect:/login";
		
		}
		request.getSession().setAttribute("email",email);
		request.getSession().setAttribute("id", user.getId());
		request.getSession().setAttribute("firstname", user.getFirstName());
		request.getSession().setAttribute("middlename", user.getMiddleName());
		request.getSession().setAttribute("lastname", user.getLastName());
		System.out.println("Logged In");
		return "redirect:/";

	}
	
	@GetMapping("/login")
	public String loginPage() {
		
		return "login";
	}
	
	
	
}
