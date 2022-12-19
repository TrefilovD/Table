package com.example.table;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.table.Event.Event;
import com.example.table.fragments.SearchFragment;
import com.example.table.fragments.test_nav_menu;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

public class event_host extends AppCompatActivity{
    private Event event;
    private String EventId;
    private TableApp myApp;
    private Databases databases;

    private TextView name_tv;
    private TextView host_tv;
    private TextView num_members_tv;
    private TextView description_tv;
    private TextView location_tv;
    private TextView metro_tv;
    private TextView details_tv;
    private TextView hashtags_tv;
    private TextView date_tv;
    private TextView time_tv;
    private TextView price_tv;

    private ArrayList participants;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_host);

        myApp = (TableApp) getApplicationContext();
        databases = new Databases(this.myApp.appwriteClient);

        name_tv = (TextView) findViewById(R.id.name_event);
        host_tv = (TextView) findViewById(R.id.name_host);
        num_members_tv = (TextView) findViewById(R.id.number_of_people);
        description_tv = (TextView) findViewById(R.id.description_event);
        location_tv = (TextView) findViewById(R.id.place_event);
        metro_tv = (TextView) findViewById(R.id.station_metro);
        details_tv = (TextView) findViewById(R.id.details_event);
        hashtags_tv = (TextView) findViewById(R.id.hash_event);
//        date_tv = (TextView) findViewById(R.id.dataTextView);
//        time_tv = (TextView) findViewById(R.id.timeTextView);
//        price_tv = (TextView) findViewById(R.id.priceTextView);

        Intent intent = getIntent();
        EventId = intent.getStringExtra("eventid");
        try {
            getEventInfo();
        } catch (AppwriteException e) {
            e.printStackTrace();
        }
//        setContentView(R.layout.event);
    }

    public void onEventEdit(View view) throws AppwriteException {
        Intent intent = new Intent(this, EventEdit.class);
        intent.putExtra("eventid", EventId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void onCancelEvent(View view) throws AppwriteException {
        databases.deleteDocument(
                myApp.databaseID,
                myApp.event_collectionID,
                EventId,
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
                                Log.i("Event", "event deleted");
                                moveOnSearch();
                            }
                        } catch (Throwable th) {
                            Log.e("PERSONAL_ACTIVITY_ERROR", th.toString());
                        }
                    }
                }
        );
    }

    public void getEventInfo() throws AppwriteException {
        databases.listDocuments(
                myApp.databaseID,
                myApp.event_collectionID,
                List.of(
                        Query.Companion.equal("$id", EventId)
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
                            }
                        } catch (Throwable th) {
                            Log.e("PERSONAL_ACTIVITY_ERROR", th.toString());
                        }
                    }
                }
        );
    }

    public void FillEventData(Map<String, Object> event) throws AppwriteException {
        this.participants = (ArrayList) event.get("participants");

        ArrayList hashtags = (ArrayList) event.get("hashtags");

        name_tv.setText(event.get("name").toString());
        setHostName();
        num_members_tv.setText(String.format("%d/%d", participants.size(), Integer.parseInt(event.get("maxUsersQty").toString())));
        description_tv.setText(event.get("description").toString());
        location_tv.setText(event.get("geo").toString());
        metro_tv.setText(event.get("metro").toString());
        details_tv.setText(event.get("details").toString());
        hashtags_tv.setText("#" + String.join("#", hashtags));
//        String str_date = event.get("dateTime").toString();
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//        Calendar calendar = Calendar.getInstance();
//        try {
//            Date date = format.parse(str_date);
//            calendar.setTime(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Calendar calendarBegin = Calendar.getInstance();
//        Calendar calendarEnd = Calendar.getInstance();
//        format = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
//        try {
//            calendarBegin.setTime(format.parse(event.get("timeBegin").toString()));
//            calendarEnd.setTime(format.parse(event.get("timeEnd").toString()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        String d = String.format("%s.%s.%s",
//                calendar.get(Calendar.DAY_OF_MONTH),
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.YEAR)
//        );
//        date_tv.setText(d);
//        String timeBegin = String.format("%s:%s",
//                calendarBegin.get(Calendar.HOUR),
//                calendarBegin.get(Calendar.MINUTE)
//        );
//        String timeEnd = String.format("%s:%s",
//                calendarEnd.get(Calendar.HOUR),
//                calendarEnd.get(Calendar.MINUTE)
//        );
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                time_tv.setText(String.format("%s - %s",
//                        timeBegin,
//                        timeEnd
//                ));
//                price_tv.setText(event.get("price").toString());
//            }
//        });
    }

    public void setHostName() throws AppwriteException {
        Databases databases = new Databases(this.myApp.appwriteClient);
        try {
            databases.listDocuments(
                    myApp.databaseID,
                    myApp.user_collectionID,
                    List.of(
                            Query.Companion.equal("$id", "635acceac8a727b1f253")
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
                                    String name = response.get("name").toString();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            host_tv.setText(name);
                                        }
                                    });
                                }
                            } catch (Throwable th) {
                                Log.e("PERSONAL_ACTIVITY_ERROR", th.toString());
                            }
                        }
                    }
            );
        } catch (AppwriteException e) {
            e.printStackTrace();
        }
    }

    public void moveOnSearch() {
        Intent intent = new Intent(this, test_nav_menu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
