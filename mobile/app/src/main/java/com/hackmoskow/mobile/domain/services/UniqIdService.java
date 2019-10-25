package com.hackmoskow.mobile.domain.services;

import android.annotation.SuppressLint;
import android.provider.Settings;

import static com.hackmoskow.mobile.ui.application.BaseApplication.getContext;

public class UniqIdService {
    @SuppressLint("HardwareIds")
    public static final String android_id = Settings.Secure.getString(getContext().getContentResolver(),
            Settings.Secure.ANDROID_ID);
}
