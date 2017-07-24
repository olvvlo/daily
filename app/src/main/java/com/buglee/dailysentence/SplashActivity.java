package com.buglee.dailysentence;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.buglee.dailysentence.api.RetrofitClient;
import com.buglee.dailysentence.api.entity.Sentence;
import com.buglee.dailysentence.api.service.DailyService;
import com.buglee.dailysentence.ui.Utils;

import java.util.ArrayList;

import me.wangyuwei.particleview.ParticleView;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {
    private ParticleView mParticleView;
    private ArrayList<Sentence> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mParticleView = findViewById(R.id.splash);
        initDataList(0);
        mParticleView.startAnim();
        mParticleView.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putParcelableArrayListExtra("data", mDataList);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initDataList(int count) {
        DailyService dailyService = RetrofitClient.getInstance().create(DailyService.class);
        Observable<Sentence> observable = dailyService.getSentence(Utils.getPastDate(count));
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
                        mDataList.add(sentence);
                    }
                });
    }

}
