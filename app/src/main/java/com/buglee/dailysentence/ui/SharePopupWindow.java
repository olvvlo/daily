package com.buglee.dailysentence.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Toast;

import com.buglee.dailysentence.R;
import com.bumptech.glide.Glide;

import static com.buglee.dailysentence.ui.Utils.saveImageToGallery;
import static com.buglee.dailysentence.ui.Utils.shotScrollView;

/**
 * Created by LEE on 2017/5/3 0003.
 */

public class SharePopupWindow extends PopupWindow {
    private Context mContext;
    private View contentView;
    private Button mSaveBtn;
    private Button mShareBtn;
    private ImageView mImageView;
    private ImageView mCloseImageView;
    private ScrollView mScrollView;
    private Bitmap mBitmap;

    public SharePopupWindow(Context context, String url) {
        super(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContext = context;
        setFocusable(true);
        findViews();

        Glide.with(mContext)
                .load(url)
                .into(mImageView);

        setListener();

        this.setContentView(contentView);
        //this.setAnimationStyle(R.style.PopupWindowAnimationBottom);
    }

    private void findViews() {
        contentView = LayoutInflater.from(mContext).inflate(R.layout.share_popup, null);
        mImageView = contentView.findViewById(R.id.share_imageView);
        mSaveBtn = contentView.findViewById(R.id.save_btn);
        mShareBtn = contentView.findViewById(R.id.share_btn);
        mScrollView = contentView.findViewById(R.id.popup_scrollView);
        mCloseImageView = contentView.findViewById(R.id.image_close);
    }

    private void setListener() {
        contentView.setFocusable(true);
        contentView.setOnKeyListener((v, keyCode, event) -> {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        dismiss();
                        return true;
                    }
                    return false;
                }
        );
        mShareBtn.setOnClickListener(v -> {
            mBitmap = shotScrollView(mScrollView);
            Intent intent = new Intent(Intent.ACTION_SEND);
            Uri uri = Uri.fromFile(saveImageToGallery(mContext, mBitmap));
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setType("image/*");
            mContext.startActivity(Intent.createChooser(intent, "分享"));
        });
        mSaveBtn.setOnClickListener(v -> {
            mBitmap = shotScrollView(mScrollView);
            saveImageToGallery(mContext, mBitmap);
            dismiss();
            Toast.makeText(mContext, "保存成功", Toast.LENGTH_LONG).show();
        });
        mCloseImageView.setOnClickListener(v -> {
            dismiss();
        });

    }
}
