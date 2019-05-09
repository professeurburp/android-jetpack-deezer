package com.professeurburp.deezerapitest.ui.albumdetails;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.professeurburp.deezerapitest.R;
import com.professeurburp.deezerapitest.data.model.AlbumDetails;
import com.professeurburp.deezerapitest.databinding.AlbumDetailsFragmentBinding;
import com.professeurburp.deezerapitest.di.Injectable;
import com.professeurburp.deezerapitest.utils.ExecutorPool;
import com.professeurburp.deezerapitest.vo.Resource;

import javax.inject.Inject;

public class AlbumDetailsFragment extends Fragment implements Injectable {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    ExecutorPool executorPool;

    private AlbumDetailsFragmentBinding binding;
    private AlbumDetailsViewModel albumDetailsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.album_details_fragment,
                container,
                false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Get vm and set parameters retrieved from navigation args
        albumDetailsViewModel =
                ViewModelProviders
                        .of(this, viewModelFactory)
                        .get(AlbumDetailsViewModel.class);

        binding.setLifecycleOwner(getViewLifecycleOwner());

        final AlbumDetailsFragmentArgs params = AlbumDetailsFragmentArgs.fromBundle(getArguments());
        albumDetailsViewModel.setAlbumId(params.getAlbumId());
        binding.setAlbumDetails(albumDetailsViewModel.getAlbumDetails());
    }
}
