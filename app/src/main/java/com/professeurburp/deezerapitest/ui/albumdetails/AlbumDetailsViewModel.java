package com.professeurburp.deezerapitest.ui.albumdetails;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.professeurburp.deezerapitest.data.model.AlbumDetails;
import com.professeurburp.deezerapitest.data.repository.AlbumRepository;
import com.professeurburp.deezerapitest.vo.Resource;

import javax.inject.Inject;

public class AlbumDetailsViewModel extends ViewModel {
    private final AlbumRepository albumRepository;

    private int currentAlbumId;
    private LiveData<Resource<AlbumDetails>> albumDetails;

    @Inject
    public AlbumDetailsViewModel(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public void setAlbumId(int albumId) {
        if (albumId == currentAlbumId) {
            return;
        }

        currentAlbumId = albumId;
        // We then trigger data retrieval, so that it can be displayed
        albumDetails = albumRepository.getAlbumDetails(currentAlbumId);
    }

    public LiveData<Resource<AlbumDetails>> getAlbumDetails() {
        return albumDetails;
    }
}
