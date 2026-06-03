package com.project.notificationapp.controller;

import com.project.notificationapp.model.Notification;
import com.project.notificationapp.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping("/notifications")
    public List<Notification> getNotifications() {
        return service.getAllNotifications();
    }
}