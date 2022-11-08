package com.example.table;

import android.app.Application;
import android.util.Log;

import com.example.table.backend_schemes.AuthorisationData;

import io.appwrite.Client;

public class TableApp extends Application {
    // Давайте не будем извращаться с приватными полями,
    // пишем и читаем из публичных
    public Client appwriteClient;
    public AuthorisationData sessionData;

}
