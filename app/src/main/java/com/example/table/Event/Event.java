package com.example.table.Event;

import java.util.ArrayList;
import java.util.Date;


public class Event {

    // features events
    private String name;
    private String description;
    private String location;
    private Date date;
    private String status;
//    private ArrayList<Hashtag> hashtags;

//    private User organiser;
//    private ArrayList<User> members;

    private int MaxNumberPeople;
    private int MinNumberPeople;

//    private Notification notification; // TODO check android.app.Notification

    public Event() {}

    public Event(
        String name,
        String description,
        String location,
        Date date,
        String status,
//        ArrayList<Hashtag> hashtags,
//        User organiser,
//        ArrayList<User> members,
        int MaxNumberPeople,
        int MinNumberPeople
//        Notification notification
    ) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = date;
        this.status = status;
//        this.hashtags = hashtags;
//        this.organiser = organiser;
//        this.members = members;
        this.MaxNumberPeople = MaxNumberPeople;
        this.MinNumberPeople = MinNumberPeople;
//        this.notification = notification;
    }


    public Event getEventById(Integer id) {
        // обращение к БД
        Event event = new Event();
        return event;
    }

    public ArrayList<Event> getAllEvent(Integer id) {
        // обращение к БД
        ArrayList<Event> events = new ArrayList<Event>();
        return events;
    }

    public void setEvent2DB() {
        // запись текущих данных в БД
    }
}