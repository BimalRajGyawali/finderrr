package com.ncit.finder.controllers;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.ncit.finder.functionality.MailSender;
import com.ncit.finder.functionality.RandomCodeGenerator;
import com.ncit.finder.models.User;
import com.ncit.finder.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

	private UserRepository userRepository;

	@Autowired
	public RegistrationController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/register/post/{post_id}")
	public String createUser(@PathVariable String post_id,Model model) {
		model.addAttribute("post_id",post_id);
		return "register";

	}
	
	@GetMapping({"/register","/register/post"})
	public String createUser() {
		return "redirect:/register/post/none";
	}
	
	@PostMapping("/verification1")
	public String sendVerification(HttpServletRequest request, RedirectAttributes redirectAttributes,Model model) {
		

		String post_id = request.getParameter("post_id");
		System.out.println(post_id);
		String fName = request.getParameter("fname");
		String mName = request.getParameter("mname");
		String lName = request.getParameter("lname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		boolean errorSignal=false;
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		
		if(fName.length()==0) {
			System.out.println("First name cant be empty");
			redirectAttributes.addFlashAttribute("fnameError",true);
			errorSignal=true;
			
		}if(lName.length()==0) {
			redirectAttributes.addFlashAttribute("lnameError",true);
			errorSignal=true;
		}if(password.length()<=7) {
			redirectAttributes.addFlashAttribute("passwordError",true);
			errorSignal=true;
		}if(!matcher.matches()) {
			redirectAttributes.addFlashAttribute("emailError",true);
			errorSignal=true;
		}if(!userRepository.testEmail(email)) {
			redirectAttributes.addFlashAttribute("emailExistsError",true);
			errorSignal=true;
		}if(!password.equals(password2)){
			redirectAttributes.addFlashAttribute("passwordMismatchError",true);
			errorSignal=true;
		}
		

		
		if(errorSignal) {
		
			redirectAttributes.addFlashAttribute("fName",fName);
			redirectAttributes.addFlashAttribute("mName", mName);
			redirectAttributes.addFlashAttribute("lName", lName);
			redirectAttributes.addFlashAttribute("email", email);
			redirectAttributes.addFlashAttribute("password", password);
			redirectAttributes.addFlashAttribute("post_id", post_id);
		    return "redirect:/register/post/"+post_id;
			
		}
		String code=RandomCodeGenerator.generate();
			
		User user=new User();
		user.setFirstName(fName);
		user.setLastName(lName);	
		user.setMiddleName(mName);
		user.setJoinedOn(LocalDateTime.now());
		user.setEmail(email);
		user.setPass(password);
		
		model.addAttribute("user",user);
		model.addAttribute("post_id",post_id);
		
		// MailSender.send(code,email);
		request.getSession().setAttribute("code", code);
		return "verification";

	}
	
	
	@GetMapping("/re-verification")
	public String tryAgain(){
		return "verification";
	}
	
	
	@PostMapping("/verification2")
	public String testVerification(HttpServletRequest request,RedirectAttributes redirectAttributes) {
		System.err.println("entryyy");
		String fName = request.getParameter("fname");
		String mName = request.getParameter("mname");
		String lName = request.getParameter("lname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String post_id = request.getParameter("post_id");
		
		System.err.println(fName+" "+mName);
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
		user.setEmail(email);
		user.setPass(password);
		System.err.println(user);
		String codeRecieved=request.getParameter("vcode");
		String codeSent =(String) request.getSession().getAttribute("code");
		System.err.println(codeRecieved+"  "+codeRecieved);
		if(codeRecieved.equals(codeSent)) {
			user.setPass(passwordHashed);
			userRepository.createUser(user);
			/** for temporary. heroku doesnot support file system */
			userRepository.insertImage("pic.jpeg", email);
			redirectAttributes.addFlashAttribute("verificationSuccess", true);
			return "redirect:/login/post/"+post_id;
		}
		redirectAttributes.addFlashAttribute("codeError",true);
		redirectAttributes.addFlashAttribute("user",user);
		

		return "redirect:/re-verification";
		
	}
	
	@GetMapping("/create-profile")
	public String createProfile(HttpServletRequest request, Model model) {
		if((String) request.getSession().getAttribute("email")==null) {
			return "redirect:/login";
		
		}
		int id = (int) request.getSession().getAttribute("id");
		User user = userRepository.getById(id);
		model.addAttribute("user", user);
		return "createprofile";
		
	}
	
	
	

}
