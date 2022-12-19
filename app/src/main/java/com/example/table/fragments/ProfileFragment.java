package com.example.table.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.table.R;
import com.example.table.TableApp;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.appwrite.Query;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.extensions.JsonExtensionsKt;
import io.appwrite.models.Document;
import io.appwrite.models.DocumentList;
import io.appwrite.services.Databases;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class ProfileFragment extends Fragment {

    TableApp myApp;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        myApp = (TableApp) getActivity().getApplicationContext();
        Log.i("TEST_PROFILE_SINGLETON", myApp.userID);
        try {
            GetPersonalInfo(view);
        } catch (AppwriteException e) {
            e.printStackTrace();
        }
        return view;
    }

    public void GetPersonalInfo(View view) throws AppwriteException {
        //подключение к БД
        Databases databases = new Databases(this.myApp.appwriteClient);

        //запрос к БД
        databases.listDocuments(
                myApp.databaseID,
                myApp.user_collectionID,
                List.of(
                        //поиск по индексу userID
                        Query.Companion.equal("userID", myApp.userID)
                ),
                //корутина для ожидания ответа с сервера
                new Continuation<Object>() {
                    @NotNull
                    @Override
                    public CoroutineContext getContext() {
                        return EmptyCoroutineContext.INSTANCE;
                    }

                    //метод, который срабатывает после получения ответа с сервера
                    @Override
                    public void resumeWith(@NotNull Object o) { //о - ответ сервера в формате JSON
                        try {
                            if (o instanceof Result.Failure) {
                                Result.Failure failure = (Result.Failure) o;
                                throw failure.exception;
                            } else {
                                Log.d("Appwrite", o.toString());

                                //ответ с сервера парсим в список документов (реализован в AppWrite)
                                //в логах будет строчка с ответом с сервера
                                DocumentList docs = (DocumentList) o;
                                //достаем первый документ из полученных
                                Document doc = docs.getDocuments().get(0);
                                //получаем данные из БД
                                Map<String, Object> response = doc.getData();
                                myApp.personalData = response;

                                //менять UI нужно в спец потоке (во фрагменте)
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        FillPersonalData(view, response);
                                    }
                                });

                            }
                        } catch (Throwable th) {
                            Log.e("PERSONAL_ACTIVITY_ERROR", th.toString());
                        }
                    }
                }
        );
    }

    public void FillPersonalData(View view, Map<String, Object> personalData) {
        Log.i("INFO", "ACCESS_USER_DATA");
        TextView name_personalinfo = (TextView) view.findViewById(R.id.name_personalinfo);
        name_personalinfo.setText(personalData.get("name").toString());

        TextView surname_personalinfo = (TextView) view.findViewById(R.id.surname_personalinfo);
        surname_personalinfo.setText(personalData.get("surname").toString());

        TextView age_personalinfo = (TextView) view.findViewById(R.id.age_personalinfo);
        if (personalData.get("age")!=null) {
            age_personalinfo.setText(personalData.get("age").toString().concat(" " + getAgePostfix((int) personalData.get("age"))));
        }

        TextView rating_personainfo = (TextView) view.findViewById(R.id.rating_personainfo);
        if (personalData.get("rate")!=null) {
            rating_personainfo.setText(personalData.get("rate").toString());
        }

        TextView number_organz_events_personalinfo = (TextView) view.findViewById(R.id.number_organz_events_personalinfo);
        ArrayList<Integer> hostedEvents = (ArrayList) personalData.get("hostedEvents");
        number_organz_events_personalinfo.setText(Integer.toString(hostedEvents.size()));

        TextView number_uchast_events_personalinfo = (TextView) view.findViewById(R.id.uchastnikTextView);
        ArrayList<Integer> currentEvents = (ArrayList) personalData.get("currentEvents");
        number_uchast_events_personalinfo.setText(Integer.toString(currentEvents.size()));

        TextView aboutmyself_personalinfo = (TextView) view.findViewById(R.id.aboutmyself_personalinfo);
        aboutmyself_personalinfo.setText(personalData.get("about").toString());

        TextView lovelygames_personalinfo = (TextView) view.findViewById(R.id.lovelygames_personalinfo);
        lovelygames_personalinfo.setText(String.join(", ", personalData.get("games").toString()));

        TextView hashtags_personalinfo = (TextView) view.findViewById(R.id.hashtags_personalinfo);
        hashtags_personalinfo.setText(String.join(" ", personalData.get("hashtags").toString()));

        TextView email_personalinfo = (TextView) view.findViewById(R.id.email_personalinfo);
        email_personalinfo.setText(String.join(" ", personalData.get("email").toString()));

        TextView number_personalinfo = (TextView) view.findViewById(R.id.number_personalinfo);
        if (personalData.get("phone") != null) {
            number_personalinfo.setText(String.join(" ", personalData.get("phone").toString()));
        }

        Log.i("INFO", "PROFILE_SUCCES");
    }

    public String getAgePostfix(int age) {
        String old = "";
        int ageLastNumber = age % 10;
        boolean isExclusion = (age % 100 >= 11) && (age % 100 <= 14);

        if (ageLastNumber == 1)
            old = "год";
        else if(ageLastNumber == 0 || ageLastNumber >= 5 && ageLastNumber <= 9)
            old = "лет";
        else if(ageLastNumber >= 2 && ageLastNumber <= 4)
            old = "года";
        if (isExclusion)
            old = "лет";
        return old;
    }
}