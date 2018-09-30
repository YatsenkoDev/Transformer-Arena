package com.aequilibrium.assignment.transfarena;

import android.app.Application;

import com.aequilibrium.assignment.transfarena.injector.ApplicationComponent;
import com.aequilibrium.assignment.transfarena.injector.ApplicationModule;
import com.aequilibrium.assignment.transfarena.injector.DaggerApplicationComponent;

public class TransfArenaApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
