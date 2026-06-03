package com.project.notificationapp.model;

public class Notification {

    private int id;
    private String title;
    private String message;
    private String type;

    public Notification() {
    }

    public Notification(int id, String title, String message, String type) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}