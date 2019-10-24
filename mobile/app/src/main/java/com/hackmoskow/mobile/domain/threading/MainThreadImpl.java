package com.hackmoskow.mobile.domain.threading;

import android.os.Handler;
import android.os.Looper;

public class MainThreadImpl implements MainThread {

    private static MainThread mainThread;

    private Handler handler;

    private MainThreadImpl() {
        handler = new Handler(Looper.getMainLooper());
    }

    public static MainThread getInstance() {
        if (mainThread == null) {
            mainThread = new MainThreadImpl();
        }

        return mainThread;
    }

    @Override
    public void post(Runnable runnable) {
        handler.post(runnable);
    }
}
