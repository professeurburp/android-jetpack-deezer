package com.professeurburp.deezerapitest.ui.common;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.professeurburp.deezerapitest.utils.ExecutorPool;

/**
 * A generic RecyclerView adapter that uses DataBinding & DiffUtil.
 *
 * @param <T> Type of the items in the list
 * @param <V> The type of the ViewDataBinding
</V></T> */
public abstract class DataBoundListAdapter<T, V extends ViewDataBinding>
        extends ListAdapter<T, DataBoundViewHolder<V>> {

    public DataBoundListAdapter(
            ExecutorPool execPool,
            @NonNull DiffUtil.ItemCallback<T> diff) {

        super(new AsyncDifferConfig.Builder<>(diff)
                .setBackgroundThreadExecutor(execPool.diskIO())
                .build());
    }

    @NonNull
    @Override
    public DataBoundViewHolder<V> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        V binding = createBinding(parent);
        return new DataBoundViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DataBoundViewHolder<V> holder, int position) {
        bind(holder.getBinding(), getItem(position));
        holder.getBinding().executePendingBindings();
    }

    protected abstract V createBinding(ViewGroup parent);

    protected abstract void bind(V binding, T item);
}
