package com.ncit.finder.controllers;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import com.ncit.finder.models.Comment;
import com.ncit.finder.models.Post;
import com.ncit.finder.models.User;
import com.ncit.finder.repository.CommentRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CommentController {
	@GetMapping("/post/{post_id}")
	public String postWithComment(Model model, @PathVariable("post_id") String post_id) {
		CommentRepository repository = new CommentRepository();
		Post post = new Post();
		post = repository.getPost(Integer.parseInt(post_id));
		System.out.println(post);
		model.addAttribute("post", post);
		model.addAttribute("currentDateTime", LocalDateTime.now());
		return "comment";

	}

	@PostMapping("/write-comment")
	public String createComment(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String post_content = request.getParameter("form_comment_content");
		int post_id = Integer.parseInt(request.getParameter("post_id"));
		int comments_count = Integer.parseInt(request.getParameter("comments_count"));
		// System.out.println(post_content+post_id+comments_count);

		User user = new User();
		user.setId(1);
		
		Comment comment = new Comment();
		comment.setUser(user);
		
		comment.setContent(post_content);
		comment.setCommentedOn(LocalDateTime.now());

		CommentRepository repository = new CommentRepository();
		boolean status = repository.createComment(comment, post_id, comments_count);

		// System.out.println(post_content+post_id+comment+status);

		redirectAttributes.addFlashAttribute("success", status);
		redirectAttributes.addFlashAttribute("failure", !status);
		return "redirect:/post/" + post_id;
	}
}
