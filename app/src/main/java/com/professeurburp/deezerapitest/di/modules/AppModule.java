package com.professeurburp.deezerapitest.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.professeurburp.deezerapitest.config.Constants;
import com.professeurburp.deezerapitest.data.rest.DeezerWebService;
import com.professeurburp.deezerapitest.data.rest.LiveDataCallAdapterFactory;

import org.itishka.gsonflatten.FlattenTypeAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {ViewModelModule.class, PersistenceModule.class})
public class AppModule {

    @Provides
    Retrofit provideRetrofit() {

        // Use Flatten
        final Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new FlattenTypeAdapterFactory())
                .create();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .baseUrl(Constants.DEEZER_ENDPOINT)
                .build();
    }

    @Provides
    @Singleton
    DeezerWebService provideApiWebservice(Retrofit restAdapter) {
        return restAdapter.create(DeezerWebService.class);
    }
}
