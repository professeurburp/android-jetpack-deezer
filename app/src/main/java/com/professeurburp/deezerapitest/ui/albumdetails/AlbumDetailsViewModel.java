package com.professeurburp.deezerapitest.ui.albumdetails;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.professeurburp.deezerapitest.data.model.AlbumDetails;
import com.professeurburp.deezerapitest.data.repository.AlbumRepository;
import com.professeurburp.deezerapitest.vo.Resource;
import com.professeurburp.deezerapitest.vo.Status;

import java.util.Optional;

import javax.inject.Inject;

public class AlbumDetailsViewModel extends ViewModel {
    private final AlbumRepository albumRepository;

    private final MutableLiveData<Integer> _currentAlbumId = new MutableLiveData<>();

    private final LiveData<Resource<AlbumDetails>> albumDetails;

    @Inject
    public AlbumDetailsViewModel(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;

        albumDetails = Transformations.switchMap(_currentAlbumId, albumRepository::getAlbumDetails);
    }

    public void setAlbumId(int albumId) {
        _currentAlbumId.setValue(albumId);
    }

    public LiveData<Resource<AlbumDetails>> getAlbumDetails() {
        return albumDetails;
    }

    public void retry() {
        // Just set the same value to the MutableLiveData
        if (_currentAlbumId.getValue() != null) {
            _currentAlbumId.setValue(_currentAlbumId.getValue());
        }
    }
}
