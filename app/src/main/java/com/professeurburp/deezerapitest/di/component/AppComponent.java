package com.professeurburp.deezerapitest.di.component;

import android.app.Application;

import com.professeurburp.deezerapitest.DeezerApiTestApplication;
import com.professeurburp.deezerapitest.di.modules.ActivityModule;
import com.professeurburp.deezerapitest.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules={AndroidSupportInjectionModule.class, ActivityModule.class, AppModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(DeezerApiTestApplication app);
}
