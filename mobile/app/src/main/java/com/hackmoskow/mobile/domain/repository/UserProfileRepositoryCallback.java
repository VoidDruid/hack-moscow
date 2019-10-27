package com.hackmoskow.mobile.domain.repository;

public interface UserProfileRepositoryCallback {
    void onUserDataSaved();
    void onUserDataNotSaved();
}
