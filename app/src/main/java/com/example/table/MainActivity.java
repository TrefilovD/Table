package com.example.table;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import org.jetbrains.annotations.NotNull;
import io.appwrite.Client;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.extensions.JsonExtensionsKt;
import io.appwrite.models.DocumentList;
import io.appwrite.models.Session;
import io.appwrite.services.Account;
import io.appwrite.services.Databases;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import android.view.View;

import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity {

    String json = new String();
    AppWriterHandler awh;
    DocumentList dl;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        awh = new AppWriterHandler(getApplicationContext());
        try {
            awh.authorise("alexandro-tolstenko@mail.ru", "12345678");
        }
        catch(Throwable th){
            Log.e("ERROR", th.toString());
        }
    }

    public void testMessage (View view){
       awh.getUserDataByUserID();
    }

    public void testUpdate(View view){
        awh.getUserDataByUserID();
    }
}

