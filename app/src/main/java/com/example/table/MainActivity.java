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
       awh.getUserDocumentsByUserID();
        /*Client client = new Client(getApplicationContext())
                .setEndpoint("http://tableapp.online:8111/v1")
                .setProject("6356860bb84da9132dfd");

        Databases databases = new Databases(client);

        try {
            databases.getDocument(
                    "635abed829e099dfcd5c",
                    "635abf1ceefe2d36771b",
                    "635acceac8a727b1f253",
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
                                    Log.d("RESPONSE", JsonExtensionsKt.toJson(o));
                                    json = JsonExtensionsKt.toJson(o);

                                    Integer index_1 = json.indexOf("{\"name");
                                    Integer index_2 = json.indexOf("\"},");
                                    String test_data = json.substring(index_1, index_2+2);

                                    Log.i("TEST", index_1.toString());
                                    Log.i("TEST", index_2.toString());
                                    Log.i("TEST", test_data);

                                    Gson g = new Gson();

                                    User user = g.fromJson(test_data, User.class);
                                    Log.i("USER", user.name);
                                }
                            } catch (Throwable th) {
                                Log.e("ERROR", th.toString());
                            }


                        }
                    }
            );
        }
        catch (Throwable th){
            Log.e("Error", th.toString());
        }
*/
    }

    public void testUpdate(View view){
        awh.getUserData();
        /*Client client = new Client(getApplicationContext())
                .setEndpoint("http://tableapp.online:8111/v1")
                .setProject("6356860bb84da9132dfd");

        Databases databases = new Databases(client);

        try{
            databases.updateDocument(
                    "635abed829e099dfcd5c",
                    "635abf1ceefe2d36771b",
                    "635acceac8a727b1f253",
                    "{\"surname\": \"Толстый\"}",
                    new Continuation<Object>() {
                        @NotNull
                        @Override
                        public CoroutineContext getContext() {
                            return EmptyCoroutineContext.INSTANCE;
                        }

                        @Override
                        public void resumeWith(@NotNull Object o) {
                            String json = "";
                            try {
                                if (o instanceof Result.Failure) {
                                    Result.Failure failure = (Result.Failure) o;
                                    throw failure.exception;
                                } else {
                                    Log.d("RESPONSE", JsonExtensionsKt.toJson(o));
                                    json = JsonExtensionsKt.toJson(o);
                                }
                            } catch (Throwable th) {
                                Log.e("ERROR", th.toString());
                            }
                        }
                    }
            );
        }
        catch (Throwable th){
            Log.e("ERROR", th.toString());
        }*/
    }
}

