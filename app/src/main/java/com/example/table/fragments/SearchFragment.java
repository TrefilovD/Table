package com.example.table.fragments;

import android.app.ApplicationErrorReport;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.table.CreateEvent;
import com.example.table.Event.Event;
import com.example.table.R;
import com.example.table.RegisterStep2_activity;
import com.example.table.event;

public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //кнопка с лупой для поиска
        ImageButton button = (ImageButton) view.findViewById(R.id.lupa_button);
        //назначаем метод, который будет ждать нажатие и в теле прописываем метод с логикой нажатия
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onClick_new(view);
            }
        });
        //кнопка для перехода к созданию мероприятия
        Button create_event_btn = (Button) view.findViewById(R.id.buttom_createevent);
        create_event_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateEventBtn(view);
            }
        });

        //кнопка перехода к записи на мероприятие
        Button register_on_event = (Button) view.findViewById(R.id.zapisatsya_Button_cardOfevent_1);
        register_on_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_on_event_1_btn();
            }
        });
    }

    public void onClick_new(View view){
        //

        CardView cardView = (CardView) view.findViewById(R.id.card1);
        cardView.setVisibility(View.INVISIBLE);
    }

    public void onCreateEventBtn(View view){
        Intent intent = new Intent(getActivity(), CreateEvent.class);
        startActivity(intent);
    }

    public void register_on_event_1_btn(){
        Intent intent = new Intent(getActivity(), event.class);
        intent.putExtra("eventid", "635af8d3091b24e06a93");
        startActivity(intent);
    }
}