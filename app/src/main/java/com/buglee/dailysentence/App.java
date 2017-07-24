package com.buglee.dailysentence;

import android.app.Application;
import android.content.Context;

/**
 * Created by LEE on 2017/7/24 0024.
 */

public class App extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
