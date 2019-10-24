package com.hackmoskow.mobile.di.component;

import com.hackmoskow.mobile.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class})
public interface AppComponent {

}
