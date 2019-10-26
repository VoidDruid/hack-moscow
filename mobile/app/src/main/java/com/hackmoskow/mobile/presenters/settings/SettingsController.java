package com.hackmoskow.mobile.presenters.settings;

import com.hackmoskow.mobile.domain.executor.Executor;
import com.hackmoskow.mobile.domain.executor.ThreadExecutor;
import com.hackmoskow.mobile.domain.models.UserData;
import com.hackmoskow.mobile.domain.repository.UserProfileRepository;
import com.hackmoskow.mobile.domain.repository.UserProfileRepositoryCallback;
import com.hackmoskow.mobile.domain.threading.MainThread;
import com.hackmoskow.mobile.domain.threading.MainThreadImpl;
import com.hackmoskow.mobile.ui.settings.SettingsActivity;

public class SettingsController implements UserProfileRepositoryCallback {
    private SettingsActivity view;
    private UserProfileRepository userProfileRepository;
    private Executor executor;
    private MainThread mainThread;

    public SettingsController(SettingsActivity view) {
        this.view = view;
        this.executor = ThreadExecutor.getInstance();
        this.mainThread = MainThreadImpl.getInstance();
        this.userProfileRepository = new UserProfileRepository("userData.txt", view);

        getSettings();
    }

    private void getSettings() {
        executor.execute(() -> {
            UserData userData = userProfileRepository.getUserData();
            if (userData != null) {
                mainThread.post(() -> view.showSettings(userData));
            }
        });
    }

    public void saveSettingsPressed(String sex, String age) {
        executor.execute(() -> {
            try {
                int intAge = Integer.parseInt(age);
                if (intAge < 0 || intAge > 150) {
                    mainThread.post(() -> view.showAgeError());
                } else {
                    userProfileRepository.saveUserData(new UserData(sex, intAge), this);
                }
            } catch (Exception e) {
                e.printStackTrace();
                mainThread.post(() -> view.showAgeError());
            }
        });
    }

    @Override
    public void onUserDataSaved() {
        mainThread.post(() -> view.close());
    }

    @Override
    public void onUserDataNotSaved() {
        mainThread.post(() -> view.showSavingError());
    }
}
