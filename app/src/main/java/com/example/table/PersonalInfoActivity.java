package com.example.table;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.appwrite.Query;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.models.Document;
import io.appwrite.models.DocumentList;
import io.appwrite.services.Databases;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class PersonalInfoActivity extends AppCompatActivity {
    TableApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApp = (TableApp) getApplicationContext();
        try {
            GetPersonalInfo();
        } catch (AppwriteException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.personal_info);
    }

    public void GetPersonalInfo() throws AppwriteException {
        Databases databases = new Databases(this.myApp.appwriteClient);
        databases.listDocuments(
                myApp.databaseID,
                myApp.user_collectionID,
                List.of(
                        Query.Companion.equal("userID", myApp.personalData.userID)
                ),
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
                                DocumentList docs = (DocumentList) o;
                                Gson g = new Gson();
                                Log.d("UserData", docs.getDocuments().get(0).getData().toString());
                                JsonElement jsonElement = g.toJsonTree(docs.getDocuments().get(0).getData());
                                myApp.personalData = g.fromJson(jsonElement, UserData.class);
                                FillPersonalData(myApp.personalData);
                            }
                        } catch (Throwable th) {
                            Log.e("REGISTRATION ERROR", th.toString());
                        }
                    }
                }
        );
    }

    public void FillPersonalData(UserData personalData) {
        TextView name_personalinfo = (TextView) findViewById(R.id.name_personalinfo);
        name_personalinfo.setText(personalData.name);
        TextView surname_personalinfo = (TextView) findViewById(R.id.surname_personalinfo);
        surname_personalinfo.setText(personalData.surname);
        TextView age_personalinfo = (TextView) findViewById(R.id.age_personalinfo);
        if (personalData.age!=null) {
            age_personalinfo.setText(Integer.toString(personalData.age).concat(" " + getAgePostfix(personalData.age)));
        }
        TextView rating_personainfo = (TextView) findViewById(R.id.rating_personainfo);
        if (personalData.rate!=null) {
            rating_personainfo.setText(Integer.toString(personalData.rate));
        }
        TextView number_organz_events_personalinfo = (TextView) findViewById(R.id.number_organz_events_personalinfo);
        number_organz_events_personalinfo.setText(Integer.toString(personalData.hostedEvents.length));
        TextView number_uchast_events_personalinfo = (TextView) findViewById(R.id.number_uchast_events_personalinfo);
        number_uchast_events_personalinfo.setText(Integer.toString(personalData.currentEvents.length));
        TextView aboutmyself_personalinfo = (TextView) findViewById(R.id.aboutmyself_personalinfo);
        aboutmyself_personalinfo.setText(personalData.about);
        TextView lovelygames_personalinfo = (TextView) findViewById(R.id.lovelygames_personalinfo);
        lovelygames_personalinfo.setText(String.join(", ", personalData.games));
        TextView hashtags_personalinfo = (TextView) findViewById(R.id.hashtags_personalinfo);
        hashtags_personalinfo.setText(String.join(" ", personalData.hashtags));
        TextView email_personalinfo = (TextView) findViewById(R.id.email_personalinfo);
        email_personalinfo.setText(String.join(" ", personalData.email));
        TextView number_personalinfo = (TextView) findViewById(R.id.number_personalinfo);
        if (personalData.phone != null) {
            number_personalinfo.setText(String.join(" ", personalData.phone));
        }
    }

    public String getAgePostfix(int age) {
        String old = "";
        int ageLastNumber = age % 10;
        boolean isExclusion = (age % 100 >= 11) && (age % 100 <= 14);

        if (ageLastNumber == 1)
            old = "год";
        else if(ageLastNumber == 0 || ageLastNumber >= 5 && ageLastNumber <= 9)
            old = "лет";
        else if(ageLastNumber >= 2 && ageLastNumber <= 4)
            old = "года";
        if (isExclusion)
            old = "лет";
        return old;
    }
}