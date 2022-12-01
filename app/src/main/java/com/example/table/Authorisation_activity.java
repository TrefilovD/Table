package com.example.table;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import io.appwrite.exceptions.AppwriteException;
import io.appwrite.models.Session;
import io.appwrite.services.Account;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;


public class Authorisation_activity extends AppCompatActivity {
    private TableApp myApp;
    //костыль
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorisation);
        myApp = (TableApp) getApplicationContext();
    }

    public void signInClicked(View view) throws AppwriteException {
        EditText email_textEdit = (EditText) findViewById(R.id.email);
        String email = email_textEdit.getText().toString();
        // Проверить строку email на валидность
        EditText password_textEdit = (EditText) findViewById(R.id.password);
        String password = password_textEdit.getText().toString();
        Account account = new Account(myApp.appwriteClient);
        account.createEmailSession(email, password, new Continuation<Session>() {
            @NonNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NonNull Object o) {
                try {
                    if (o instanceof Result.Failure) {
                        Result.Failure failure = (Result.Failure) o;
                        throw failure.exception;
                    } else {
                        myApp.session = (Session) o;
                        Log.d("AUTHORISE_SUCCESS", myApp.session.getUserId());
                    }
                } catch (Throwable th) {
                    Log.e("LOGINERROR", th.toString());
                    if (th.getMessage().contains("Invalid credentials.")) {
                        Log.i("LOGIN EXCEPTION", "Invalid credentials!");
                        // Тут показываем сообщение что логин или пароль неверные
                    } else if (th.getMessage().contains("Invalid email.")) {
                        Log.i("LOGIN EXCEPTION", "Invalid Email!");
                    }
                }
            }
        });
    }

    public void NoAccountButtonClicked(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}