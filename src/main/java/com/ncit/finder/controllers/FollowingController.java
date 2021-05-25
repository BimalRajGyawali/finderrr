package com.ncit.finder.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ncit.finder.models.Following;
import com.ncit.finder.models.HashTag;
import com.ncit.finder.repository.FollowingRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class FollowingController {
    
    @PostMapping("/follow")
    public ResponseEntity<Boolean> follow(@RequestBody Map<String, String> hashtagMap, HttpServletRequest request){
        if (request.getSession().getAttribute("id") == null) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        int userId = (int)request.getSession().getAttribute("id");

    
        System.out.println(hashtagMap.get("hashtag"));
        String hashtag = hashtagMap.get("hashtag");
        FollowingRepository repository = new FollowingRepository();
        Following following = new Following(userId, hashtag, LocalDateTime.now());
        boolean booleanResponse = repository.follow(following);
        HttpStatus httpStatus = HttpStatus.CREATED;
        if(!booleanResponse){
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(booleanResponse, httpStatus);
    }

    @PostMapping("/unfollow")
    public ResponseEntity<Boolean> unfollow(@RequestBody Map<String, String> hashtagMap, HttpServletRequest request){
        if (request.getSession().getAttribute("id") == null) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        int userId = (int)request.getSession().getAttribute("id");

        String hashtag = hashtagMap.get("hashtag");
        FollowingRepository repository = new FollowingRepository();
        Following following = new Following(userId, hashtag);
        boolean booleanResponse = repository.unfollow(following);
        HttpStatus httpStatus = HttpStatus.OK;
        if(!booleanResponse){
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(booleanResponse, httpStatus);
    }

    @GetMapping("/recommended-hashtags")
    public String getRecommendateHashTags(Model model, HttpServletRequest request){
        if (request.getSession().getAttribute("id") == null) {
            return "redirect:/guest";
        }
        int userId = (int)request.getSession().getAttribute("id");

        FollowingRepository repository = new FollowingRepository();
        List<HashTag> recommendedHashTags = repository.recommendedHashTags(userId, -1);
        List<HashTag> followedHashTags = repository.followedHashTags(userId);
        if(recommendedHashTags.size() > 0){
			model.addAttribute("recommendedHashTags", recommendedHashTags);
			model.addAttribute("hasRecommendations", true);
		}else{
			model.addAttribute("hasRecommendations", false);
		}

        if(followedHashTags.size() > 0){
            model.addAttribute("followedHashTags", followedHashTags);
			model.addAttribute("hasFollowings", true);
		}else{
			model.addAttribute("hasFollowings", false);
        }
        return "recommendedHashTags";
    }

}
