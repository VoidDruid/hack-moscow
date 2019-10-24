package com.hackmoskow.mobile.di.module;

import android.content.Context;

import com.hackmoskow.mobile.domain.executor.Executor;
import com.hackmoskow.mobile.domain.executor.ThreadExecutor;
import com.hackmoskow.mobile.domain.threading.MainThread;
import com.hackmoskow.mobile.domain.threading.MainThreadImpl;
import com.hackmoskow.mobile.ui.application.BaseApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final BaseApplication application;

    public ApplicationModule(BaseApplication application) {
        this.application = application;
    }

    @Provides
    MainThread provideMainThread() {
        return MainThreadImpl.getInstance();
    }

    @Provides
    Executor provideThreadExecutor() {
        return ThreadExecutor.getInstance();
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application;
    }
}
