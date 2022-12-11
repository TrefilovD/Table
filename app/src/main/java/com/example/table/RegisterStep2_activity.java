package com.example.table;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import io.appwrite.exceptions.AppwriteException;
import io.appwrite.services.Account;
import io.appwrite.services.Databases;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class RegisterStep2_activity extends AppCompatActivity {
    TableApp myApp;
    Account account;
    String email, password, name, surname, accountId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myApp = (TableApp) getApplicationContext();
        this.account = new Account(myApp.appwriteClient);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step2);
        Bundle arguments = getIntent().getExtras();
        this.email = arguments.get("email").toString();
        this.password = arguments.get("password").toString();
        this.name = arguments.get("name").toString();
        this.surname = arguments.get("surname").toString();
        this.accountId = arguments.get("accountId").toString();

    }

    public void DoneButtonClicked(View view) throws AppwriteException {
        TextView editAboutYou = (TextView) findViewById(R.id.editTellaboutU);
        TextView editLoveGames = (TextView) findViewById(R.id.editlovegames);
        TextView editHashTags = (TextView) findViewById(R.id.register_editHashTags);
        String aboutYou = editAboutYou.getText().toString();
        String [] loveGames = editLoveGames.getText().toString().trim().split(" +");;
        String [] hashTags = editHashTags.getText().toString().trim().split(" +");;
        UserData userData = new UserData(this.name,this.surname, this.accountId,null,null,this.email, aboutYou, loveGames, hashTags, null, null, null, null);
        myApp.personalData = userData;
        Gson g = new Gson();
        String json = g.toJson(userData);
        Databases databases = new Databases(this.myApp.appwriteClient);
        databases.createDocument(
                "635abed829e099dfcd5c",
                "635abf1ceefe2d36771b",
                "unique()",
                json,
                new Continuation<Object>() {
                    @NotNull
                    @Override
                    public CoroutineContext getContext() {
                        return EmptyCoroutineContext.INSTANCE;
                    }

                    @Override
                    public void resumeWith(@NotNull Object o) {
                        try {
                            if (o instanceof Result.Failure) {
                                Result.Failure failure = (Result.Failure) o;
                                throw failure.exception;
                            } else {
                                Log.d("Appwrite", o.toString());

                                goToAccountActivity();
                            }
                        } catch (Throwable th) {
                            Log.e("REGISTRATION ERROR", th.toString());
                        }
                    }
                }
        );
    }

    public void goToAccountActivity() {
        Intent intent = new Intent(this, PersonalInfoActivity.class);
        startActivity(intent);
    }
}