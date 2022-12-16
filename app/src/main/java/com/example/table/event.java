package com.example.table;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.table.Event.Event;
import com.example.table.TableApp;

import org.jetbrains.annotations.NotNull;

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

public class event extends AppCompatActivity{
    private String status_person; // not_member, member, owner
    private Event event;
    private ArrayList<Event> events = new TableApp().Events;
    Integer EventId;
    private TableApp myApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myApp = (TableApp) getApplicationContext();

        Intent intent = getIntent();

        EventId = Integer.parseInt(intent.getStringExtra("eventid"));

        try {
            getEventInfo();
        } catch (AppwriteException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);
    }

    public void onClickRegistrationOnEvent(View view) {

    }

    public void onClickEventInfo(View view, String status_person, Event event) {
        this.status_person = status_person;
        switch (status_person) {
            case ("not_member"):
                break;
            case ("member"):
                break;
            case ("owner"):
                break;
        }
    }

    public void onClickEditEvent(View view) {
        Intent intent = new Intent(this, EventEdit.class);
        intent.putExtra("EventId", EventId);
        startActivity(intent);
    }

    public void onClickTakePart(View view) {

    }

    public void getEventInfo() throws AppwriteException {
        Databases databases = new Databases(this.myApp.appwriteClient);
        databases.listDocuments(
                myApp.databaseID,
                myApp.event_collectionID,
                List.of(
                        Query.Companion.equal("userID", EventId.toString())
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
                                FillEventData(response);
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

    public void FillEventData(Map<String, Object> event) {
        TextView name_tv = (TextView) findViewById(R.id.name_event);
        TextView host_tv = (TextView) findViewById(R.id.name_host);
        TextView num_members_tv = (TextView) findViewById(R.id.number_of_people);
        TextView description_tv = (TextView) findViewById(R.id.description_event);
        TextView location_tv = (TextView) findViewById(R.id.place_event);
        TextView metro_tv = (TextView) findViewById(R.id.station_metro);
        TextView details_tv = (TextView) findViewById(R.id.details_event);
        TextView hashtags_tv = (TextView) findViewById(R.id.hash_event);
        TextView date_tv = (TextView) findViewById(R.id.dataTextView);
        TextView time_tv = (TextView) findViewById(R.id.timeTextView);
        TextView price_tv = (TextView) findViewById(R.id.priceTextView);

        String[] participants = (String []) event.get("participants");
        String[] hashtags = (String []) event.get("hashtags");

        name_tv.setText(event.get("name").toString());
        host_tv.setText(event.get("hostID").toString());
        num_members_tv.setText(String.format("%d/%d", participants.length, Integer.parseInt(event.get("maxUsersQty").toString())));
        description_tv.setText(event.get("description").toString());
        location_tv.setText(event.get("geo").toString());
        metro_tv.setText(event.get("metro").toString());
        details_tv.setText(event.get("details").toString());
//        hashtags_tv.setText(event.hashtags[0]);
        date_tv.setText(event.get("dateTime").toString());
        time_tv.setText(String.format("%s - %s","10:00", "12:00")); // FIXME
        price_tv.setText(event.get("price").toString());
    }
}
