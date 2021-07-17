package com.ncit.finder.controllers;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ncit.finder.functionality.EmitterService;
import com.ncit.finder.models.HashTag;
import com.ncit.finder.models.Notification;
import com.ncit.finder.repository.FollowingRepository;
import com.ncit.finder.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final FollowingRepository followingRepository;
    private final EmitterService emitterService;

    private static final int SIZE = 30;

    public NotificationController(NotificationRepository notificationRepository,
            FollowingRepository followingRepository, EmitterService emitterService) {
        this.notificationRepository = notificationRepository;
        this.followingRepository = followingRepository;
        this.emitterService = emitterService;
    }

    @GetMapping("/notifications")
    public String showAllNotifications(@RequestParam(required = false) String before, HttpServletRequest request,
            Model model) {
        if (request.getSession().getAttribute("id") == null) {
            return "redirect:/guest";
        }

        int userId = Integer.parseInt(request.getSession().getAttribute("id").toString());
        List<HashTag> recommendedHashTags = followingRepository.recommendedHashTags(userId, 8);
        if (recommendedHashTags.size() > 0) {
            model.addAttribute("recommendedHashTags", recommendedHashTags);
            model.addAttribute("hasRecommendations", true);
        } else {
            model.addAttribute("hasRecommendations", false);
        }

        LocalDateTime beforeDateTime = LocalDateTime.now();
        if (before != null && !before.isEmpty()) {
            beforeDateTime = LocalDateTime.parse(before);
        }
        List<Notification> notifications = notificationRepository.getAllNotifications(userId, SIZE, beforeDateTime);
        if (notifications.size() > 0) {
            model.addAttribute("hasNotifications", true);
            model.addAttribute("notifications", notifications);
            LocalDateTime oldestDate = notifications.get(notifications.size() - 1).getInitiatedOn();
            model.addAttribute("oldestDate", oldestDate);
            List<Notification> olderNotifications = notificationRepository.getAllNotifications(userId, SIZE,
                    oldestDate);
            boolean hasOlderNotifications = olderNotifications.size() != 0;
            model.addAttribute("hasOlderNotifications", hasOlderNotifications);

        }
        int notificationCount = notificationRepository.getNotificationCount(userId);
		model.addAttribute("notificationCount", notificationCount);
        return "notifications";
    }

    @GetMapping("/notifications/{nid}")
    public String handleNotificationClick(@PathVariable int nid) {
        Notification notification = notificationRepository.getNotificationById(nid);
        if (notification == null) {
            return "redirect:/notifications";
        }
        // mark nid as seen
        notificationRepository.markAsSeen(nid);
        System.out.println("Notification type " + notification.getNotificationType());
        if (notification.getNotificationType().equals(Notification.JOIN_REQUEST)) {
            return "redirect:/" + notification.getPost().getId() + "/join-requests";
        }
        // else it is a comment
        return "redirect:/post/" + notification.getPost().getId();

    }

    @GetMapping("/subscription")
    public SseEmitter subsribe() {
        System.out.println("subscribing...");

        SseEmitter sseEmitter = new SseEmitter(24 * 60 * 60 * 1000l);
        emitterService.addEmitter(sseEmitter);

        System.out.println("subscribed");
        return sseEmitter;
    }

}
