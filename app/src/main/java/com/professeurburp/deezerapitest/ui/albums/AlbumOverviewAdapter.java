package com.professeurburp.deezerapitest.ui.albums;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;

import com.professeurburp.deezerapitest.R;
import com.professeurburp.deezerapitest.data.model.AlbumOverview;
import com.professeurburp.deezerapitest.databinding.AlbumOverviewItemBinding;
import com.professeurburp.deezerapitest.ui.common.DataBoundListAdapter;
import com.professeurburp.deezerapitest.utils.ExecutorPool;

public class AlbumOverviewAdapter extends DataBoundListAdapter<AlbumOverview, AlbumOverviewItemBinding> {

    private final DataBindingComponent dataBindingComponent;
    private final Consumer<Integer> selectedAlbumCallback;

    AlbumOverviewAdapter(DataBindingComponent dataBindingComponent, ExecutorPool executorPool, Consumer<Integer> selectedCallback) {
        super(executorPool, new DiffUtil.ItemCallback<AlbumOverview>() {
            @Override
            public boolean areItemsTheSame(@NonNull AlbumOverview oldItem, @NonNull AlbumOverview newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull AlbumOverview oldItem, @NonNull AlbumOverview newItem) {
                return oldItem.equals(newItem);
            }
        });

        this.dataBindingComponent = dataBindingComponent;
        this.selectedAlbumCallback = selectedCallback;
    }

    @Override
    protected AlbumOverviewItemBinding createBinding(ViewGroup parent) {
        return DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.album_overview_item,
                parent,
                false,
                dataBindingComponent);
    }

    @Override
    protected void bind(AlbumOverviewItemBinding binding, AlbumOverview item) {
        binding.setAlbumOverview(item);

        binding.getRoot()
                .setOnClickListener(
                        v -> selectedAlbumCallback.accept(item.getId()));
    }
}
