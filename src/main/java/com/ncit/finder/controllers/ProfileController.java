package com.ncit.finder.controllers;


import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ncit.finder.repository.PostRepository;
import com.ncit.finder.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ncit.finder.models.Post;
import com.ncit.finder.models.User;



@Controller
public class ProfileController {
    
    private PostRepository postRepository;
	private UserRepository userRepository;

    @Autowired
	public ProfileController(PostRepository postRepository,UserRepository userRepository) {
		this.postRepository = postRepository;
		this.userRepository=userRepository;
	}

    @GetMapping("/profile/id/{profile_id}")
	public String createComment(@PathVariable("profile_id") String id,@RequestParam(required = false) String before,HttpServletRequest request,Model model) {
       	LocalDateTime beforeDateTime = LocalDateTime.now();
		if (before != null && !before.isEmpty()) {
			beforeDateTime = LocalDateTime.parse(before);
		}
        List<Post> posts = postRepository.getUserIdSpecificPosts(id,31,beforeDateTime);
		
		int postSize=posts.size();
		if (postSize > 0) {
			if(postSize==31){
				model.addAttribute("morePostsAvailable",true);
				posts.remove(30);
			
			}else{
				model.addAttribute("morePostsAvailable",false);
			}

			model.addAttribute("profileUserId",id);
			model.addAttribute("oldestDate", posts.get(posts.size() - 1).getPostedDateTime());
			
		}else{
			User user=new User();
			user=userRepository.getById(Integer.valueOf(id));
			if(user.getId()==0){
				model.addAttribute("error", true);
			}
			model.addAttribute("singleUser",user);
			
			
		}
		
		model.addAttribute("posts", posts);
        return "profile";
	}
    
    
}
