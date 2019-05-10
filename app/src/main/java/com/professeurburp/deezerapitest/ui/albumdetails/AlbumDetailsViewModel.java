package com.professeurburp.deezerapitest.ui.albumdetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.professeurburp.deezerapitest.data.model.AlbumDetails;
import com.professeurburp.deezerapitest.data.repository.AlbumRepository;
import com.professeurburp.deezerapitest.vo.Resource;
import com.professeurburp.deezerapitest.vo.Status;

import javax.inject.Inject;

public class AlbumDetailsViewModel extends ViewModel {
    private final AlbumRepository albumRepository;

    private int currentAlbumId;
    private LiveData<Resource<AlbumDetails>> albumDetails;
    private LiveData<String> albumAdditionalInfo; // Not used, just for reference and info about Transformations capabilities.

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

        // Also, if some info is dependant on the full album details,
        // we can add a transformation so that we'll be able to bind directly on it without
        // making these transformations in the UI classes.
        albumAdditionalInfo = Transformations.map(albumDetails, this::parseAdditionalInfo);
    }

    private <Y, X> String parseAdditionalInfo(Resource<AlbumDetails> albumDetailsResource) {

        if (!(albumDetailsResource.getStatus() == Status.SUCCESS) ||
                albumDetailsResource.data == null) {
            return ""; // No need to transform anything if no data.
        }

        // Anyway we're not doing anything, just keeping this for further notice.
        return null;
    }

    public LiveData<Resource<AlbumDetails>> getAlbumDetails() {
        return albumDetails;
    }
}
