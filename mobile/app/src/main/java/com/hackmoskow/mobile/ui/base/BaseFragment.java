package com.hackmoskow.mobile.ui.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hackmoskow.mobile.presenters.base.BasePresenter;

import java.util.HashSet;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements BaseView {

    public Set<BasePresenter> presenters;

    private Unbinder unbinder;

    protected void setupBinding(@NonNull Object target, @NonNull View view) {
        unbinder = ButterKnife.bind(target, view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenters = new HashSet<>();

        for (BasePresenter presenter : getPresenters()) {
            presenter.create();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        for (BasePresenter presenter : getPresenters()) {
            presenter.start();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        for (BasePresenter presenter : getPresenters()) {
            presenter.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        for (BasePresenter presenter : getPresenters()) {
            presenter.pause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        for (BasePresenter presenter : getPresenters()) {
            presenter.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        for (BasePresenter presenter : getPresenters()) {
            presenter.destroy();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract Set<BasePresenter> getPresenters();

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMessage(int resourceId) {
        showMessage(getString(resourceId));
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
