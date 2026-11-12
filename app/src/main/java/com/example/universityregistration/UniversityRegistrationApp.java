package com.example.universityregistration;

import android.app.Application;
import com.google.firebase.database.FirebaseDatabase;

public class UniversityRegistrationApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance()
                .setPersistenceEnabled(true);
    }
}