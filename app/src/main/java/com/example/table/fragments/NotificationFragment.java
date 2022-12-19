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
import com.example.table.TableApp;
import com.example.table.databinding.TestNavBottomBinding;

import org.jetbrains.annotations.NotNull;

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


public class NotificationFragment extends Fragment {

    TableApp myApp;

    public NotificationFragment() {
        // Required empty public constructor
    }


    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
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
        Log.i("TEST", "onCreateView");

        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        myApp = (TableApp) getActivity().getApplicationContext();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        try {

            Databases databases = new Databases(this.myApp.appwriteClient);
            databases.listDocuments(
                    myApp.databaseID,
                    myApp.notification_collectionID,
                    List.of(
                            Query.Companion.equal("receiverID", myApp.userID)
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

                                    //TODO доделать получение уведомлений
                                    Document doc = docs.getDocuments().get(0);
                                    Map<String, Object> response = doc.getData();
                                    myApp.personalData = response;

                                    Log.i("NOTIFICATIONS", (String) response.get("type"));
                                    //менять UI нужно в спец потоке (во фрагменте)
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                        }
                                    });

                                }
                            } catch (Throwable th) {
                                Log.e("NOTIFICATION_ERROR", th.toString());
                            }
                        }
                    }

            );

        }
        catch (Throwable th){
            Log.e("ERROR", th.toString());
        }

        //заглушка для теста
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

    //передаю вьюху, так как это фрагмент
    public void getNotificationsInfo(View view) throws AppwriteException {
        Databases databases = new Databases(this.myApp.appwriteClient);
        databases.listDocuments(
                myApp.databaseID,
                myApp.notification_collectionID,
                List.of(
                        Query.Companion.equal("receiverID", myApp.userID)
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

                                //TODO доделать получение уведомлений
                                Document doc = docs.getDocuments().get(0);
                                Map<String, Object> response = doc.getData();
                                myApp.personalData = response;

                                //менять UI нужно в спец потоке (во фрагменте)
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                });

                            }
                        } catch (Throwable th) {
                            Log.e("NOTIFICATION_ERROR", th.toString());
                        }
                    }
                }

        );

    }
}