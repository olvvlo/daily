package com.buglee.dailysentence.ui;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.buglee.dailysentence.R;
import com.buglee.dailysentence.api.RetrofitClient;
import com.buglee.dailysentence.api.entity.SentenceBean;
import com.buglee.dailysentence.api.service.DailyService;
import com.buglee.dailysentence.ui.custom.FadeTransitionImageView;
import com.buglee.dailysentence.ui.custom.HorizontalTransitionLayout;
import com.buglee.dailysentence.ui.custom.VerticalTransitionLayout;
import com.buglee.dailysentence.utils.Utils;

import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.buglee.dailysentence.utils.Utils.getDateString;

public class MainActivity extends BaseActivity {
    private View mPositionView;
    private HorizontalTransitionLayout mTitleView, mLoveView;
    private VerticalTransitionLayout mSubTitleView, mTransView;
    private FadeTransitionImageView mBtmPicView;
    private TextView mDescView;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        //initSplash(getDateString(new Date(System.currentTimeMillis())));
        if (!Utils.isNetworkReachable(this)) {
            setContentView(R.layout.item_empty);
        }
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder((Activity) mContext, R.id.calendarView)
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                addData(date);

            }
        });

        initStatusBar(mPositionView);
    }

    @Override
    protected void findViews() {
        mPositionView = findViewById(R.id.positionView);
        mTitleView = findViewById(R.id.countryView);
        mLoveView = findViewById(R.id.temperatureView);
        mSubTitleView = findViewById(R.id.addressView);
        mDescView = findViewById(R.id.descriptionView);
        mTransView = findViewById(R.id.timeView);
        mBtmPicView = findViewById(R.id.bottomImageView);
    }

    public void addData(Date date) {

        DailyService dailyService = RetrofitClient.getInstance().create(DailyService.class);
        Observable<SentenceBean> observable = dailyService.getSentence(getDateString(date));
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SentenceBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SentenceBean sentenceBean) {
                        transition(sentenceBean);
                    }
                });
    }

    private void transition(SentenceBean sentenceBean) {
        mTitleView.firstInit("DAILY");
        mDescView.setText(String.format("%s\n%s", sentenceBean.getContent(), sentenceBean.getNote()));
        mLoveView.firstInit(sentenceBean.getLove());
        mSubTitleView.firstInit("每日一句");
        mBtmPicView.firstInit(sentenceBean.getPicture2());
        mTransView.firstInit(sentenceBean.getTranslation());

    }
}
