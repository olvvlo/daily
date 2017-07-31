package com.buglee.dailysentence.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.buglee.dailysentence.R;
import com.buglee.dailysentence.utils.StatusUtils;
import com.jkyeo.splashview.SplashView;


/**
 * Created by LEE on 2017/7/31 0031.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getContentView());
        findViews();
        initData();
    }

    protected abstract int getContentView();

    protected abstract void initData();

    protected abstract void findViews();

    protected void initSplash(String url){
        String imgUrl = String.format("http://cdn.iciba.com/web/news/longweibo/imag/%s.jpg", url);
        // call after setContentView;
        SplashView.showSplashView(this, 3, R.drawable.splash, new SplashView.OnSplashViewActionListener() {
            @Override
            public void onSplashImageClick(String actionUrl) {

            }

            @Override
            public void onSplashViewDismiss(boolean initiativeDismiss) {

            }
        });

        // call this method anywhere to update splash view data
        SplashView.updateSplashData(this, imgUrl, "");
    }


    protected void initStatusBar(View positionView){
        // 1. 状态栏侵入
        boolean adjustStatusHeight = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            adjustStatusHeight = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        // 2. 状态栏占位View的高度调整
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
            adjustStatusBarHeight(positionView); // 调整状态栏高度
        }
    }

    /**
     * 调整沉浸状态栏
     */
    private void adjustStatusBarHeight(View positionView) {
        int statusBarHeight = StatusUtils.getStatusBarHeight(this);
        ViewGroup.LayoutParams lp = positionView.getLayoutParams();
        lp.height = statusBarHeight;
        positionView.setLayoutParams(lp);
    }

}
