package com.project.notificationapp.service;

import com.project.notificationapp.model.Notification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.*;

@Service
public class NotificationService {

    @Value("${affordmed.token}")
    private String token;

    public List<Notification> getAllNotifications() {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> entity =
                new HttpEntity<>(headers);

        ResponseEntity<Map> response =
                restTemplate.exchange(
                        "http://4.224.186.213/evaluation-service/notifications",
                        HttpMethod.GET,
                        entity,
                        Map.class
                );

        List<Map<String,Object>> list =
                (List<Map<String,Object>>) response.getBody().get("notifications");

        List<Notification> result = new ArrayList<>();

        for(Map<String,Object> item : list){

            Notification n = new Notification();

            n.setID((String)item.get("ID"));
            n.setType((String)item.get("Type"));
            n.setMessage((String)item.get("Message"));
            n.setTimestamp((String)item.get("Timestamp"));

            result.add(n);
        }

        return result;
    }
}