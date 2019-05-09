package com.professeurburp.deezerapitest.di.modules;

import android.app.Application;

import androidx.room.Room;

import com.professeurburp.deezerapitest.data.persistence.DeezerDatabase;
import com.professeurburp.deezerapitest.data.persistence.dao.AlbumDetailsDao;
import com.professeurburp.deezerapitest.data.persistence.dao.AlbumOverviewDao;
import com.professeurburp.deezerapitest.data.persistence.dao.UserDao;
import com.professeurburp.deezerapitest.data.repository.AlbumRepository;
import com.professeurburp.deezerapitest.data.repository.UserRepository;
import com.professeurburp.deezerapitest.data.rest.DeezerWebService;
import com.professeurburp.deezerapitest.utils.ExecutorPool;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PersistenceModule {

    @Provides
    @Singleton
    DeezerDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                DeezerDatabase.class, "DeezerApiTest.db")
                .build();
    }

    @Provides
    @Singleton
    AlbumOverviewDao provideAlbumDao(DeezerDatabase database) { return database.getAlbumDao(); }

    @Provides
    @Singleton
    UserDao provideUserDao(DeezerDatabase database) { return database.getUserDao(); }

    @Provides
    @Singleton
    AlbumDetailsDao provideAlbumDetailsDao(DeezerDatabase database) { return database.getAlbumDetailsDao(); }

    @Provides
    @Singleton
    AlbumRepository provideAlbumRepository(
            DeezerWebService webservice,
            AlbumOverviewDao albumOverviewDao,
            AlbumDetailsDao albumDetailsDao,
            ExecutorPool executorPool) {

        return new AlbumRepository(webservice, albumOverviewDao, albumDetailsDao, executorPool);
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository(DeezerWebService webservice, UserDao userDao, ExecutorPool executorPool) {
        return new UserRepository(webservice, userDao, executorPool);
    }
}
