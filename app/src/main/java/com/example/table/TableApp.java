package com.example.table;

import android.app.Application;

import com.example.table.Event.Event;

import java.util.ArrayList;

import io.appwrite.Client;
import io.appwrite.models.Session;
//костыль
public class TableApp extends Application {
    // Давайте не будем извращаться с приватными полями,
    // пишем и читаем из публичных
    public Client appwriteClient;
    //public AuthorisationData sessionData;
    public Session session;

    public ArrayList<Event> Events;
}
