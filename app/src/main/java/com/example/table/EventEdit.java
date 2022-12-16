package com.example.table;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.table.Event.Event;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;
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

public class EventEdit extends AppCompatActivity {
    Integer EventId;
    private TableApp myApp;

    TextView name_tv;
    TextView minUsersQty_tv;
    TextView maxUsersQty_tv;
    TextView dateTime_tv;
    Spinner spinner_town;
    TextView geo_tv;
    Spinner spinner_metro;
    TextView description_tv;
    TextView linkToChat_tv;
    TextView details_tv;
    TextView price_tv;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            getEventInfo();
        } catch (AppwriteException e) {
            e.printStackTrace();
        }

        button = (Button) findViewById(R.id.buttonCreateEvent);
        button.setText("Сохранить");

        name_tv = (TextView) findViewById(R.id.editNameEvent);
        minUsersQty_tv = (TextView) findViewById(R.id.editMinKolvo);
        maxUsersQty_tv = (TextView) findViewById(R.id.editMaxKolvo);
        dateTime_tv = (TextView) findViewById(R.id.editDate);
        spinner_town = (Spinner) findViewById(R.id.spinner_gorod);
        geo_tv = (TextView) findViewById(R.id.editPlace);
        spinner_metro = (Spinner) findViewById(R.id.spinner_metro);
        description_tv = (TextView) findViewById(R.id.editDescription);
        linkToChat_tv = (TextView) findViewById(R.id.editChat);
        details_tv = (TextView) findViewById(R.id.editDetali);
//        price_tv = (TextView) findViewById(R.id.);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);
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

    public void FillEventData(Map<String, Object> event) throws AppwriteException {

        String[] participants = (String []) event.get("participants");
        String[] hashtags = (String []) event.get("hashtags");

        name_tv.setText(event.get("name").toString());
        minUsersQty_tv.setText(event.get("minUsersQty").toString());
        maxUsersQty_tv.setText(event.get("maxUsersQty").toString());
        dateTime_tv.setText(event.get("dateTime").toString());
//        spinner_town.setSe(event.get("dateTime").toString());
        geo_tv.setText(event.get("geo").toString());
//        spinner_metro = (Spinner) findViewById(R.id.spinner_metro);
        description_tv.setText(event.get("description").toString());
        linkToChat_tv.setText(event.get("linkToChat").toString());
        details_tv.setText(event.get("details").toString());
        onClickSaveChanges(event);
    }

    public void onClickEventInfo(View view) {

    }

    public void onClickEditEvent(View view) {

    }

    public void onClickSaveChanges(Map<String, Object> event) throws AppwriteException {
        // обновление БД

        String id = "asdasdasd";

        Intent intent = new Intent(this, event.class);
        Databases databases = new Databases(myApp.appwriteClient);
        databases.updateDocument(
                myApp.databaseID,
                myApp.event_collectionID,
                event.get("id").toString(),
                new JSONObject(event),
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
                                button.setText("Создать");
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
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

}
