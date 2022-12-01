package com.example.table;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import io.appwrite.exceptions.AppwriteException;
import io.appwrite.extensions.JsonExtensionsKt;
import io.appwrite.models.Session;
import io.appwrite.services.Account;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class RegisterActivity extends AppCompatActivity {
    TableApp myApp;
    Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myApp = (TableApp) getApplicationContext();
        this.account = new Account(myApp.appwriteClient);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
    }

    public void RegisterButtonClicked(View view) throws AppwriteException {
        TextView nameField = (TextView) findViewById(R.id.name);
        TextView surnameField = (TextView) findViewById(R.id.surname);
        TextView emailField = (TextView) findViewById(R.id.email);
        TextView passwordField = (TextView) findViewById(R.id.password);
        TextView passwordConfirmField = (TextView) findViewById(R.id.confirmPassword);
        String name = nameField.getText().toString();
        String surname = surnameField.getText().toString();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        String confirm_password = passwordConfirmField.getText().toString();

        // Сюда вставить проверку на валидность email.
        // если не валид, то выводим сообщение и вызываем return
        // Также нужно ввести проверку на пустые поля

        if (!password.equals(confirm_password)) {
            // Тут показываем сообщение что пароли не совпадают
            Log.w("REGISTRATION WARNING", "Passwords are different:" + password + " " + confirm_password);
            return;
        }
        if (password.length()<8) {
            // Тут вызваем надпись что пароль слишком короткий
            Log.w("REGISTRATION WARNING", "Password must be at least 8 characters");
        }

        this.account.create(
                "unique()",
                email,
                password,
                name + " " + surname,
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
                                io.appwrite.models.Account account = (io.appwrite.models.Account) o;
                                Log.i("REGISTRATION SUCCESS", account.toString());
                                LoginAfterRegistration(email, password, name, surname, account);
                            }
                        } catch (Throwable th) {
                            Log.e("REGISTRATION ERROR", th.toString());
                            if (th.toString().contains("A user with the same email already exists in your project")) {
                                Log.e("REGISTRATION ERROR", "A user with the same email already exists in your project");
                                // Выводим сообщение что аккаунт уже существует
                            }
                        }
                    }
                }
        );

    }

    public void LoginAfterRegistration(String email, String password, String name, String surname, io.appwrite.models.Account account) throws AppwriteException {
        this.account.createEmailSession(email, password, new Continuation<Session>() {
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
                        Log.d("AUTHORISE_SUCCESS", JsonExtensionsKt.toJson(myApp.session));
                        GoToRegistrationStep2(email,password,name,surname, account.getId());
                    }
                } catch (Throwable th) {
                    Log.e("LOGIN ERROR", th.toString());
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

    public void GoToRegistrationStep2(String email, String password, String name, String surname, String accountId) {
        Intent intent = new Intent(this, RegisterStep2_activity.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("name", name);
        intent.putExtra("surname", surname);
        intent.putExtra("accountId", accountId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}