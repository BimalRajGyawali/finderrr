package com.ncit.finder.controllers;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ncit.finder.models.Comment;
import com.ncit.finder.models.HashTag;
import com.ncit.finder.models.Notification;
import com.ncit.finder.models.Post;
import com.ncit.finder.models.User;
import com.ncit.finder.repository.CommentRepository;
import com.ncit.finder.repository.FollowingRepository;
import com.ncit.finder.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CommentController {
	private CommentRepository commentRepository;
	private FollowingRepository followingRepository;
	private NotificationRepository notificationRepository;

	@Autowired
	public CommentController(CommentRepository commentRepository, FollowingRepository followingRepository,
			NotificationRepository notificationRepository) {
		this.commentRepository = commentRepository;
		this.followingRepository = followingRepository;
		this.notificationRepository = notificationRepository;
	}


	@GetMapping("/post/{post_id}")
	public String postWithComment(Model model, @PathVariable("post_id") String post_id, HttpServletRequest request) {
		Post post = new Post();
		post = commentRepository.getPost(Integer.parseInt(post_id));
		System.out.println(post);
		model.addAttribute("post", post);
		model.addAttribute("currentDateTime", LocalDateTime.now());

		if (request.getSession().getAttribute("id") != null) {
			int userId = Integer.parseInt(request.getSession().getAttribute("id").toString());
			List<HashTag> recommendedHashTags = followingRepository.recommendedHashTags(userId, 8);
			if (recommendedHashTags.size() > 0) {
				model.addAttribute("recommendedHashTags", recommendedHashTags);
				model.addAttribute("hasRecommendations", true);
			} else {
				model.addAttribute("hasRecommendations", false);
			}
		}
		return "comment";

	}

	@PostMapping("/write-comment")
	public String createComment(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String post_content = request.getParameter("form_comment_content");
		int post_id = Integer.parseInt(request.getParameter("post_id"));
		int comments_count = Integer.parseInt(request.getParameter("comments_count"));

		User user = new User();
		user.setId((int) request.getSession().getAttribute("id"));

		Comment comment = new Comment();
		comment.setUser(user);

		comment.setContent(post_content);
		comment.setCommentedOn(LocalDateTime.now());

		boolean status = commentRepository.createComment(comment, post_id, comments_count);

		if(status){
				Post post = new Post();
				post.setId(post_id);
		
				Notification notification = new Notification();
				notification.setInitiator(user);
				notification.setPost(post);
				notification.setSeen(false);
				notification.setNotificationType(Notification.COMMENT);
				notification.setInitiatedOn(LocalDateTime.now());
				notificationRepository.save(notification);
			
		}
		redirectAttributes.addFlashAttribute("success", status);
		redirectAttributes.addFlashAttribute("failure", !status);
		return "redirect:/post/" + post_id;
	}
}
