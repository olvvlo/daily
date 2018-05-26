package com.buglee.dailysentence.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.buglee.dailysentence.utils.StatusUtils;

public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getContentView());
        findViews();
        initData();
        moveHandler();
    }

    protected abstract int getContentView();

    protected abstract void initData();

    protected abstract void findViews();

    private void moveHandler() {
        mGestureDetector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if(Math.abs(e1.getRawY() - e2.getRawY())>100){
                    return true;
                }
                if(Math.abs(velocityX)<150){
                    return true;
                }

                if((e1.getRawX() - e2.getRawX()) >200){
                    next();
                    return true;
                }

                if((e2.getRawX() - e1.getRawX()) >200){
                    pre();
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    public abstract void next();

    public abstract void pre();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    protected void initStatusBar(View positionView){
        boolean  adjustStatusHeight = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        String brand = Build.BRAND;
        if (brand.contains("Xiaomi")) {
            StatusUtils.setXiaomiDarkMode(this);
        } else if (brand.contains("Meizu")) {
            StatusUtils.setMeizuDarkMode(this);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            adjustStatusHeight = false;
        }
        if (adjustStatusHeight) {
            adjustStatusBarHeight(positionView);
        }
    }

    private void adjustStatusBarHeight(View positionView) {
        int statusBarHeight = StatusUtils.getStatusBarHeight(this);
        ViewGroup.LayoutParams lp = positionView.getLayoutParams();
        lp.height = statusBarHeight;
        positionView.setLayoutParams(lp);
    }

}
