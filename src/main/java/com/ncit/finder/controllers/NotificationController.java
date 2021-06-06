package com.ncit.finder.controllers;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ncit.finder.models.Notification;
import com.ncit.finder.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotificationController {

    private NotificationRepository notificationRepository;

    private static final int SIZE = 30;

    @Autowired
    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @GetMapping("/notifications")
    public String showAllNotifications(@RequestParam(required = false) String before, 
                                        HttpServletRequest request, Model model){
        int userId;
		if (request.getSession().getAttribute("id") != null) {
			userId = Integer.parseInt(request.getSession().getAttribute("id").toString());
		}else{
            return "redirect:/guest";
        }
        LocalDateTime beforeDateTime = LocalDateTime.now();
		if (before != null && !before.isEmpty()) {
			beforeDateTime = LocalDateTime.parse(before);
		}
        List<Notification> notifications = notificationRepository.getAllNotifications(userId, SIZE, beforeDateTime);
        if(notifications.size()>0){
            model.addAttribute("hasNotifications", true);
            model.addAttribute("notifications", notifications);
        }
        System.out.println(notifications);
        return "notifications";
    }

    @GetMapping("/notifications/{nid}")
    public String handleNotificationClick(@PathVariable int nid){
        Notification notification = notificationRepository.getNotificationById(nid);
        if(notification == null){
            return "redirect:/notifications";
        }
        // mark nid as seen
        notificationRepository.markAsSeen(nid);
        System.out.println("Notification type "+notification.getNotificationType());
        if(notification.getNotificationType().equals(Notification.JOIN_REQUEST)){
            return "redirect:/"+notification.getPost().getId()+"/join-requests";
        }
        // else it is a comment
        return "redirect:/post/"+notification.getPost().getId();
        
    }

    
}
