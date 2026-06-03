package com.project.notificationapp.service;

import com.project.notificationapp.model.Notification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    private List<Notification> notifications = new ArrayList<>();

    public NotificationService() {

        notifications.add(
                new Notification(
                        1,
                        "Placement Drive",
                        "TCS Hiring Drive",
                        "Placement"
                )
        );

        notifications.add(
                new Notification(
                        2,
                        "Result",
                        "Semester Result Declared",
                        "Result"
                )
        );
    }

    public List<Notification> getAllNotifications() {
        return notifications;
    }
}