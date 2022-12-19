package com.example.table;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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
import java.util.Objects;

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
    private Map<String, Object> event;
    private String EventId;
    private TableApp myApp;
    private Databases databases;

    // Управление элементами на окне
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
        /*
        Обработка событий на окне EventEdit
        Вход: eventid: String - id выбранного ивента
         */
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
        /*
        Запрос к БД на получение информации об иввенте новой записи ивента
         */
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
        /*
        Заполнение элементов на окне
         */
        this.event = event;
        ArrayList hashtags = (ArrayList) event.get("hashtags");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
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
                String str_date = event.get("dateTime").toString();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Calendar calendar = Calendar.getInstance();
                try {
                    Date date = format.parse(str_date);
                    calendar.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar calendarBegin = Calendar.getInstance();
                Calendar calendarEnd = Calendar.getInstance();
                format = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
                try {
                    calendarBegin.setTime(format.parse(event.get("timeBegin").toString()));
                    calendarEnd.setTime(format.parse(event.get("timeEnd").toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String d = String.format("%s.%s.%s",
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.YEAR)
                );
                dateTime_tv.setText(d);
                String timeBegin = String.format("%s:%s",
                        calendarBegin.get(Calendar.HOUR),
                        calendarBegin.get(Calendar.MINUTE)
                );
                String timeEnd = String.format("%s:%s",
                        calendarEnd.get(Calendar.HOUR),
                        calendarEnd.get(Calendar.MINUTE)
                );
                editTimeBegin_tv.setText(timeBegin);
                editTimeEnd_tv.setText(timeEnd);
                price_tv.setText(event.get("price").toString());
                //        runOnUiThread(new Runnable() {
            }
        });
    }

    @Override
    public void onClickCreateEvent(View view) throws AppwriteException {
        /*
        Обработка события "Редактирование ивента"
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

//        String status = "created";
//        String hostID = myApp.userID;

        ArrayList<String> participants_l = (ArrayList<String>) Objects.requireNonNull(event.get("participants"));
        String[] participants = participants_l.toArray(new String[0]);

        Event event_upd = new Event(
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
                Objects.requireNonNull(event.get("status")).toString(),
                Objects.requireNonNull(event.get("hostID")).toString(),
                participants,
                price
        );

        updateEvent(event_upd);
    }

    private void updateEvent(Event event) throws AppwriteException {
        /*
        Запрос к БД на обновление записи ивента
         */
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

    @Override
    public void onBackPressed() {
        moveOnEventHost();
    }

    private void moveOnEventHost() {
        /*
        Переход на окно поиска ивентов
         */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button save = (Button) findViewById(R.id.buttonCreateEvent);
                save.setText("Создать");
            }
        });

        Log.i("SUPER", "Super");
        Intent intent = new Intent(this, event_host.class);
        intent.putExtra("eventid", EventId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}