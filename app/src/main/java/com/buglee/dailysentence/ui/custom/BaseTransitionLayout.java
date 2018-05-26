package com.buglee.dailysentence.ui.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;


public abstract class BaseTransitionLayout extends FrameLayout {

    public BaseTransitionLayout(Context context) {
        this(context, null);
    }

    public BaseTransitionLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseTransitionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addViewWhenFinishInflate();
    }

    public abstract void addViewWhenFinishInflate();

    public abstract void firstInit(String info);

    @SuppressLint("MissingSuperCall")
    public abstract void onAnimationEnd();

}
