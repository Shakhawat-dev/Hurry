package com.hubit.hurry;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

public class myApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        // Enable verbose OneSignal logging to debug issues if needed.
        //    OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId("e9ffdb6a-50bf-4799-8ae7-0bada38b0f78");

    }
}
