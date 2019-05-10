package com.professeurburp.deezerapitest.binding;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.Target;
import com.professeurburp.deezerapitest.R;

public class FragmentBindingAdapters {

    private final Fragment fragment;

    public FragmentBindingAdapters(Fragment fragment) {
        this.fragment = fragment;
    }

    @BindingAdapter("imageUrl")
    public void bindImage(ImageView imageView, String url) {
        Glide.with(fragment)
                .load(url)
                .placeholder(R.drawable.ic_cover_empty)
                .fallback(R.drawable.ic_cover_empty)
                .transition(DrawableTransitionOptions.withCrossFade(750))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(Target.SIZE_ORIGINAL)
                .into(imageView);
    }
}