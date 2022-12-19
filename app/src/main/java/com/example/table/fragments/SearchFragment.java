package com.example.table.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.table.R;
import com.example.table.TableApp;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    TableApp myApp;

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
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myApp = (TableApp) getActivity().getApplicationContext();
        ImageButton button = (ImageButton) view.findViewById(R.id.lupa_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    onClick_new(view);
                } catch (AppwriteException e) {
                    e.printStackTrace();
                }
            }
        });
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
                        //поиск по индексу userID
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }


}