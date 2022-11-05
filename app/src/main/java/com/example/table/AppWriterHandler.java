package com.example.table;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;

import io.appwrite.Client;
import io.appwrite.Query;
import io.appwrite.extensions.JsonExtensionsKt;
import io.appwrite.models.DocumentList;
import io.appwrite.models.Session;
import io.appwrite.services.Account;
import io.appwrite.services.Databases;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class AppWriterHandler {

    //параметры, которые нужны для обращения к серверу
    private static final String endpointID;
    private static final String projectID;
    private static final String databaseID;
    private static final String user_collectionID;
    private static final String event_collectionID;
    private static final String notification_collectionID;

    static {
        endpointID = "http://tableapp.online:8111/v1";
        projectID = "6356860bb84da9132dfd";
        databaseID = "635abed829e099dfcd5c";
        user_collectionID = "635abf1ceefe2d36771b";
        event_collectionID = "635abf30a88c059105e3";
        notification_collectionID = "635abf7dd8fadf48772e";
    }

    public Session session;

    private static Client client;
    private final Databases databases;
    private final Account account;
    private String json;
    private String userID;

    public AppWriterHandler(Context context) {
        this.client = new Client(context)
                .setEndpoint(endpointID)
                .setProject(projectID);

        this.databases = new Databases(client);
        this.account = new Account(client);
    };

    public Session authorise(String email, String password){

        Session[] session = new Session[1];

        try{
            account.createEmailSession(
                    email,
                    password,
                    new Continuation<>() {
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
                                    session[0] = (Session) o;
                                    Log.d("AUTHORISE_SUCCESS", JsonExtensionsKt.toJson(session[0]));

                                    Gson g = new Gson();
                                    AuthorisationData ad = g.fromJson(JsonExtensionsKt.toJson(session[0]), AuthorisationData.class);
                                    userID = ad.getUserID();
                                    Log.i("TEST_AD", ad.getUserID());
                                }
                            } catch (Throwable th) {
                                Log.e("ERROR", th.toString());
                            }
                        }
                    });
        }
        catch (Throwable th){
            Log.e("ERROR_SESSION", th.toString());
        }

        this.session = session[0];
        return session[0];
    }

    public String getUserDocumentsByUserID(){
        final DocumentList[] dl = new DocumentList[1];
        final String[] response = new String[1];

        try{
            databases.listDocuments(
                    databaseID,
                    user_collectionID,
                    Collections.singletonList(Query.Companion.equal("userID", userID)),
                    new Continuation<>() {
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
                                    response[0] = JsonExtensionsKt.toJson(o);
                                    json = response[0];
                                    Log.d("LIST_DOCUMENTS", JsonExtensionsKt.toJson(o));
                                }
                            } catch (Throwable th) {
                                Log.e("ERROR", th.toString());
                            }
                        }
                    });
        }
        catch (Throwable th){
            Log.e("ERROR", th.toString());
        }

        return response[0];
    }

    public User getUserData (){
        User user = new User();
        //TODO разобраться с многопоточностью, чтобы не было ссылок на NULL
        try{
            Integer index_1 = json.indexOf("{\"name");
            Integer index_2 = json.indexOf("\"},");
            String test_data = json.substring(index_1, index_2+2);

            Log.i("TEST_DATA", test_data);
            //databases.getDocument(databaseID, user_collectionID,*/
        }
        catch (Throwable th){
            Log.e("ERROR", th.toString());
        }
        return user;
    }
}
