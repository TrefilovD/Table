package com.example.table;

public class UserData {
    //костыль
    //костыль
    public String name;
    public String surname;
    public Integer age;
    public Integer rate;
    public String email;
    public String about;
    public String [] games;
    public String [] hashtags;
    public Integer [] hostedEvents;
    public Integer [] currentEvents;
    public String phone;
    public String sex;
    public String userID;

    public UserData(String name,
                    String surname,
                    String userID,
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
        this.userID = userID;
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

    public UserData() {

    }

    public String getUserID (){
        return this.userID;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }
}


