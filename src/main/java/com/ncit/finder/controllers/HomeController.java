package com.ncit.finder.controllers;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ncit.finder.db.DBResponse;
import com.ncit.finder.models.JoinRequest;
import com.ncit.finder.models.Post;
import com.ncit.finder.models.User;
import com.ncit.finder.repository.PostRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

	@GetMapping("/")
	public String index(@RequestParam(required=false) String before, Model model) {
		PostRepository repository = new PostRepository();
		LocalDateTime beforeDateTime = LocalDateTime.now();
		if(before != null && !before.isEmpty()){
			beforeDateTime = LocalDateTime.parse(before);
		}
		List<Post> posts = repository.getPosts(5, beforeDateTime);
		model.addAttribute("posts", posts);
		if(posts.size() > 0 ){
			model.addAttribute("oldestDate", posts.get(posts.size() - 1).getPostedDateTime());
			model.addAttribute("hasPosts", true);
		}
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

	@GetMapping("/{postId}/join-requests")
	public String getPostWithJoinRequests(@PathVariable int postId, Model model){
		PostRepository repository = new PostRepository();
	 	Post post = repository.getPostWithJoinRequests(postId);
		model.addAttribute("post", post);
		return "joinrequests";
	}



	@PostMapping("/{postId}/join-requests")
	public String addJoinRequest(@PathVariable int postId, RedirectAttributes redirectAttributes){
		Post post = new Post();
		post.setId(postId);

		User user = new User();
		user.setId(1);

		JoinRequest joinRequest = new JoinRequest();
		joinRequest.setRequestedOn(LocalDateTime.now());
		joinRequest.setPost(post);
		joinRequest.setUser(user);

		PostRepository repository = new PostRepository();
		DBResponse response = repository.addJoinRequest(joinRequest);

		redirectAttributes.addFlashAttribute("joinRequestResponse", response);
		
		
		return "redirect:/";
	}
	
}
