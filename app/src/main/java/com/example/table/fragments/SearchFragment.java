package com.example.table.fragments;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.table.CreateEvent;
import com.example.table.R;
import com.example.table.TableApp;
import com.example.table.event;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    TableApp myApp;
    List <String> Events_ID = Arrays.asList("1","2");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public void first_search() throws AppwriteException {
        //подключение к БД
        Databases databases = new Databases(this.myApp.appwriteClient);
        EditText search_editText = (EditText) getView().findViewById(R.id.search_editText);
        String search_string = search_editText.getText().toString();
        //запрос к БД
        databases.listDocuments(
                myApp.databaseID,
                myApp.event_collectionID,
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
                                Map<String, Object> response1 = doc.getData();
                                if (docs.getDocuments().size()>1) {
                                    doc = docs.getDocuments().get(1);
                                }
                                Map<String, Object> response2 = doc.getData();
                                //менять UI нужно в спец потоке (во фрагменте)
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            FillEventData(response1, response2, docs.getDocuments().size());
                                        } catch (AppwriteException e) {
                                            e.printStackTrace();
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

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
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myApp = (TableApp) getActivity().getApplicationContext();
        try {
            first_search();
        } catch (AppwriteException e) {
            e.printStackTrace();
        }
        ImageButton buttonsearch = (ImageButton) view.findViewById(R.id.lupa_button);
        buttonsearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(@NonNull View view) {
                try {
                    onClick_new(view);
                } catch (AppwriteException e) {
                    e.printStackTrace();
                }
            }
        });
        //кнопка для перехода к созданию мероприятия
        Button create_event_btn = (Button) getView().findViewById(R.id.buttom_createevent);
        create_event_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateEventBtn(view);
            }
        });
        Log.d("fff", String.valueOf(Events_ID.size()));

        //кнопка перехода к записи на мероприятие
        Button register_on_event1 = (Button) getView().findViewById(R.id.zapisatsya_Button_cardOfevent_1);
        if (Events_ID.size()>0) {
            register_on_event1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    register_on_event_btn(Events_ID.get(0));
                }
            });
            Log.d("fff", Events_ID.get(0));
            Button register_on_event2 = (Button) view.findViewById(R.id.zapisatsya_Button_cardOfevent_2);
            if (Events_ID.size()==2) {
                register_on_event2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        register_on_event_btn(Events_ID.get(1));
                    }
                });
            }
        }
    }

    public void onClick_new(View view) throws AppwriteException {
        //подключение к БД
        Databases databases = new Databases(this.myApp.appwriteClient);
        EditText search_editText = (EditText) getView().findViewById(R.id.search_editText);
        String search_string = search_editText.getText().toString();
        //запрос к БД
        databases.listDocuments(
                myApp.databaseID,
                myApp.event_collectionID,
                List.of(
                        //поиск по именам
                        Query.Companion.search("name", search_string)
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
                                Map<String, Object> response1 = doc.getData();
                                if (docs.getDocuments().size()>1) {
                                    doc = docs.getDocuments().get(1);
                                }
                                Map<String, Object> response2 = doc.getData();
                                //менять UI нужно в спец потоке (во фрагменте)
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            FillEventData(response1, response2, docs.getDocuments().size());
                                        } catch (AppwriteException e) {
                                            e.printStackTrace();
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

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
    public void FillEventData(Map<String, Object> eventData1, Map<String, Object> eventData2, int size) throws AppwriteException, ParseException {
        //Заполнение первой карты
        TextView name_card1 = (TextView) getView().findViewById(R.id.name_cardOfevent_1);
        name_card1.setText(eventData1.get("name").toString());
        TextView data_card1 = (TextView) getView().findViewById(R.id.data_cardOfevent_1);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str_date = eventData1.get("dateTime").toString();
        Date date = format.parse(str_date);
        String date_n = new SimpleDateFormat("dd.MM.yy").format(date);
        data_card1.setText(date_n);
        String alr = eventData1.get("participants").toString();
        int alreadyreg = alr.length()-alr.replace(",","").length()+1;
        TextView people_card1 = (TextView) getView().findViewById(R.id.people_cardOfevent_1);
        people_card1.setText(String.valueOf(alreadyreg)+"/"+eventData1.get("maxUsersQty").toString());
        format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String str_timeb = eventData1.get("timeBegin").toString();
        String str_timee = eventData1.get("timeEnd").toString();
        String time_event = "("+ new SimpleDateFormat("HH:mm").format(format.parse(str_timeb))+"-"+ new SimpleDateFormat("HH:mm").format(format.parse(str_timee))+")";
        TextView time_card1 = (TextView) getView().findViewById(R.id.time_cardOfevent_1);
        time_card1.setText(time_event);
        TextView metro_card1 = (TextView) getView().findViewById(R.id.metro_cardOfevent_1);
        String metro = eventData1.get("metro").toString().replace("[","").replace("]","");
        metro_card1.setText(metro);
        TextView price_card1 = (TextView) getView().findViewById(R.id.price_cardOfevent_1);
        price_card1.setText(eventData1.get("price").toString());
        Events_ID.set(0, String.valueOf(eventData1.get("$id")));
        if (size>1){
            TextView name_card2 = (TextView) getView().findViewById(R.id.name_cardOfevent_2);
            name_card2.setText(eventData2.get("name").toString());
            TextView data_card2 = (TextView) getView().findViewById(R.id.data_cardOfevent_2);
            format = new SimpleDateFormat("yyyy-MM-dd");
            str_date = eventData2.get("dateTime").toString();
            date = format.parse(str_date);
            date_n = new SimpleDateFormat("dd.MM.yy").format(date);
            data_card2.setText(date_n);
            alr = eventData2.get("participants").toString();
            alreadyreg = alr.length()-alr.replace(",","").length()+1;
            TextView people_card2 = (TextView) getView().findViewById(R.id.people_cardOfevent_2);
            people_card2.setText(String.valueOf(alreadyreg)+"/"+eventData2.get("maxUsersQty").toString());
            format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            str_timeb = eventData2.get("timeBegin").toString();
            str_timee = eventData2.get("timeEnd").toString();
            time_event = "("+ new SimpleDateFormat("HH:mm").format(format.parse(str_timeb))+"-"+ new SimpleDateFormat("HH:mm").format(format.parse(str_timee))+")";
            TextView time_card2 = (TextView) getView().findViewById(R.id.time_cardOfevent_2);
            time_card2.setText(time_event);
            TextView metro_card2 = (TextView) getView().findViewById(R.id.metro_cardOfevent_2);
            metro = eventData2.get("metro").toString().replace("[","").replace("]","");
            metro_card2.setText(metro);
            TextView price_card2 = (TextView) getView().findViewById(R.id.price_cardOfevent_2);
            price_card2.setText(eventData2.get("price").toString());
            Events_ID.set(1, String.valueOf(eventData2.get("$id")));
        }
        else {
            TextView name_card2 = (TextView) getView().findViewById(R.id.name_cardOfevent_2);
            name_card2.setText("");
            TextView data_card2 = (TextView) getView().findViewById(R.id.data_cardOfevent_2);
            data_card2.setText("");
            TextView people_card2 = (TextView) getView().findViewById(R.id.people_cardOfevent_2);
            people_card2.setText("");
            TextView time_card2 = (TextView) getView().findViewById(R.id.time_cardOfevent_2);
            time_card2.setText("");
            TextView metro_card2 = (TextView) getView().findViewById(R.id.metro_cardOfevent_2);
            metro_card2.setText("");
            TextView price_card2 = (TextView) getView().findViewById(R.id.price_cardOfevent_2);
            price_card2.setText("");
            Events_ID.remove(1);
        }


    }
    public void onCreateEventBtn(View view){
        Intent intent = new Intent(getActivity(), CreateEvent.class);
        startActivity(intent);
    }

    public void register_on_event_btn(String eventID){
        Intent intent = new Intent(getActivity(), event.class);
        intent.putExtra("eventid", eventID);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }


}