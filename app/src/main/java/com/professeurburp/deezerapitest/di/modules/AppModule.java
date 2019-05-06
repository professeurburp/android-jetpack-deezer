package com.professeurburp.deezerapitest.di.modules;

import android.app.Application;

import androidx.room.Room;

import com.professeurburp.deezerapitest.configuration.NetworkConstants;
import com.professeurburp.deezerapitest.data.persistence.DeezerDatabase;
import com.professeurburp.deezerapitest.data.persistence.dao.AlbumOverviewDao;
import com.professeurburp.deezerapitest.data.repository.AlbumRepository;
import com.professeurburp.deezerapitest.data.rest.AlbumWebService;
import com.professeurburp.deezerapitest.data.rest.LiveDataCallAdapterFactory;
import com.professeurburp.deezerapitest.utils.ExecutorPool;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    DeezerDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                DeezerDatabase.class, "DeezerApiTest.db")
                .build();
    }

    @Provides
    @Singleton
    AlbumOverviewDao provideAlbumDao(DeezerDatabase database) { return database.albumDao(); }

    @Provides
    @Singleton
    AlbumRepository provideUserRepository(AlbumWebService webservice, AlbumOverviewDao userDao, ExecutorPool executorPool) {
        return new AlbumRepository(webservice, userDao, executorPool);
    }

    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .baseUrl(NetworkConstants.DEEZER_ENDPOINT)
                .build();
    }

    @Provides
    @Singleton
    AlbumWebService provideApiWebservice(Retrofit restAdapter) {
        return restAdapter.create(AlbumWebService.class);
    }
}
