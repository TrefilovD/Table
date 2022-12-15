package com.example.table;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.table.Event.Event;
import com.example.table.TableApp;

import java.util.ArrayList;

public class event extends AppCompatActivity{
    private String status_person; // not_member, member, owner
    private Event event;
    private ArrayList<Event> events = new TableApp().Events;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);

        Intent intent = getIntent();

        Integer EventId = Integer.parseInt(intent.getStringExtra("eventid"));

        event = events.get(EventId);

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

        name_tv.setText(event.name);
        host_tv.setText(event.participants[0]);
        num_members_tv.setText(String.format("%d/%d", event.participants.length, event.maxUsersQty));
        description_tv.setText(event.description);
        location_tv.setText(event.geo);
        metro_tv.setText(event.metro[0]);
        details_tv.setText(event.details);
        hashtags_tv.setText(event.hashtags[0]);
        date_tv.setText(event.dateTime);
        time_tv.setText(String.format("%s - %s","10:00", "12:00")); // FIXME
        price_tv.setText(event.price.toString());
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

    public void visualisationEvent() {

    }

    public void onClickEditEvent(View view) {

    }

    public void onClickTakePart(View view) {

    }
}
