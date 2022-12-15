package com.example.table;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.table.Event.Event;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import io.appwrite.ID;
import io.appwrite.services.Account;
import io.appwrite.services.Databases;
import org.jetbrains.annotations.NotNull;

import io.appwrite.exceptions.AppwriteException;
import io.appwrite.extensions.JsonExtensionsKt;
import io.appwrite.models.Session;
import io.appwrite.services.Account;
import io.appwrite.services.Databases;
import io.appwrite.Client;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import io.appwrite.Client;
//import io.appwrite.coroutines.CoroutineCallback;
import io.appwrite.services.Databases;

public class CreateEvent extends AppCompatActivity {
    TableApp myApp;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myApp = (TableApp) getApplicationContext();
//        this.account = new Account(myApp.appwriteClient);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);
    }

    public void onClickCreateEvent(View view) throws AppwriteException {
        TextView name_tv = (TextView) findViewById(R.id.editNameEvent);
        TextView minUsersQty_tv = (TextView) findViewById(R.id.editMinKolvo);
        TextView maxUsersQty_tv = (TextView) findViewById(R.id.editMaxKolvo);
        TextView dateTime_tv = (TextView) findViewById(R.id.editDate);
        Spinner spinner_town = (Spinner) findViewById(R.id.spinner_gorod);
        TextView geo_tv = (TextView) findViewById(R.id.editPlace);
        Spinner spinner_metro = (Spinner) findViewById(R.id.spinner_metro);
        TextView description_tv = (TextView) findViewById(R.id.editDescription);
        TextView linkToChat_tv = (TextView) findViewById(R.id.editChat);
        TextView details_tv = (TextView) findViewById(R.id.editDetali);

        String name = name_tv.getText().toString();
        Integer minUsersQty = Integer.parseInt(minUsersQty_tv.getText().toString());
        Integer maxUsersQty = Integer.parseInt(maxUsersQty_tv.getText().toString());
//        String str_date = findViewById(R.id.editDate).toString();
//        Date format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(
//                str_date, new ParsePosition(0)
//        );
        String dateTime = dateTime_tv.getText().toString();
        String city = spinner_town.getSelectedItem().toString();
        String geo = geo_tv.getText().toString();
        String[] metro = new String[] {spinner_metro.getSelectedItem().toString()};
        String description = description_tv.getText().toString();
        String linkToChat = linkToChat_tv.getText().toString();
        String details = details_tv.getText().toString();
        String[] hashtags = new String[] {"-1"};
        String status = "created";
        String hostID = "-1";
        String[] participants = new String[maxUsersQty];
        Double price = Double.parseDouble("1000");

        Event event = new Event(
                name,
                minUsersQty,
                maxUsersQty,
                dateTime,
                city,
                geo,
                metro,
                description,
                linkToChat,
                details,
                hashtags,
                status,
                hostID,
                participants,
                price
        );

        Boolean isRightInput = checkInputValues(event);

        if (isRightInput) {
            addNewEvent(event);
//            Intent intent = new Intent(".event");
//            intent.putExtra("eventid", event.hash);
//            startActivity(intent);
        }
        else {
            Log.e("INPUTS EXCEPTION", "Invalid Values");
        }
    }

    private Boolean checkInputValues(
            Event event
    ) {
        Boolean isRight = true;

        return isRight;
    }

    private void addNewEvent(Event event) throws AppwriteException {
        myApp = (TableApp) getApplicationContext();
//        Client client = new Client(getApplicationContext())
//                .setEndpoint("http://tableapp.online:8111/v1") // Your API Endpoint
//                .setProject("6356860bb84da9132dfd"); // Your project ID
        Databases databases = new Databases(myApp.appwriteClient);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", event.name); // string
        map.put("minUsersQty", event.minUsersQty);
        map.put("maxUsersQty", event.maxUsersQty);
        map.put("dateTime", event.dateTime);
        map.put("city", event.city);
        map.put("geo", event.geo);
        map.put("metro", event.metro);
        map.put("description", event.description);
        map.put("linkToChat", event.linkToChat);
        map.put("details", event.details);
        map.put("hashtags", event.hashtags);
        map.put("status", event.status);
        map.put("participants", event.participants); // required: string[]
        map.put("price", event.price); // not required: double
        map.put("hostID", event.hostID); // required: string
//        ID id = new ID();
        String id = "asdasdasd";
        databases.createDocument(
                "635abed829e099dfcd5c",
                "635abf30a88c059105e3",
                id,
                map,
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
//                                io.appwrite.models.Account account = (io.appwrite.models.Account) o;
//                                Log.i("CREATING EVENT SUCCESS", account.toString());
                                moveOnEvent();
                            }
                        } catch (Throwable th) {
                            Log.e("CREATING EVENT ERROR", th.toString());
                            if (th.toString().contains("A user with the same email already exists in your project")) {
                                Log.e("CREATING EVENT ERROR", "A user with the same email already exists in your project");
                                // Выводим сообщение что аккаунт уже существует
//                                throw new IllegalArgumentException();
                            }
                        }
                    }
                }
        );
    }

    private void moveOnEvent() {
        String hash = "asdasdasd";
//        String hash = new ID().toString();
        Log.i("SUPER", "Super");
//        Intent intent = new Intent(".event");
//        intent.putExtra("eventid", hash);
//        startActivity(intent);
    }
}