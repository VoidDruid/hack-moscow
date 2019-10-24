package com.hackmoskow.mobile.domain.threading;

public interface MainThread {
    void post(final Runnable runnable);
}
