package com.ncit.finder.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ncit.finder.models.User;
import com.ncit.finder.models.UserDetail;
import com.ncit.finder.repository.UserRepository;

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
		UserDetail userDetail=repository.getUserDetail(email, passwordHashed);
		if(userDetail.getUser().getId()==0) {
			redirectAttributes.addFlashAttribute("email",email);
			redirectAttributes.addFlashAttribute("password",password);
			redirectAttributes.addFlashAttribute("error",true);
			return "redirect:/login";
		
		}
		request.getSession().setAttribute("email",email);
		request.getSession().setAttribute("id", userDetail.getUser().getId());
		request.getSession().setAttribute("firstname", userDetail.getUser().getFirstName());
		request.getSession().setAttribute("middlename", userDetail.getUser().getMiddleName());
		request.getSession().setAttribute("lastname", userDetail.getUser().getLastName());
		request.getSession().setAttribute("bio", userDetail.getUser().getBio());
		request.getSession().setAttribute("profile_pic", userDetail.getProfilePic());
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
