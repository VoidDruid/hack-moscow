package com.hackmoskow.mobile.presenters.base;

import com.hackmoskow.mobile.ui.base.BaseView;

public abstract class AbstractPresenter<T extends BaseView> implements BasePresenter {
    protected T view;

    public AbstractPresenter(T view) {
        this.view = view;
    }

    @Override
    public void create() {

    }

    @Override
    public void start() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }
}
