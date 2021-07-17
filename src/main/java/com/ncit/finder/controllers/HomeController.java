package com.ncit.finder.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ncit.finder.db.Response;
import com.ncit.finder.functionality.EmitterService;
import com.ncit.finder.functionality.LocalDateTimeParser;
import com.ncit.finder.models.HashTag;
import com.ncit.finder.models.JoinRequest;
import com.ncit.finder.models.Notification;
import com.ncit.finder.models.Post;
import com.ncit.finder.models.Status;
import com.ncit.finder.models.User;
import com.ncit.finder.repository.FollowingRepository;
import com.ncit.finder.repository.NotificationRepository;
import com.ncit.finder.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
	private final PostRepository postRepository;
	private final FollowingRepository followingRepository;
	private final NotificationRepository notificationRepository;
	private final EmitterService emitterService;

	private static final int POSTS_SIZE = 50;

	public HomeController(PostRepository postRepository, FollowingRepository followingRepository,
			NotificationRepository notificationRepository, EmitterService emitterService) {
		this.postRepository = postRepository;
		this.followingRepository = followingRepository;
		this.notificationRepository = notificationRepository;
		this.emitterService = emitterService;
	}

	@GetMapping("/guest")
	public String guestHome(@RequestParam(required = false) String before, Model model) {
		LocalDateTime beforeDateTime = LocalDateTimeParser.parseOrGetCurrent(before);
		List<Post> posts = postRepository.getDetailedPosts(POSTS_SIZE, beforeDateTime);
		model.addAttribute("posts", posts);

		if (posts.size() > 0) {
			LocalDateTime oldestDateTime = posts.get(posts.size() - 1).getPostedDateTime();
			model.addAttribute("oldestDate", oldestDateTime);
			model.addAttribute("hasPosts", true);
			List<Post> olderPosts = postRepository.getDetailedPosts(POSTS_SIZE, oldestDateTime);
			boolean hasOlderPosts = olderPosts.size() != 0 ;
			model.addAttribute("hasOlderPosts", hasOlderPosts);
		}
		return "home";
	}

	@GetMapping("/")
	public String index(@RequestParam(required = false) String before, Model model, HttpServletRequest request) {
		if(request.getSession().getAttribute("id") == null){
			return before != null ? "redirect:/guest?before="+before :"redirect:/guest";
		}
		LocalDateTime beforeDateTime = LocalDateTimeParser.parseOrGetCurrent(before);

		int userId = Integer.parseInt(request.getSession().getAttribute("id").toString());

		List<Post> posts = postRepository.getRecommendedPosts(userId, POSTS_SIZE, beforeDateTime);
		model.addAttribute("posts", posts);

		List<HashTag> recommendedHashTags = followingRepository.recommendedHashTags(userId, 8);

		if (recommendedHashTags.size() > 0) {
			model.addAttribute("recommendedHashTags", recommendedHashTags);
			model.addAttribute("hasRecommendations", true);
		} else {
			model.addAttribute("hasRecommendations", false);
		}
		if (posts.size() > 0) {
			LocalDateTime oldestDateTime = posts.get(posts.size() - 1).getPostedDateTime();
			model.addAttribute("oldestDate", oldestDateTime);
			model.addAttribute("hasPosts", true);
			List<Post> olderPosts = postRepository.getRecommendedPosts(userId, POSTS_SIZE, oldestDateTime);
			boolean hasOlderPosts = olderPosts.size() != 0;
			model.addAttribute("hasOlderPosts", hasOlderPosts);
		}
		int notificationCount = notificationRepository.getNotificationCount(userId);
		model.addAttribute("notificationCount", notificationCount);
		return "home";
	}

	@GetMapping("/explore")
	public String explore(@RequestParam(required = false) String before,
							 Model model, HttpServletRequest request) {
		if(request.getSession().getAttribute("id") == null){
			return before != null ? "redirect:/guest?before="+before :"redirect:/guest";
		}
		LocalDateTime beforeDateTime = LocalDateTimeParser.parseOrGetCurrent(before);

		int userId = Integer.parseInt(request.getSession().getAttribute("id").toString());

		List<Post> posts = postRepository.getExploringPosts(userId, POSTS_SIZE, beforeDateTime);
		model.addAttribute("posts", posts);
		List<HashTag> recommendedHashTags = followingRepository.recommendedHashTags(userId, 8);
		if (recommendedHashTags.size() > 0) {
			model.addAttribute("recommendedHashTags", recommendedHashTags);
			model.addAttribute("hasRecommendations", true);
		} else {
			model.addAttribute("hasRecommendations", false);
		}
		if (posts.size() > 0) {
			LocalDateTime oldestDateTime = posts.get(posts.size() - 1).getPostedDateTime();
			model.addAttribute("oldestDate", oldestDateTime);
			model.addAttribute("hasPosts", true);
			List<Post> olderPosts = postRepository.getExploringPosts(userId, POSTS_SIZE, oldestDateTime);
			boolean hasOlderPosts = olderPosts.size() != 0;
			model.addAttribute("hasOlderPosts", hasOlderPosts);
		}
		int notificationCount = notificationRepository.getNotificationCount(userId);
		model.addAttribute("notificationCount", notificationCount);

		return "explore";
	}

	
	@GetMapping("/posts/hashtag/{hashtag}")
	public String getPostsFromHashTag(@PathVariable String hashtag, @RequestParam(required = false) String before,
			Model model, HttpServletRequest request) {
				hashtag = HashTag.sanitize(hashtag);
		LocalDateTime beforeDateTime = (before != null && !before.isEmpty()) ?
				LocalDateTime.parse(before) : LocalDateTime.now();

		List<Post> posts = postRepository.getPostsFromHashTag(hashtag, POSTS_SIZE, beforeDateTime);
		model.addAttribute("posts", posts);
		System.out.println("posts size "+posts.size());

		if (posts.size() > 0) {
			LocalDateTime oldestDateTime = posts.get(posts.size() - 1).getPostedDateTime();
			model.addAttribute("oldestDate", oldestDateTime);
			model.addAttribute("hasPosts", true);
			List<Post> olderPosts = postRepository.getPostsFromHashTag(hashtag, POSTS_SIZE, oldestDateTime);
			boolean hasOlderPosts = olderPosts.size() != 0;
			model.addAttribute("hasOlderPosts", hasOlderPosts);
		}
		
		boolean isHashTagPresent = followingRepository.isHashTagPresent(hashtag);
		model.addAttribute("requestedHashTag", hashtag);
		model.addAttribute("isHashTagPresent", isHashTagPresent);

		if (request.getSession().getAttribute("id") != null) {
			int userId = Integer.parseInt(request.getSession().getAttribute("id").toString());
			boolean hasFollowed = followingRepository.hasFollowed(userId, hashtag);
			model.addAttribute("hasFollowed", hasFollowed);
			List<HashTag> recommendedHashTags = followingRepository.recommendedHashTags(userId, 8);
			if (recommendedHashTags.size() > 0) {
				model.addAttribute("recommendedHashTags", recommendedHashTags);
				model.addAttribute("hasRecommendations", true);
			} else {
				model.addAttribute("hasRecommendations", false);
			}
		}
		if (request.getSession().getAttribute("id") != null) {
			int userId = Integer.parseInt(request.getSession().getAttribute("id").toString());
			int notificationCount = notificationRepository.getNotificationCount(userId);
			model.addAttribute("notificationCount", notificationCount);
		}

		return "posts";
	}

	@GetMapping("/posts/hashtag/")
	public String redirectToGetPostsFromHashtag(Model model, HttpServletRequest request){
		if (request.getSession().getAttribute("id") != null) {
			int userId = Integer.parseInt(request.getSession().getAttribute("id").toString());

			List<HashTag> recommendedHashTags = followingRepository.recommendedHashTags(userId, 8);
			if (recommendedHashTags.size() > 0) {
				model.addAttribute("recommendedHashTags", recommendedHashTags);
				model.addAttribute("hasRecommendations", true);
			} else {
				model.addAttribute("hasRecommendations", false);
			}
			int notificationCount = notificationRepository.getNotificationCount(userId);
			model.addAttribute("notificationCount", notificationCount);
		}
		return "hashtagerror";
	}

	@GetMapping("/create-post")
	public String getCreatePostPage(HttpServletRequest request, Model model) {
		if (request.getSession().getAttribute("id") == null) {
			return "redirect:/login/post/create";
		}

		int userId = Integer.parseInt(request.getSession().getAttribute("id").toString());
		List<HashTag> recommendedHashTags = followingRepository.recommendedHashTags(userId, 8);
		if (recommendedHashTags.size() > 0) {
			model.addAttribute("recommendedHashTags", recommendedHashTags);
			model.addAttribute("hasRecommendations", true);
		} else {
			model.addAttribute("hasRecommendations", false);
		}
		int notificationCount = notificationRepository.getNotificationCount(userId);
		model.addAttribute("notificationCount", notificationCount);
		return "createpost";

	}

	@PostMapping("/create-post")
	public String createPost(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String postContent = request.getParameter("post-content");
		String hashTagsString = request.getParameter("hashtags");
		List<HashTag> hashTags = new ArrayList<>();
		String postStatus = request.getParameter("post-status");
		Status pStatus = Status.valueOf(postStatus);
		for (String hashTag : hashTagsString.split(",")) {
			HashTag h = new HashTag();
			h.setTitle(hashTag);
			hashTags.add(h);
		}

		User user = new User();
		user.setId((int) request.getSession().getAttribute("id"));
		user.setFirstName((String) request.getSession().getAttribute("firstname"));
		user.setMiddleName((String) request.getSession().getAttribute("middlename"));
		user.setLastName("lastname");

		Post post = new Post();
		post.setContent(postContent);
		post.setPostedDateTime(LocalDateTime.now());
		post.setUser(user);
		post.setHashTags(hashTags);
		post.setStatus(pStatus);
		int generatedId = postRepository.createPost(post);

		redirectAttributes.addFlashAttribute("success", generatedId != 0);
		redirectAttributes.addFlashAttribute("failure", generatedId == 0);

		return "redirect:/"+generatedId+"/join-requests";
	}

	@GetMapping("/{postId}/join-requests")
	public String getPostWithJoinRequests(@PathVariable int postId, Model model, HttpServletRequest request) {
		Post post = postRepository.getPostWithJoinRequests(postId);
		model.addAttribute("post", post);
		
		model.addAttribute("hasJoinRequests", post.getUsersRequestingToJoin().size() > 0);
		System.out.println(post.getUsersRequestingToJoin().size());

		if (request.getSession().getAttribute("id") != null) {
			int userId = Integer.parseInt(request.getSession().getAttribute("id").toString());
			List<HashTag> recommendedHashTags = followingRepository.recommendedHashTags(userId, 8);
			if (recommendedHashTags.size() > 0) {
				model.addAttribute("recommendedHashTags", recommendedHashTags);
				model.addAttribute("hasRecommendations", true);
			} else {
				model.addAttribute("hasRecommendations", false);
			}
			int notificationCount = notificationRepository.getNotificationCount(userId);
			model.addAttribute("notificationCount", notificationCount);
		}
		return "joinrequests";
	}

	@PostMapping("/{postId}/join-requests")
	public String addJoinRequest(@PathVariable int postId, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		Post post = new Post();
		post.setId(postId);

		User user = new User();
		user.setId((int) request.getSession().getAttribute("id"));

		JoinRequest joinRequest = new JoinRequest();
		joinRequest.setRequestedOn(LocalDateTime.now());
		joinRequest.setPost(post);
		joinRequest.setUser(user);

		Response response = postRepository.addJoinRequest(joinRequest);
		int authorId = postRepository.getAuthor(postId).getId();
		// if jr is added successfully and person sending jr is not the author
		if(response.isSuccessStatus() && user.getId() != authorId){
			Notification notification = new Notification();
			notification.setInitiator(user);
			notification.setPost(post);
			notification.setSeen(false);
			notification.setNotificationType(Notification.JOIN_REQUEST);
			notification.setInitiatedOn(LocalDateTime.now());
			notificationRepository.save(notification);
			int notificationCount = notificationRepository.getNotificationCount(authorId);
			emitterService.pushNotification(authorId, notificationCount);

		}

		redirectAttributes.addFlashAttribute("joinRequestResponse", response);

		return "redirect:/" + post.getId() + "/join-requests";
	}

	@GetMapping("/editpost/{postId}")
	public String getEditPostPage(@PathVariable int postId, Model model, HttpServletRequest request) {
		Post post = postRepository.getPostById(postId);
		System.err.println(post);
		int userId;
		if (request.getSession().getAttribute("id") != null) {
			 userId = Integer.parseInt(request.getSession().getAttribute("id").toString());
			if (userId != post.getUser().getId()) {
				return "redirect:/";
			}
		} else {
			return "redirect:/";
		}
		model.addAttribute("post", post);
		boolean ongoingStatus = true;
		boolean completedStatus = false;

		if (post != null) {
			if (post.getStatus() == Status.ongoing) {
				ongoingStatus = true;
			} else if (post.getStatus() == Status.completed) {
				completedStatus = true;
				ongoingStatus = false;
			}
		}
		model.addAttribute("ongoingStatus", ongoingStatus);
		model.addAttribute("completedStatus", completedStatus);
		int notificationCount = notificationRepository.getNotificationCount(userId);
		model.addAttribute("notificationCount", notificationCount);
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
		for (String hashTag : hashTagsString.split(",")) {
			HashTag h = new HashTag();
			h.setTitle(hashTag);
			hashTags.add(h);
		}

		Post post = new Post();
		post.setId(id);
		post.setContent(postContent);
		post.setHashTags(hashTags);
		post.setStatus(pStatus);
		boolean status = postRepository.updatePost(post);

		redirectAttributes.addFlashAttribute("updateSuccess", status);
		redirectAttributes.addFlashAttribute("updateFailure", !status);

		return "redirect:/post/"+post.getId();
	}

	@PostMapping("/delete-post")
	public String deletePost(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		int postId = Integer.parseInt(request.getParameter("post-id"));
		boolean status = postRepository.deletePost(postId);
		redirectAttributes.addFlashAttribute("deleteSuccess", status);
		redirectAttributes.addFlashAttribute("deleteFailure", !status);
		return "redirect:/";
	}

}
