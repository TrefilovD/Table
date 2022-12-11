package com.example.table;

import android.app.Application;

import io.appwrite.Client;
import io.appwrite.models.Session;
//костыль
public class TableApp extends Application {
    // Давайте не будем извращаться с приватными полями,
    // пишем и читаем из публичных

    public String endpointID;
    public String projectID;
    public String databaseID;
    public String user_collectionID;
    public String event_collectionID;
    public String notification_collectionID;

     {
        endpointID = "http://tableapp.online:8111/v1";
        projectID = "6356860bb84da9132dfd";
        databaseID = "635abed829e099dfcd5c";
        user_collectionID = "635abf1ceefe2d36771b";
        event_collectionID = "635abf30a88c059105e3";
        notification_collectionID = "635abf7dd8fadf48772e";
    }

    public Client appwriteClient;
    //public AuthorisationData sessionData;
    public Session session;
    public UserData personalData;
}
