package com.example.table.Event;


public class Event {
    // features events

    public String name; // required: string
    public Integer minUsersQty; // required: integer
    public Integer maxUsersQty; // required: integer
    public String dateTime; // required: datetime
    public String timeBegin; // not required: datetime
    public String timeEnd; // not required: datetime
    public String city; // required: string
    public String geo; // required: string
    public String[] metro; // required: string[]
    public String description; // required: string
    public String linkToChat; // required: string
    public String details; // required: string
    public String[] hashtags; // required: string[]
    public String status; // required: string
    public String[] participants; // not required: string[]
    public Double price; // not required: double
    public String hostID; // required: string

    public Event() {}

    public Event(
        String name,
        Integer minUsersQty,
        Integer maxUsersQty,
        String dateTime,
        String timeBegin,
        String timeEnd,
        String city,
        String geo,
        String[] metro,
        String description,
        String linkToChat,
        String details,
        String[] hashtags,
        String status,
        String hostID,
        String[] participants,
        Double price
    ) {
        this.name = name;
        this.minUsersQty = minUsersQty;
        this.maxUsersQty = maxUsersQty;
        this.dateTime = dateTime;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.city = city;
        this.geo = geo;
        this.metro = metro;
        this.description = description;
        this.linkToChat = linkToChat;
        this.details = details;
        this.hashtags = hashtags;
        this.status = status;
        this.participants = participants;
        this.price = price;
        this.hostID = hostID;
    }
}
