package com.professeurburp.deezerapitest.ui.albums;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.professeurburp.deezerapitest.BuildConfig;
import com.professeurburp.deezerapitest.data.model.AlbumOverview;
import com.professeurburp.deezerapitest.data.model.User;
import com.professeurburp.deezerapitest.data.repository.AlbumRepository;
import com.professeurburp.deezerapitest.data.repository.UserRepository;
import com.professeurburp.deezerapitest.vo.Resource;

import java.util.List;

import javax.inject.Inject;

public class AlbumListViewModel extends ViewModel {;

    private AlbumRepository albumRepository;
    private UserRepository userRepository;
    private LiveData<Resource<User>> user;
    private LiveData<Resource<List<AlbumOverview>>> albumList;

    @Inject
    AlbumListViewModel(UserRepository userRepository, AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;

        // Start user and albums retrieval as soon as possible
        user = this.userRepository.getUser(BuildConfig.USER_ID);
        albumList = this.albumRepository.getUserFavoriteAlbums(BuildConfig.USER_ID);
    }

    public LiveData<Resource<User>> getUser() { return  user; }

    public LiveData<Resource<List<AlbumOverview>>> getUserAlbums() { return albumList; }
}
