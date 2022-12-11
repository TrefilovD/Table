package com.example.table.fragments;

public class MyNotifications {
    private Integer notification_image;
    private String event_name;
    private String date;
    private String time;
    private Integer avatar_image;
    private String host_name;
    private String type_of_notification;

    public MyNotifications(Integer notification_image, String event_name, String date, String time, Integer avatar_image, String host_name, String type_of_notification){

            this.notification_image = notification_image;
            this.event_name = event_name;
            this.date = date;
            this.time = time;
            this.avatar_image = avatar_image;
            this.host_name = host_name;
            this.type_of_notification = type_of_notification;
    }

    public Integer getNotification_image() {
        return notification_image;
    }

    public void setNotification_image(Integer notification_image) {
        this.notification_image = notification_image;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getAvatar_image() {
        return avatar_image;
    }

    public void setAvatar_image(Integer avatar_image) {
        this.avatar_image = avatar_image;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getType_of_notification() {
        return type_of_notification;
    }

    public void setType_of_notification(String type_of_notification) {
        this.type_of_notification = type_of_notification;
    }
}
