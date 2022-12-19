package com.example.table;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.table.Event.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.appwrite.models.Document;
import io.appwrite.services.Databases;
import org.jetbrains.annotations.NotNull;

import io.appwrite.exceptions.AppwriteException;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
//import io.appwrite.coroutines.CoroutineCallback;


public class CreateEvent extends AppCompatActivity {
    private TableApp myApp;
    private Databases databases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        Обработка событий на окне CreateEvent
        Вход: eventid: String - id выбранного ивента
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);
        myApp = (TableApp) getApplicationContext();
        databases = new Databases(this.myApp.appwriteClient);

    }

    public void onClickCreateEvent(View view) throws AppwriteException {
        /*
        Обработка события "создание нового ивента"
        Результат: переход на окно поиска события
         */
        TextView name_tv = (TextView) findViewById(R.id.editNameEvent);
        String name = name_tv.getText().toString();
        if (name.length() == 0) {
            name_tv.setBackgroundColor(Color.RED);
            return;
        }
        name_tv.setBackgroundColor(Color.WHITE);

        TextView minUsersQty_tv = (TextView) findViewById(R.id.editMinKolvo);
        TextView maxUsersQty_tv = (TextView) findViewById(R.id.editMaxKolvo);
        Integer minUsersQty, maxUsersQty;
        try {
            minUsersQty = Integer.parseInt(minUsersQty_tv.getText().toString());
            maxUsersQty = Integer.parseInt(maxUsersQty_tv.getText().toString());
            if (minUsersQty > maxUsersQty) {
                minUsersQty_tv.setBackgroundColor(Color.RED);
                maxUsersQty_tv.setBackgroundColor(Color.RED);
                return;
            }
        }
        catch (NumberFormatException e) {
            minUsersQty_tv.setBackgroundColor(Color.RED);
            maxUsersQty_tv.setBackgroundColor(Color.RED);
            return;
        }
        minUsersQty_tv.setBackgroundColor(Color.WHITE);
        maxUsersQty_tv.setBackgroundColor(Color.WHITE);

        TextView price_tv = (TextView) findViewById(R.id.price_createEvent);
        CheckBox price_box = (CheckBox) findViewById(R.id.checkbox_free);
        Double price = 0.;
//        if (price_box.isChecked()) {
        try {
            price = Double.parseDouble(price_tv.getText().toString());
        }
        catch (NumberFormatException e) {
            if (price_tv.getText().toString().length() == 0) {
                price = 0.;
            }
            else {
                price_tv.setBackgroundColor(Color.RED);
                return;
            }
        }
//        }
        price_tv.setBackgroundColor(Color.WHITE);

        TextView dateTime_tv = (TextView) findViewById(R.id.editDate);
        Date dateTime, editTimeEnd, editTimeBegin;
        String str_date = dateTime_tv.getText().toString();
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        try {
            dateTime = format.parse(str_date);
        }
        catch (ParseException e) {
            dateTime_tv.setBackgroundColor(Color.RED);
            return;
        }
        dateTime_tv.setBackgroundColor(Color.WHITE);

        TextView editTimeEnd_tv = (TextView) findViewById(R.id.editTimeEnd);
        TextView editTimeBegin_tv = (TextView) findViewById(R.id.editTimeBegin);
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

        Spinner spinner_town = (Spinner) findViewById(R.id.spinner_gorod);
        String city = spinner_town.getSelectedItem().toString();

        TextView geo_tv = (TextView) findViewById(R.id.editPlace);
        String geo = geo_tv.getText().toString();

        Spinner spinner_metro = (Spinner) findViewById(R.id.spinner_metro);
        String[] metro = new String[] {spinner_metro.getSelectedItem().toString()};

        TextView description_tv = (TextView) findViewById(R.id.editDescription);
        String description = description_tv.getText().toString();

        TextView linkToChat_tv = (TextView) findViewById(R.id.editChat);
        String linkToChat = linkToChat_tv.getText().toString();

        TextView details_tv = (TextView) findViewById(R.id.editDetali);
        String details = details_tv.getText().toString();

        TextView hashtags_tv = (TextView) findViewById(R.id.hashtag_event);
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

        addNewEvent(event);
    }

    private void addNewEvent(Event event) throws AppwriteException {
        /*
        Запрос к БД на добавление новой записи ивента
         */
        databases.createDocument(
                myApp.databaseID,
                myApp.event_collectionID,
                "unique()",
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
                                Document document = (Document) o;
                                String eventid = document.getId();
                                Log.i("CREATED_EVENT", eventid);
                                moveOnEvent(eventid);
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


    private void moveOnEvent(String eventid) {
        /*
        Переход на окно созданного мероприятия
         */
        Intent intent = new Intent(this, event_host.class);
        intent.putExtra("eventid",eventid);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}