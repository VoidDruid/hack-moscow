package com.hackmoskow.mobile.ui.application;

import android.app.Application;
import android.content.Context;

import com.hackmoskow.mobile.di.component.AppComponent;
import com.hackmoskow.mobile.di.component.DaggerAppComponent;
import com.hackmoskow.mobile.di.module.ApplicationModule;

public class BaseApplication extends Application {

    private static volatile BaseApplication application;
    private static AppComponent component;
    private static Context context;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        context = getBaseContext();

        initializeComponent();
    }

    public static Context getContext() {
        return context;
    }

    private void initializeComponent() {
        component = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(application))
                .build();
    }
}
