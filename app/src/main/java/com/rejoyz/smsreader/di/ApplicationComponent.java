package com.rejoyz.smsreader.di;

import android.app.Application;

import com.rejoyz.smsreader.ApplicationController;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,   AppModule.class, ActivityBuilderModule.class})
public interface ApplicationComponent {

    void inject(ApplicationController application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }
}
