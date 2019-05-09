package com.professeurburp.deezerapitest.di.modules;

import com.professeurburp.deezerapitest.ui.albumdetails.AlbumDetailsFragment;
import com.professeurburp.deezerapitest.ui.user.UserFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract UserFragment contributeAlbumListFragment();

    @ContributesAndroidInjector
    abstract AlbumDetailsFragment contributeAlbumDetailsFragment();
}
