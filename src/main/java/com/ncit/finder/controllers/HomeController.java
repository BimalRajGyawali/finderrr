package com.ncit.finder.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ncit.finder.db.DBResponse;
import com.ncit.finder.models.HashTag;
import com.ncit.finder.models.JoinRequest;
import com.ncit.finder.models.Post;
import com.ncit.finder.models.Status;
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
		List<Post> posts = repository.getDetailedPosts(5, beforeDateTime);
		model.addAttribute("posts", posts);
		if(posts.size() > 0 ){
			model.addAttribute("oldestDate", posts.get(posts.size() - 1).getPostedDateTime());
			model.addAttribute("hasPosts", true);
		}
		return "home";
	}

	@GetMapping("/posts/hashtag/{hashtag}")
	public String getPostsFromHashTag(@PathVariable String hashtag, @RequestParam(required = false)String before, Model model){
		PostRepository repository = new PostRepository();
		LocalDateTime beforeDateTime = LocalDateTime.now();
		if(before != null && !before.isEmpty()){
			beforeDateTime = LocalDateTime.parse(before);	
		}
		List<Post> posts = repository.getPostsFromHashTag(hashtag, 5, beforeDateTime);
		model.addAttribute("posts", posts);
		if(posts.size() > 0 ){
			model.addAttribute("oldestDate", posts.get(posts.size() - 1).getPostedDateTime());
			model.addAttribute("hasPosts", true);
		}
		model.addAttribute("requestedHashTag", hashtag);
		return "posts";
	}
	

	@GetMapping("/create-post")
	public String getCreatePostPage(HttpServletRequest request){
		if(request.getSession().getAttribute("id")==null) {
			return "redirect:/login/post/create";
		}
		return "createpost";
	}

	@PostMapping("/create-post")
	public String createPost(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String postContent = request.getParameter("post-content");
		String hashTagsString = request.getParameter("hashtags");
		List<HashTag> hashTags = new ArrayList<>();
		String postStatus = request.getParameter("post-status");
		Status pStatus = Status.valueOf(postStatus);		 
		for(String hashTag : hashTagsString.split(",")){
			HashTag h = new HashTag();
			h.setTitle(hashTag);
			hashTags.add(h);
		}

		User user = new User();
		user.setId((int)request.getSession().getAttribute("id"));
		user.setFirstName((String) request.getSession().getAttribute("firstname"));
		user.setMiddleName((String) request.getSession().getAttribute("middlename"));
		user.setLastName("lastname");
		
		Post post = new Post();
		post.setContent(postContent);
		post.setPostedDateTime(LocalDateTime.now());
		post.setUser(user);
		post.setHashTags(hashTags);
		post.setStatus(pStatus);
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
		model.addAttribute("hasJoinRequests", post.getUsersRequestingToJoin().size() > 0);
		System.out.println(post.getUsersRequestingToJoin().size());
		return "joinrequests";
	}



	@PostMapping("/{postId}/join-requests")
	public String addJoinRequest(@PathVariable int postId, RedirectAttributes redirectAttributes,HttpServletRequest request){
		Post post = new Post();
		post.setId(postId);

		User user = new User();
		user.setId((int) request.getSession().getAttribute("id"));

		JoinRequest joinRequest = new JoinRequest();
		joinRequest.setRequestedOn(LocalDateTime.now());
		joinRequest.setPost(post);
		joinRequest.setUser(user);

		PostRepository repository = new PostRepository();
		DBResponse response = repository.addJoinRequest(joinRequest);

		redirectAttributes.addFlashAttribute("joinRequestResponse", response);
		
		
		return "redirect:/"+post.getId()+"/join-requests";
	}
	
	@GetMapping("/editpost/{postId}")
	public String getEditPostPage(@PathVariable int postId, Model model){
		PostRepository repository = new PostRepository();
		Post post = repository.getPostById(postId);
		model.addAttribute("post", post);
		boolean ongoingStatus = true;
		boolean completedStatus = false;

		if(post != null){
			if(post.getStatus() == Status.ongoing){
				ongoingStatus = true;
			}else if(post.getStatus() == Status.completed){
				completedStatus = true;
				ongoingStatus = false;
			}
		}
		model.addAttribute("ongoingStatus", ongoingStatus);
		model.addAttribute("completedStatus", completedStatus);
		return "editpost";
	}

	@PostMapping("/edit-post")
	public String editPost(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		int id = Integer.parseInt(request.getParameter("post-id"));
		String postContent = request.getParameter("post-content");
		String hashTagsString = request.getParameter("hashtags");
		List<HashTag> hashTags = new ArrayList<>();
		String postStatus = request.getParameter("post-status");
		Status pStatus = Status.valueOf(postStatus);		 
		for(String hashTag : hashTagsString.split(",")){
			HashTag h = new HashTag();
			h.setTitle(hashTag);
			hashTags.add(h);
		}

		
		Post post = new Post();
		post.setId(id);
		post.setContent(postContent);
		post.setHashTags(hashTags);
		post.setStatus(pStatus);
		PostRepository repository = new PostRepository();
		boolean status = repository.updatePost(post);
		
		
		redirectAttributes.addFlashAttribute("updateSuccess", status);
		redirectAttributes.addFlashAttribute("updateFailure", !status);
		
		return "redirect:/";
	}

	@PostMapping("/delete-post")
	public String deletePost(HttpServletRequest request, RedirectAttributes redirectAttributes){
		int postId = Integer.parseInt(request.getParameter("post-id"));
		PostRepository repository = new PostRepository();
		boolean status = repository.deletePost(postId);
		redirectAttributes.addFlashAttribute("deleteSuccess", status);
		redirectAttributes.addFlashAttribute("deleteFailure", !status);
		return "redirect:/";
	}
	
	
	
	

	
	
}
