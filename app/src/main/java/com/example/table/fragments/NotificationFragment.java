package com.example.table.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.table.R;
import com.example.table.databinding.TestNavBottomBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/*
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
*/

    // TODO: Rename and change types of parameters
/*
    private String mParam1;
    private String mParam2;
*/

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
/*        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
/*            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
        }

        Log.i("TEST", "OnCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("TEST", "onCreateView");

        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MyNotifications [] myNotifications = new MyNotifications[]{
                new MyNotifications(
                        R.drawable.image_notification,
                        "Мафия в антикафе 12 ярдов",
                        "16.11.2022",
                        "17:00 - 22:00",
                        R.drawable.avatar_notification,
                        "Alexander Tolstenko",
                        "Запрос на участие"),
                new MyNotifications(
                        R.drawable.image_notification,
                        "Мафия в антикафе 12 ярдов",
                        "16.11.2022",
                        "17:00 - 22:00",
                        R.drawable.avatar_notification,
                        "Alexander Tolstenko",
                        "Запрос на участие"),
                new MyNotifications(
                        R.drawable.image_notification,
                        "Мафия в антикафе 12 ярдов",
                        "16.11.2022",
                        "17:00 - 22:00",
                        R.drawable.avatar_notification,
                        "Alexander Tolstenko",
                        "Запрос на участие")
        };

        MyNotificationAdapter myNotificationAdapter = new MyNotificationAdapter(myNotifications, this);

        recyclerView.setAdapter(myNotificationAdapter);

        TextView notificationCounter = (TextView) view.findViewById(R.id.Notification_counter);
        Integer counter = myNotifications.length;
        String notificationCounterText = notificationCounter.getText().toString();
        notificationCounter.setText(notificationCounterText + " " + counter.toString());

        return view;
    }
}