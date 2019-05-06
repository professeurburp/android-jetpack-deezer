package com.professeurburp.deezerapitest.ui.albums;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.professeurburp.deezerapitest.data.model.AlbumOverview;
import com.professeurburp.deezerapitest.data.repository.AlbumRepository;
import com.professeurburp.deezerapitest.vo.Resource;

import java.util.List;

import javax.inject.Inject;

public class AlbumListViewModel extends ViewModel {

    private AlbumRepository albumRepository;
    private LiveData<Resource<List<AlbumOverview>>> albumList;

    @Inject
    AlbumListViewModel(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;

        // Start albums retrieval as soon as possible
        albumList = this.albumRepository.getAlbums();
    }

    public LiveData<Resource<List<AlbumOverview>>> getUserAlbums() { return albumList; }
}
