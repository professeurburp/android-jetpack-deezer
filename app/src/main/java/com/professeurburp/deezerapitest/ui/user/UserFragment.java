package com.professeurburp.deezerapitest.ui.user;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.professeurburp.deezerapitest.R;
import com.professeurburp.deezerapitest.binding.FragmentDataBindingComponent;
import com.professeurburp.deezerapitest.data.model.AlbumOverview;
import com.professeurburp.deezerapitest.data.model.User;
import com.professeurburp.deezerapitest.databinding.UserFragmentBinding;
import com.professeurburp.deezerapitest.di.Injectable;
import com.professeurburp.deezerapitest.utils.ExecutorPool;
import com.professeurburp.deezerapitest.vo.Resource;

import java.util.List;

import javax.inject.Inject;

/**
 * UI Controller responsible for the display of the selected user's albums.
 */
public class UserFragment extends Fragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    ExecutorPool executorPool;

    private final androidx.databinding.DataBindingComponent dataBinding = new FragmentDataBindingComponent(this);

    private UserFragmentBinding binding;
    private UserViewModel userViewModel;
    private AlbumOverviewAdapter albumAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate view with binding
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.user_fragment,
                container,
                false,
                dataBinding);

        // Get and configure album RecyclerView (grid layout with 4 columns)
        binding.userAlbumsRecyclerView.setHasFixedSize(true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Retrieve the ViewModel for this fragment
        userViewModel =
                ViewModelProviders
                        .of(this, viewModelFactory)
                        .get(UserViewModel.class);

        // Set binding lifecycle ownership
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // Apply insets to draw under status bar and navigation bar
        applyInsets();

        // Create bindings
        applyBindings();
    }

    private void applyBindings() {
        initRecyclerView();

        albumAdapter = new AlbumOverviewAdapter(dataBinding, executorPool, this::onAlbumSelected);
        binding.userAlbumsRecyclerView.setAdapter(albumAdapter);
        binding.setAlbumList(userViewModel.getUserAlbums());
        binding.setUser(userViewModel.getUser());
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
        userViewModel
                .getUserAlbums()
                .observe(getViewLifecycleOwner(),
                        listResource -> albumAdapter.submitList(listResource.data));
    }

    private void applyInsets() {
        View appBarView = binding.userInfoContainer;
        View recyclerView = binding.userAlbumsRecyclerView;
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) appBarView.getLayoutParams();

        // Add top margin toAppBar for a pretty UI (when scrolling, view will be displayed
        // under status bar)
        ViewCompat.setOnApplyWindowInsetsListener(
                appBarView,
                (view, insets) -> {
                    params.setMargins(
                            params.leftMargin,
                            params.topMargin + insets.getSystemWindowInsetTop(),
                            params.leftMargin,
                            params.bottomMargin);
                    appBarView.setLayoutParams(params);

                    return insets;
                });

        // Do the same for bottom padding, so that the last row of albums won't be masked by navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(
                recyclerView,
                (view, insets) -> {
                    view.setPadding(
                            view.getPaddingLeft(),
                            view.getPaddingTop(),
                            view.getPaddingRight(),
                            view.getPaddingBottom() + insets.getSystemWindowInsetBottom());

                    return insets;
                });
    }

    /**
     * Callback from the AlbumOverviewAdapter that's called when any album is tapped/clicked
     * in the album list.
     *
     * @param selectedAlbumId Selected album id to be provided to next screen, in order to display
     *                        its details.
     */
    private void onAlbumSelected(int selectedAlbumId) {
        NavHostFragment
                .findNavController(this)
                .navigate(UserFragmentDirections.showAlbumDetails(selectedAlbumId));
    }
}
