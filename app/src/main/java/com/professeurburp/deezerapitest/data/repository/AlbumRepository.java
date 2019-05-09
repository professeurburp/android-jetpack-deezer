package com.professeurburp.deezerapitest.data.repository;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.professeurburp.deezerapitest.BuildConfig;
import com.professeurburp.deezerapitest.data.common.ApiResponse;
import com.professeurburp.deezerapitest.data.common.NetworkBoundResource;
import com.professeurburp.deezerapitest.data.model.AlbumDetails;
import com.professeurburp.deezerapitest.data.model.AlbumOverview;
import com.professeurburp.deezerapitest.data.persistence.dao.AlbumDetailsDao;
import com.professeurburp.deezerapitest.data.persistence.dao.AlbumOverviewDao;
import com.professeurburp.deezerapitest.data.rest.DeezerWebService;
import com.professeurburp.deezerapitest.data.rest.DeezerListResponse;
import com.professeurburp.deezerapitest.utils.ExecutorPool;
import com.professeurburp.deezerapitest.vo.Resource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AlbumRepository {
    private final DeezerWebService deezerWebService;
    private final AlbumOverviewDao albumOverviewDao;
    private final AlbumDetailsDao albumDetailsDao;
    private final ExecutorPool executorPool;

    @Inject
    public AlbumRepository(
            DeezerWebService webservice,
            AlbumOverviewDao albumOverviewDao,
            AlbumDetailsDao albumDetailsDao,
            ExecutorPool executorPool) {

        deezerWebService = webservice;
        this.albumOverviewDao = albumOverviewDao;
        this.albumDetailsDao = albumDetailsDao;
        this.executorPool = executorPool;
    }

    public LiveData<Resource<List<AlbumOverview>>> getUserFavoriteAlbums(int userId) {
        return new NetworkBoundResource<List<AlbumOverview>, DeezerListResponse<AlbumOverview>>(executorPool) {
            @Override
            protected void saveCallResult(DeezerListResponse<AlbumOverview> item) {
                // TODO: optimize!!!
                // This is not optimal, we won't delete elements
                // that are not present in request result any more...
                albumOverviewDao.insertAlbumsOverview(item.getValues());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<AlbumOverview> data) {
                return true; // (data == null || data.size() == 0);
            }

            @Override
            protected LiveData<List<AlbumOverview>> loadFromDb() {
                return albumOverviewDao.loadAll();
            }

            @Override
            protected LiveData<ApiResponse<DeezerListResponse<AlbumOverview>>> createCall() {
                return deezerWebService.listAlbums(userId, BuildConfig.FAVORITES_LIMIT);
            }
        }.asLiveData();
    }

    public LiveData<Resource<AlbumDetails>> getAlbumDetails(int albumId) {
        return new NetworkBoundResource<AlbumDetails, AlbumDetails>(executorPool) {

            @Override
            protected void saveCallResult(AlbumDetails item) {
                albumDetailsDao.insertAlbumDetails(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable AlbumDetails data) {
                // TODO: add discrimination so that we won't reload each time from network as well.
                return true;
            }

            @Override
            protected LiveData<AlbumDetails> loadFromDb() {
                return albumDetailsDao.getAlbumDetails(albumId);
            }

            @Override
            protected LiveData<ApiResponse<AlbumDetails>> createCall() {
                return deezerWebService.getAlbumDetails(albumId);
            }
        }.asLiveData();
    }
}
