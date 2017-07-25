package com.buglee.dailysentence;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.buglee.dailysentence.api.RetrofitClient;
import com.buglee.dailysentence.api.entity.Sentence;
import com.buglee.dailysentence.api.service.DailyService;
import com.buglee.dailysentence.ui.Utils;
import com.buglee.dailysentence.ui.custom.FadeTransitionImageView;
import com.buglee.dailysentence.ui.custom.HorizontalTransitionLayout;
import com.buglee.dailysentence.ui.custom.VerticalTransitionLayout;
import com.jkyeo.splashview.SplashView;

import java.text.SimpleDateFormat;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private View positionView;
    private HorizontalTransitionLayout countryView, temperatureView;
    private VerticalTransitionLayout addressView, timeView;
    private FadeTransitionImageView bottomView;
    private TextView descriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSplash(getDateString(new Date(System.currentTimeMillis())));
        positionView = findViewById(R.id.positionView);
        countryView = findViewById(R.id.countryView);
        temperatureView = findViewById(R.id.temperatureView);
        addressView = findViewById(R.id.addressView);
        descriptionView = findViewById(R.id.descriptionView);
        timeView = findViewById(R.id.timeView);
        bottomView = findViewById(R.id.bottomImageView);
        if (!Utils.isNetworkReachable(this)) {
            setContentView(R.layout.item_empty);
        }
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                addData(date);

            }
        });
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
            Utils.setXiaomiDarkMode(this);
        } else if (brand.contains("Meizu")) {
            Utils.setMeizuDarkMode(this);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            adjustStatusHeight = false;
        }
        if (adjustStatusHeight) {
            adjustStatusBarHeight(); // 调整状态栏高度
        }
    }

    private String getDateString(Date date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public void addData(Date date) {

        DailyService dailyService = RetrofitClient.getInstance().create(DailyService.class);
        Observable<Sentence> observable = dailyService.getSentence(getDateString(date));
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Sentence>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Sentence sentence) {
                        transition(sentence);
                    }
                });
    }

    private void transition(Sentence sentence) {
        countryView.firstInit("DAILY");
        descriptionView.setText(String.format("%s\n%s", sentence.getContent(), sentence.getNote()));
        temperatureView.firstInit(sentence.getLove());
        addressView.firstInit(sentence.getDateline());
        bottomView.firstInit(sentence.getPicture2());
        timeView.firstInit(sentence.getTranslation());

    }

    /**
     * 调整沉浸状态栏
     */
    private void adjustStatusBarHeight() {
        int statusBarHeight = Utils.getStatusBarHeight(this);
        ViewGroup.LayoutParams lp = positionView.getLayoutParams();
        lp.height = statusBarHeight;
        positionView.setLayoutParams(lp);
    }

    private void setSplash(String url) {
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
}
