package com.professeurburp.deezerapitest.ui.albums;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.professeurburp.deezerapitest.R;
import com.professeurburp.deezerapitest.binding.FragmentDataBindingComponent;
import com.professeurburp.deezerapitest.databinding.AlbumListFragmentBinding;
import com.professeurburp.deezerapitest.di.Injectable;
import com.professeurburp.deezerapitest.utils.ExecutorPool;

import javax.inject.Inject;

/**
 * UI Controller responsible for the display of the selected user's albums.
 */
public class AlbumListFragment extends Fragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    ExecutorPool executorPool;

    private final DataBindingComponent dataBinding = new FragmentDataBindingComponent(this);

    private AlbumListFragmentBinding binding;
    private AlbumListViewModel albumListViewModel;

    private AlbumOverviewAdapter albumAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate view with binding
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.album_list_fragment,
                container,
                false);

        // Get and configure album RecyclerView (grid layout with 4 columns)
        binding.userAlbumsRecyclerView.setHasFixedSize(true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Retrieve the desired ViewModel
        albumListViewModel =
                ViewModelProviders
                        .of(this, viewModelFactory)
                        .get(AlbumListViewModel.class);

        // Set binding lifecycle ownership
        binding.setLifecycleOwner(getViewLifecycleOwner());

        initRecyclerView();

        albumAdapter = new AlbumOverviewAdapter(dataBinding, executorPool);
        binding.userAlbumsRecyclerView.setAdapter(albumAdapter);
        binding.setAlbumList(albumListViewModel.getUserAlbums());
    }

    private void initRecyclerView() {

        // Register scroll changes to trigger next pages load
        binding.userAlbumsRecyclerView.addOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                        if (lastVisiblePosition == albumAdapter.getItemCount() - (R.integer.album_list_columns * 2)) {
                            // TODO: trigger next page load when accessing the last rows.
                        }
                    }
                });

        // Subscribe to ViewModel results, so that we can update the list accordingly
        albumListViewModel
                .getUserAlbums()
                .observe(getViewLifecycleOwner(),
                        listResource -> albumAdapter.submitList(listResource.data));
    }
}
