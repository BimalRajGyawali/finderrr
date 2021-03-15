package com.ncit.finder.controllers;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ncit.finder.models.Post;
import com.ncit.finder.models.User;
import com.ncit.finder.repository.PostRepository;

@Controller
public class HomeController {

	@GetMapping("/")
	public String index(Model model) {
		PostRepository repository = new PostRepository();
		List<Post> posts = repository.getPosts();
		model.addAttribute("posts", posts);
		model.addAttribute("currentDateTime", LocalDateTime.now());
		return "home";
	}
	
	@PostMapping("/create-post")
	public String createPost(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String postContent = request.getParameter("post-content");
		
		User user = new User();
		user.setId(1);
		user.setFirstName("Bimal");
		user.setMiddleName("Raj");
		user.setLastName("Gyawali");
		
		Post post = new Post();
		post.setContent(postContent);
		post.setPostedDateTime(LocalDateTime.now());
		post.setUser(user);
		
		PostRepository repository = new PostRepository();
		boolean status = repository.createPost(post);
		
		
		redirectAttributes.addFlashAttribute("success", status);
		redirectAttributes.addFlashAttribute("failure", !status);
		
		return "redirect:/";
	}
	
}
