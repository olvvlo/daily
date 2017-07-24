package com.buglee.dailysentence;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.buglee.dailysentence.api.entity.Sentence;
import com.buglee.dailysentence.ui.Utils;
import com.buglee.dailysentence.ui.custom.FadeTransitionImageView;
import com.buglee.dailysentence.ui.custom.HorizontalTransitionLayout;
import com.buglee.dailysentence.ui.custom.VerticalTransitionLayout;
import com.bumptech.glide.Glide;
import com.stone.pile.libs.PileLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private View positionView;
    private PileLayout pileLayout;
    private ArrayList<Sentence> mDataList = new ArrayList<>();

    private int lastDisplay = -1;

    private ObjectAnimator transitionAnimator;
    private HorizontalTransitionLayout countryView, temperatureView;
    private VerticalTransitionLayout addressView, timeView;
    private FadeTransitionImageView bottomView;
    private Animator.AnimatorListener animatorListener;
    private TextView descriptionView;
    private float transitionValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataList = getIntent().getParcelableArrayListExtra("data");
        positionView = findViewById(R.id.positionView);
        countryView = findViewById(R.id.countryView);
        temperatureView = findViewById(R.id.temperatureView);
        pileLayout = findViewById(R.id.pileLayout);
        addressView = findViewById(R.id.addressView);
        descriptionView = findViewById(R.id.descriptionView);
        timeView = findViewById(R.id.timeView);
        bottomView = findViewById(R.id.bottomImageView);

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

        animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                countryView.onAnimationEnd();
                temperatureView.onAnimationEnd();
                addressView.onAnimationEnd();
                bottomView.onAnimationEnd();
                timeView.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
        // 3. PileLayout绑定Adapter
        if (!mDataList.isEmpty())
            bindAdapter();
        else {
            setContentView(R.layout.item_empty);
            Toast.makeText(this, "无网络连接,请检查", Toast.LENGTH_LONG).show();
        }


    }

    private void bindAdapter() {
        pileLayout.setAdapter(new PileLayout.Adapter() {
            @Override
            public int getLayoutId() {
                return R.layout.item_layout;
            }

            @Override
            public void bindView(View view, int position) {
                ViewHolder viewHolder = (ViewHolder) view.getTag();
                if (viewHolder == null) {
                    viewHolder = new ViewHolder();
                    viewHolder.imageView = view.findViewById(R.id.imageView);
                    view.setTag(viewHolder);
                }

                Glide.with(MainActivity.this)
                        .load(mDataList.get(position).getPicture())
                        .into(viewHolder.imageView);
            }

            @Override
            public int getItemCount() {
                return mDataList.size();
            }

            @Override
            public void displaying(int position) {
                descriptionView.setText(mDataList.get(position).getContent() + "\n" +
                        mDataList.get(position).getNote());
                if (lastDisplay < 0) {
                    initSecene(position);
                    lastDisplay = 0;
                } else if (lastDisplay != position) {
                    transitionSecene(position);
                    lastDisplay = position;
                }
            }

            @Override
            public void onItemClick(View view, int position) {
                super.onItemClick(view, position);
            }
        });
    }

    private void initSecene(int position) {
        countryView.firstInit("DAILY");
        temperatureView.firstInit(mDataList.get(position).getLove());
        addressView.firstInit(mDataList.get(position).getDateline());
        bottomView.firstInit(mDataList.get(position).getPicture2());
        timeView.firstInit(mDataList.get(position).getTranslation());
    }

    private void transitionSecene(int position) {
        if (transitionAnimator != null) {
            transitionAnimator.cancel();
        }

        countryView.saveNextPosition(position, "DAILY");
        temperatureView.saveNextPosition(position, mDataList.get(position).getLove());
        addressView.saveNextPosition(position, mDataList.get(position).getDateline());
        bottomView.saveNextPosition(position, mDataList.get(position).getPicture2());
        timeView.saveNextPosition(position, mDataList.get(position).getTranslation());

        transitionAnimator = ObjectAnimator.ofFloat(this, "transitionValue", 0.0f, 1.0f);
        transitionAnimator.setDuration(300);
        transitionAnimator.start();
        transitionAnimator.addListener(animatorListener);

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

    /**
     * 属性动画
     */
    public void setTransitionValue(float transitionValue) {
        this.transitionValue = transitionValue;
        countryView.duringAnimation(transitionValue);
        temperatureView.duringAnimation(transitionValue);
        addressView.duringAnimation(transitionValue);
        bottomView.duringAnimation(transitionValue);
        timeView.duringAnimation(transitionValue);
    }

    public float getTransitionValue() {
        return transitionValue;
    }

    class ViewHolder {
        ImageView imageView;
    }
}
