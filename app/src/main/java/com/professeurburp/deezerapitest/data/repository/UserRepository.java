package com.professeurburp.deezerapitest.data.repository;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.professeurburp.deezerapitest.data.common.ApiResponse;
import com.professeurburp.deezerapitest.data.common.NetworkBoundResource;
import com.professeurburp.deezerapitest.data.model.User;
import com.professeurburp.deezerapitest.data.persistence.dao.UserDao;
import com.professeurburp.deezerapitest.data.rest.DeezerWebService;
import com.professeurburp.deezerapitest.utils.ExecutorPool;
import com.professeurburp.deezerapitest.vo.Resource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRepository {

    private final DeezerWebService deezerWebService;
    private final UserDao userDao;
    private final ExecutorPool executorPool;

    @Inject
    public UserRepository(DeezerWebService webservice, UserDao userDao, ExecutorPool executorPool) {
        deezerWebService = webservice;
        this.userDao = userDao;
        this.executorPool = executorPool;
    }

    public LiveData<Resource<User>> getUser(int userId) {
        return new NetworkBoundResource<User, User>(executorPool) {

            @Override
            protected void saveCallResult(User item) {
                userDao.insertUser(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                // TODO: Configure fetch constraints
                return true; // data == null;
            }

            @Override
            protected LiveData<User> loadFromDb() {
                return userDao.loadUser(userId);
            }

            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return deezerWebService.getUser(userId);
            }
        }.asLiveData();
    }
}
