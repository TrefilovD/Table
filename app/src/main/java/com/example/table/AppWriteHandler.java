package com.example.table;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import io.appwrite.Client;

public class AppWriteHandler extends AppCompatActivity {

    //параметры, которые нужны для обращения к серверу
    private static String endpointID = "http://tableapp.online:8111/v1";
    private static String projectID = "6356860bb84da9132dfd";
    private static String databaseID = "635abed829e099dfcd5c";
    private static String user_collectionID = "635abf1ceefe2d36771b";
    private static String event_collectionID = "635abf30a88c059105e3";
    private static String notification_collectionID = "635abf7dd8fadf48772e";

    private final Client client = new Client(getApplicationContext())
                .setEndpoint(endpointID)
                .setProject(projectID);


}
