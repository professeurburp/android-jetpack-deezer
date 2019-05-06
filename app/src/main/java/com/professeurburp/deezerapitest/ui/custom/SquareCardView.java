package com.professeurburp.deezerapitest.ui.custom;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

/**
 * SquareCardView is an override that allows to create square views in the albums list
 */
public class SquareCardView extends CardView {

    public SquareCardView(@NonNull Context context) {
        super(context);
    }

    public SquareCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //noinspection SuspiciousNameCombination -- That's precisely what we want...
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
