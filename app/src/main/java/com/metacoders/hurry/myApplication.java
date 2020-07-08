package com.metacoders.hurry;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public  class myApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
