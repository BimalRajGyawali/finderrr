package com.ncit.finder.controllers;

import java.time.LocalDateTime;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import com.ncit.finder.functionality.MailSender;

import com.ncit.finder.functionality.RandomCodeGenerator;

import com.ncit.finder.models.User;
import com.ncit.finder.models.UserDetail;

import com.ncit.finder.repository.UserRepository;

@Controller
public class RegistrationController {
	
	@GetMapping("/register")
	public String createUser() {
		return "register";

	}
	
	@PostMapping("/verification1")
	public String sendVerification(HttpServletRequest request, RedirectAttributes redirectAttributes,Model model) {
		
		String code=RandomCodeGenerator.generate();
		System.out.print("Verification Code ="+code);
		String fName = request.getParameter("fname");
		String mName = request.getParameter("mname");
		String lName = request.getParameter("lname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		boolean errorSignal=false;
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		UserRepository repository=new UserRepository();
		
		if(fName.length()==0) {
			System.out.println("First name cant be empty");
			redirectAttributes.addFlashAttribute("fnameError",true);
			errorSignal=true;
			
		}if(lName.length()==0) {
			redirectAttributes.addFlashAttribute("lnameError",true);
			errorSignal=true;
		}if(password.length()<=7) {
			System.out.println(password.length());
			redirectAttributes.addFlashAttribute("passwordError",true);
			errorSignal=true;
		}if(!matcher.matches()) {
			redirectAttributes.addFlashAttribute("emailError",true);
			errorSignal=true;
		}if(!repository.testEmail(email)) {
			redirectAttributes.addFlashAttribute("emailExistsError",true);
			errorSignal=true;
		}
		

		
		if(errorSignal) {
		
			redirectAttributes.addFlashAttribute("fName",fName);
			redirectAttributes.addFlashAttribute("mName", mName);
			redirectAttributes.addFlashAttribute("lName", lName);
			redirectAttributes.addFlashAttribute("email", email);
			redirectAttributes.addFlashAttribute("password", password);
		    return "redirect:/register";
			
		}
			
		User user=new User();
		user.setFirstName(fName);
		user.setLastName(lName);	
		user.setMiddleName(mName);
		user.setJoinedOn(LocalDateTime.now());
		UserDetail userdetail=new UserDetail();
		userdetail.setEmail(email);
		userdetail.setPass(password);
		userdetail.setUser(user);
		
		model.addAttribute("userdetail",userdetail);
		
		MailSender.send(code,email);
		request.getSession().setAttribute("code", code);
		return "verification";

	}
	
	
	@GetMapping("/re-verification")
	public String tryAgain(){
		return "verification";
	}
	
	
	@PostMapping("/verification2")
	public String testVerification(HttpServletRequest request,RedirectAttributes redirectAttributes) {
		UserRepository repository= new UserRepository ();
		String fName = request.getParameter("fname");
		String mName = request.getParameter("mname");
		String lName = request.getParameter("lname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		
		
		if(fName.length()==0||lName.length()==0||email.length()==0||password.length()==0) {
			redirectAttributes.addFlashAttribute("formError", true);
			return "redirect:/register";
		}
		
		byte[] passwordByte = DigestUtils.md5Digest(password.getBytes());
		String passwordHashed=new String(passwordByte);
		
		User user=new User();
		user.setFirstName(fName);
		user.setLastName(lName);	
		user.setMiddleName(mName);
		user.setJoinedOn(LocalDateTime.now());
		UserDetail userdetail=new UserDetail();
		userdetail.setEmail(email);
		userdetail.setPass(password);
		userdetail.setUser(user);

		String codeRecieved=request.getParameter("vcode");
		String codeSent =(String) request.getSession().getAttribute("code");

		if(codeRecieved.equals(codeSent)) {
			userdetail.setPass(passwordHashed);
			repository.createUser(userdetail);
			return "redirect:/login";
		}
		redirectAttributes.addFlashAttribute("codeError",true);
		redirectAttributes.addFlashAttribute("userdetail",userdetail);
		

		return "redirect:/re-verification";
		
	}

}
