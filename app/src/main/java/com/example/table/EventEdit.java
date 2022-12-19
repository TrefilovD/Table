package com.example.table;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.table.Event.Event;

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
import io.appwrite.models.Document;
import io.appwrite.models.DocumentList;
import io.appwrite.services.Databases;
import org.jetbrains.annotations.NotNull;

import io.appwrite.exceptions.AppwriteException;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
//import io.appwrite.coroutines.CoroutineCallback;


public class EventEdit extends CreateEvent {
    private Event event;
    private String EventId;
    private TableApp myApp;
    private Databases databases;

    private TextView name_tv;
    private TextView minUsersQty_tv;
    private TextView maxUsersQty_tv;
    private TextView description_tv;
    private TextView geo_tv;
    private TextView details_tv;
    private TextView hashtags_tv;
    private TextView dateTime_tv;
    private TextView editTimeEnd_tv;
    private TextView editTimeBegin_tv;
    private TextView price_tv;
    private CheckBox price_box;
    private Spinner spinner_town, spinner_metro;
    private TextView linkToChat_tv;

    private ArrayList participants;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        Button save = (Button) findViewById(R.id.buttonCreateEvent);
        save.setText("Сохранить");

        myApp = (TableApp) getApplicationContext();
        databases = new Databases(this.myApp.appwriteClient);

        name_tv = (TextView) findViewById(R.id.editNameEvent);
        minUsersQty_tv = (TextView) findViewById(R.id.editMinKolvo);
        maxUsersQty_tv = (TextView) findViewById(R.id.editMaxKolvo);
        price_tv = (TextView) findViewById(R.id.price_createEvent);
        price_box = (CheckBox) findViewById(R.id.checkbox_free);
        dateTime_tv = (TextView) findViewById(R.id.editDate);
        editTimeEnd_tv = (TextView) findViewById(R.id.editTimeEnd);
        editTimeBegin_tv = (TextView) findViewById(R.id.editTimeBegin);
        spinner_town = (Spinner) findViewById(R.id.spinner_gorod);
        geo_tv = (TextView) findViewById(R.id.editPlace);
        spinner_metro = (Spinner) findViewById(R.id.spinner_metro);
        description_tv = (TextView) findViewById(R.id.editDescription);
        linkToChat_tv = (TextView) findViewById(R.id.editChat);
        details_tv = (TextView) findViewById(R.id.editDetali);
        hashtags_tv = (TextView) findViewById(R.id.hashtag_event);

        Intent intent = getIntent();
        EventId = intent.getStringExtra("eventid");
        try {
            getEventInfo();
        } catch (AppwriteException e) {
            e.printStackTrace();
        }
//        setContentView(R.layout.event);
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

    public void FillEventData(Map<String, Object> event) {
        ArrayList hashtags = (ArrayList) event.get("hashtags");

        name_tv.setText(event.get("name").toString());
        minUsersQty_tv.setText(event.get("minUsersQty").toString());
        maxUsersQty_tv.setText(event.get("maxUsersQty").toString());
        description_tv.setText(event.get("description").toString());
        price_tv.setText(event.get("price").toString());
//        spinner_town.setSelection(spinner_town.);
        geo_tv.setText(event.get("geo").toString());
        description_tv.setText(event.get("description").toString());
        linkToChat_tv.setText(event.get("linkToChat").toString());
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
//        date_tv.setText(String.format("%s.%s.%s",
//                calendar.get(Calendar.DAY_OF_MONTH),
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.YEAR)
//                ));
//        date_tv.setText(event.get("dateTime").toString());
//        time_tv.setText(String.format("%s - %s",
//                event.get("timeBegin").toString(),
//                event.get("timeEnd").toString()
//        ));
//        price_tv.setText(event.get("price").toString());
    }

    @Override
    public void onClickCreateEvent(View view) throws AppwriteException {
        TextView name_tv = (TextView) findViewById(R.id.editNameEvent);
        TextView minUsersQty_tv = (TextView) findViewById(R.id.editMinKolvo);
        TextView maxUsersQty_tv = (TextView) findViewById(R.id.editMaxKolvo);
        TextView price_tv = (TextView) findViewById(R.id.priceTextView);
        CheckBox price_box = (CheckBox) findViewById(R.id.checkbox_free);
        TextView dateTime_tv = (TextView) findViewById(R.id.editDate);
        TextView editTimeEnd_tv = (TextView) findViewById(R.id.editTimeEnd);
        TextView editTimeBegin_tv = (TextView) findViewById(R.id.editTimeBegin);
        Spinner spinner_town = (Spinner) findViewById(R.id.spinner_gorod);
        TextView geo_tv = (TextView) findViewById(R.id.editPlace);
        Spinner spinner_metro = (Spinner) findViewById(R.id.spinner_metro);
        TextView description_tv = (TextView) findViewById(R.id.editDescription);
        TextView linkToChat_tv = (TextView) findViewById(R.id.editChat);
        TextView details_tv = (TextView) findViewById(R.id.editDetali);
        TextView hashtags_tv = (TextView) findViewById(R.id.hashtag_event);


        String name = name_tv.getText().toString();
        Integer minUsersQty, maxUsersQty;
        try {
            minUsersQty = Integer.parseInt(minUsersQty_tv.getText().toString());
            maxUsersQty = Integer.parseInt(maxUsersQty_tv.getText().toString());
        }
        catch (NumberFormatException e) {
            Log.e("Value error", "Mistakes in min/max users");
            return;
        }
        Date dateTime, editTimeEnd, editTimeBegin;
        String str_date = dateTime_tv.getText().toString();
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        try {
            dateTime = format.parse(str_date);
        }
        catch (ParseException e) {
            Log.e("Value error", "Mistakes in date");
            return;
        }
        String editTimeEnd_str = editTimeEnd_tv.getText().toString();
        String editTimeBegin_str = editTimeBegin_tv.getText().toString();
        format = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
        try {
            editTimeEnd = format.parse(editTimeEnd_str);
            editTimeBegin = format.parse(editTimeBegin_str);
        }
        catch (ParseException e) {
            Log.e("Value error", "Mistakes in date");
            return;
        }
        Double price = 0.;
        if (!price_box.isChecked()) {
            price = Double.parseDouble(price_tv.getText().toString());
        }

        if (minUsersQty >= maxUsersQty) {
            minUsersQty = maxUsersQty;
            Log.w("MIN more MAX", "MIN = MAX");
        }

        String city = spinner_town.getSelectedItem().toString();
        String geo = geo_tv.getText().toString();
        String[] metro = new String[] {spinner_metro.getSelectedItem().toString()};
        String description = description_tv.getText().toString();
        String linkToChat = linkToChat_tv.getText().toString();
        String details = details_tv.getText().toString();
        String[] hashtags = hashtags_tv.getText().toString().
                replaceFirst("#", "").split("#");
        String status = "created";
        String hostID = myApp.userID;
        String[] participants = new String[] {myApp.userID};

        Event event = new Event(
                name,
                minUsersQty,
                maxUsersQty,
                dateTime.toString(),
                editTimeEnd.toString(),
                editTimeBegin.toString(),
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

        updateEvent(event);
    }

    private void updateEvent(Event event) throws AppwriteException {

        databases.updateDocument(
                myApp.databaseID,
                myApp.event_collectionID,
                EventId,
                event,
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
                                moveOnEventHost();
                            }
                        } catch (Throwable th) {
                            Log.e("CREATING EVENT ERROR", th.toString());
                            if (th.toString().contains("A user with the same email already exists in your project")) {
                                Log.e("CREATING EVENT ERROR", "A user with the same email already exists in your project");
                            }
                        }
                    }
                }
        );
    }

    private void moveOnEventHost() {
//        String hash = new ID().toString();
//        Button save = (Button) findViewById(R.id.buttonCreateEvent);
//        save.setText("Создать");
        Log.i("SUPER", "Super");
        Intent intent = new Intent(this, event_host.class);
        intent.putExtra("eventid", EventId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}