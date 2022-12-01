package com.example.table;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import org.jetbrains.annotations.NotNull;
import io.appwrite.Client;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.extensions.JsonExtensionsKt;
import io.appwrite.models.DocumentList;
import io.appwrite.models.Session;
import io.appwrite.services.Account;
import io.appwrite.services.Databases;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import android.view.View;

import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity {
    TableApp myApp;
    //костыль
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myApp = (TableApp) getApplicationContext();
        myApp.appwriteClient = new Client(getApplicationContext())
                .setEndpoint("http://tableapp.online:8111/v1")
                .setProject("6356860bb84da9132dfd");
        Account account = new Account(myApp.appwriteClient);
        try {
            CheckLogin(account);
        } catch (AppwriteException e) {
            e.printStackTrace();
        }
    }
    
    public void CheckLogin(Account account) throws AppwriteException {
        account.get(new Continuation<io.appwrite.models.Account>() {
            @NonNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NonNull Object o) {
                try {
                    if (o instanceof Result.Failure) {
                        Result.Failure failure = (Result.Failure) o;
                        throw failure.exception;
                    } else {
                        io.appwrite.models.Account response = (io.appwrite.models.Account) o;
                        String json = response.toString();
                        Log.d("Account get response:", json);
                    }
                } catch (Throwable th) {
                    GoToLoginActivity();
                    Log.e("Account get ERROR", th.toString());
                }
            }
        });
    }

    public void GoToLoginActivity() {
        Intent intent = new Intent(this, Authorisation_activity.class);
        startActivity(intent);
        finish();
    }

    public void GoToAnotherActivity() {

    }
}

