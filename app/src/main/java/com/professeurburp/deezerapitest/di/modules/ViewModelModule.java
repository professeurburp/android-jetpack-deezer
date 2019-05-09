package com.professeurburp.deezerapitest.di.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.professeurburp.deezerapitest.di.qualifiers.ViewModelKey;
import com.professeurburp.deezerapitest.ui.albumdetails.AlbumDetailsViewModel;
import com.professeurburp.deezerapitest.ui.user.UserViewModel;
import com.professeurburp.deezerapitest.viewmodel.DeezerViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUserAlbumsViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AlbumDetailsViewModel.class)
    abstract ViewModel bindAlbumDetailsViewModel(AlbumDetailsViewModel albumDetailsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(DeezerViewModelFactory factory);
}