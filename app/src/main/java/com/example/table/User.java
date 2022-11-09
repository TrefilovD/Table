package com.example.table;

import java.util.List;

public class User {

    public String name;
    private String surname;
    private Integer age;
    private Integer rate;
    private String email;
    private String about;
    private List<String> games;
    private List<String> hashtags;
    private List<Integer> hostedEvents;
    private List<Integer> currentEvents;
    private String phone;
    private String sex;
    private String userID;

    public String getUserID (){
        return this.userID;
    }
    public String getGame(){
        return this.games.get(0);
    };
    public void setUserID(String userID){
        this.userID = userID;
    }
}


