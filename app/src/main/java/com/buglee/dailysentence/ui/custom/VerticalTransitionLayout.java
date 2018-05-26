package com.buglee.dailysentence.ui.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.buglee.dailysentence.R;

public class VerticalTransitionLayout extends BaseTransitionLayout {

    private TextView textView1, textView2;

    protected int currentPosition = -1;
    protected int nextPosition = -1;

    private float textSize = 22;
    private int textColor = Color.BLACK;

    public VerticalTransitionLayout(Context context) {
        this(context, null);
    }

    public VerticalTransitionLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalTransitionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        @SuppressLint("CustomViewStyleable")
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.scene);
        textSize = a.getFloat(R.styleable.scene_textSize, textSize);
        textColor = a.getColor(R.styleable.scene_textColor, textColor);
        a.recycle();
    }

    @Override
    public void addViewWhenFinishInflate() {
        textView1 = new TextView(getContext());
        textView1.setGravity(Gravity.CENTER_VERTICAL);
        textView1.setTextSize(textSize);
        textView1.setTextColor(textColor);
        FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        addView(textView1, lp1);

        textView2 = new TextView(getContext());
        textView2.setGravity(Gravity.CENTER_VERTICAL);
        textView2.setTextSize(textSize);
        textView2.setTextColor(textColor);
        FrameLayout.LayoutParams lp2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        addView(textView2, lp2);
    }

    @Override
    public void firstInit(String text) {
        this.textView1.setText(text);
        currentPosition = 0;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onAnimationEnd() {
        currentPosition = nextPosition;
        TextView tmp = textView1;
        textView1 = textView2;
        textView2 = tmp;
    }

}
