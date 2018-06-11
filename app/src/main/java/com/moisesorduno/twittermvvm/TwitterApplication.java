package com.moisesorduno.twittermvvm;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class TwitterApplication extends Application {

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
