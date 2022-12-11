package com.example.table;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                        Query.Companion.equal("userID", myApp.userID)
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
                                Document doc = docs.getDocuments().get(0);
                                Map<String, Object> response = doc.getData();
                                FillPersonalData(response);
                                /*Gson g = new Gson();
                                Log.d("UserData", docs.getDocuments().get(0).getData().toString());
                                JsonElement jsonElement = g.toJsonTree(docs.getDocuments().get(0).getData());
                                Log.i("INFO", "ACCESS_USER_DATA");
                                myApp.personalData = g.fromJson(jsonElement, UserData.class);
                                Log.i("INFO", "CHANGES");
                                FillPersonalData(myApp.personalData);*/
                            }
                        } catch (Throwable th) {
                            Log.e("PERSONAL_ACTIVITY_ERROR", th.toString());
                        }
                    }
                }
        );
    }

    public void FillPersonalData(Map<String, Object> personalData) {
        Log.i("INFO", "ACCESS_USER_DATA");
        TextView name_personalinfo = (TextView) findViewById(R.id.name_personalinfo);
        name_personalinfo.setText(personalData.get("name").toString());
        Log.i("INFO", "CHANGES");
        TextView surname_personalinfo = (TextView) findViewById(R.id.surname_personalinfo);
        surname_personalinfo.setText(personalData.get("surname").toString());
        TextView age_personalinfo = (TextView) findViewById(R.id.age_personalinfo);
        if (personalData.get("age")!=null) {
            age_personalinfo.setText(personalData.get("age").toString().concat(" " + getAgePostfix((int) personalData.get("age"))));
        }
        TextView rating_personainfo = (TextView) findViewById(R.id.rating_personainfo);
        if (personalData.get("rate")!=null) {
            rating_personainfo.setText(personalData.get("rate").toString());
        }
        TextView number_organz_events_personalinfo = (TextView) findViewById(R.id.number_organz_events_personalinfo);
        number_organz_events_personalinfo.setText(Array.getLength(personalData.get("hostedEvents")));
        TextView number_uchast_events_personalinfo = (TextView) findViewById(R.id.number_uchast_events_personalinfo);
        number_uchast_events_personalinfo.setText(Array.getLength(personalData.get("currentEvents")));
        TextView aboutmyself_personalinfo = (TextView) findViewById(R.id.aboutmyself_personalinfo);
        aboutmyself_personalinfo.setText(personalData.get("about").toString());
        TextView lovelygames_personalinfo = (TextView) findViewById(R.id.lovelygames_personalinfo);
        lovelygames_personalinfo.setText(String.join(", ", personalData.get("games").toString()));
        TextView hashtags_personalinfo = (TextView) findViewById(R.id.hashtags_personalinfo);
        hashtags_personalinfo.setText(String.join(" ", personalData.get("hashtags").toString()));
        TextView email_personalinfo = (TextView) findViewById(R.id.email_personalinfo);
        email_personalinfo.setText(String.join(" ", personalData.get("email").toString()));
        TextView number_personalinfo = (TextView) findViewById(R.id.number_personalinfo);
        if (personalData.get("phone") != null) {
            number_personalinfo.setText(String.join(" ", personalData.get("phone").toString()));
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