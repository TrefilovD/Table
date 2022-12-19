package com.example.table;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.table.Event.Event;

import java.util.Date;

public class CreatingEventActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);
    }
    //костыль
    public void creatingEvent() {
        // функция для кнопки "создать"
        Event event = setInputValues();
        event.setEvent2DB();
        // TODO move to SearchActivity
    }

    private Event setInputValues() {
        // чтение полей
        String name;
        String description;
        String location;
        Date date;
        String status;
//        ArrayList<Hashtag> hashtags,
//        User organiser,
//        ArrayList<User> members,
        int MaxNumberPeople;
        int MinNumberPeople;
//        Notification notification;

        Boolean isRightInput = checkInputValues();

        Event event = new Event();

        return event;
    }

    private Boolean checkInputValues() {
        Boolean isRight = true;

        return isRight;
    }
}