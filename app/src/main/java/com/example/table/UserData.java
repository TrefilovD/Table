package com.example.table;

public class UserData {

    public String name;
    private String surname;
    private Integer age;
    private Integer rate;
    private String email;
    private String about;
    private String [] games;
    private String [] hashtags;
    private Integer [] hostedEvents;
    private Integer [] currentEvents;
    private String phone;
    private String sex;
    private String userID;

    public UserData(){};

    public UserData(String name,
                    String surname,
                    Integer age,
                    Integer rate,
                    String email,
                    String about,
                    String[] games,
                    String[] hashtags,
                    Integer[] hostedEvents,
                    Integer[] currentEvents,
                    String phone,
                    String sex
    ) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.rate = rate;
        this.email = email;
        this.about = about;
        this.games = games;
        this.hashtags = hashtags;
        this.hostedEvents = hostedEvents;
        this.currentEvents = currentEvents;
        this.phone = phone;
        this.sex = sex;
    }

    public String getUserID (){
        return this.userID;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }
}

