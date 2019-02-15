package com.example.sakthivel.retrofitapp;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static String Instance_id = "Instance_id";

    @Override
    public void onTokenRefresh() {

        String token_id = FirebaseInstanceId.getInstance().getToken();         // Get the value of Firebase instance id or token
        Log.d(Instance_id, token_id);

    }
}





