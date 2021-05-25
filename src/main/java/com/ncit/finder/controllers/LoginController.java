package com.ncit.finder.controllers;

import javax.servlet.http.HttpServletRequest;

import com.ncit.finder.models.User;
import com.ncit.finder.repository.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	@PostMapping("/loginsubmit/post/{post_id}")
	public String loginUser(HttpServletRequest request,RedirectAttributes redirectAttributes,@PathVariable("post_id") String post_id) {
		System.out.println("Post to redirect="+post_id);
		String email = request.getParameter("email");
		String password=request.getParameter("password");
		byte[] passwordByte = DigestUtils.md5Digest(password.getBytes());
		String passwordHashed=new String(passwordByte);
		UserRepository repository= new UserRepository(); 
		User user = repository.getUserDetail(email, passwordHashed);
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
		request.getSession().setAttribute("bio", user.getBio());
		request.getSession().setAttribute("profile_pic", user.getProfilePic());
		System.out.println("Logged In");
		if(post_id.equals("none")) {
			return "redirect:/";
		}
		if(post_id.equals("create")) {
			return "redirect:/create-post";
		}
		return "redirect:/post/"+post_id;

	}
	
	@GetMapping("/login/post/{post_id}")
	public String loginPage(@PathVariable("post_id") String post_id,Model model) {
		model.addAttribute("post_id", post_id);
		return "login";
	}
	
	@GetMapping({"/login","/login/post"})
	public String loginPage() {
		return "redirect:/login/post/none";
	}

	
}
