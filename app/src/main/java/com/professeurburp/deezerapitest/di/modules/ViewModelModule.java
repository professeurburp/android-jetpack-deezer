package com.professeurburp.deezerapitest.di.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.professeurburp.deezerapitest.di.qualifiers.ViewModelKey;
import com.professeurburp.deezerapitest.ui.albumdetails.AlbumDetailsViewModel;
import com.professeurburp.deezerapitest.ui.albums.AlbumListViewModel;
import com.professeurburp.deezerapitest.viewmodel.DeezerViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AlbumListViewModel.class)
    abstract ViewModel bindUserAlbumsViewModel(AlbumListViewModel albumListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AlbumDetailsViewModel.class)
    abstract ViewModel bindAlbumDetailsViewModel(AlbumDetailsViewModel albumDetailsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(DeezerViewModelFactory factory);
}