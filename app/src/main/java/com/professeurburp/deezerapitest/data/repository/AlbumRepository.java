package com.professeurburp.deezerapitest.data.repository;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.professeurburp.deezerapitest.data.common.ApiResponse;
import com.professeurburp.deezerapitest.data.common.NetworkBoundResource;
import com.professeurburp.deezerapitest.data.model.AlbumOverview;
import com.professeurburp.deezerapitest.data.persistence.dao.AlbumOverviewDao;
import com.professeurburp.deezerapitest.data.rest.AlbumWebService;
import com.professeurburp.deezerapitest.data.rest.DeezerUserResponse;
import com.professeurburp.deezerapitest.utils.ExecutorPool;
import com.professeurburp.deezerapitest.vo.Resource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AlbumRepository {
    private final AlbumWebService albumWebService;
    private final AlbumOverviewDao albumOverviewDao;
    private final ExecutorPool executorPool;

    @Inject
    public AlbumRepository(AlbumWebService webservice, AlbumOverviewDao albumOverviewDao, ExecutorPool executorPool) {
        albumWebService = webservice;
        this.albumOverviewDao = albumOverviewDao;
        this.executorPool = executorPool;
    }

    public LiveData<Resource<List<AlbumOverview>>> getAlbums() {
        return new NetworkBoundResource<List<AlbumOverview>, DeezerUserResponse<AlbumOverview>>(executorPool) {
            @Override
            protected void saveCallResult(DeezerUserResponse<AlbumOverview> item) {
                // This is not optimal, we won't delete elements
                // that are not present in request result any more...
                albumOverviewDao.insertAlbumsOverview(item.getValues());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<AlbumOverview> data) {
                return (data == null || data.size() == 0);
            }

            @Override
            protected LiveData<List<AlbumOverview>> loadFromDb() {
                return albumOverviewDao.loadAll();
            }

            @Override
            protected LiveData<ApiResponse<DeezerUserResponse<AlbumOverview>>> createCall() {
                return albumWebService.listAlbums("198550305");
            }
        }.asLiveData();
    }
}
