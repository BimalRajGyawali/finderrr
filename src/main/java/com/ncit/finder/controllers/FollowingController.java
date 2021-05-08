package com.ncit.finder.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Boolean> follow(@RequestBody Map<String, String> hashtagMap){
        System.out.println(hashtagMap.get("hashtag"));
        String hashtag = hashtagMap.get("hashtag");
        FollowingRepository repository = new FollowingRepository();
        Following following = new Following(1, hashtag, LocalDateTime.now());
        boolean booleanResponse = repository.follow(following);
        HttpStatus httpStatus = HttpStatus.CREATED;
        if(!booleanResponse){
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(booleanResponse, httpStatus);
    }

    @PostMapping("/unfollow")
    public ResponseEntity<Boolean> unfollow(@RequestBody Map<String, String> hashtagMap){
        String hashtag = hashtagMap.get("hashtag");
        FollowingRepository repository = new FollowingRepository();
        Following following = new Following(1, hashtag);
        boolean booleanResponse = repository.unfollow(following);
        HttpStatus httpStatus = HttpStatus.OK;
        if(!booleanResponse){
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(booleanResponse, httpStatus);
    }

    @GetMapping("/recommended-hashtags")
    public String getRecommendateHashTags(Model model){
        FollowingRepository repository = new FollowingRepository();
        List<HashTag> recommendedHashTags = repository.recommendedHashTags(1, -1);
        model.addAttribute("recommededHashtags", recommendedHashTags);
        return "recommendedhashtag.jsp";
    }
}
