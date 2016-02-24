package com.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.pulltorefresh.indicator.PtrIndicator;
import com.chanven.commonpulltorefreshview.R;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * by xjz
 */
public class PtrGifHeaderView extends FrameLayout implements PtrUIHandler {


    public PtrGifHeaderView(Context context) {
        super(context);
        initViews();
    }

    public PtrGifHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrGifHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    public GifImageView mHeaderImage;

    protected void initViews() {
        try {
            buildAnimation();
            View header = LayoutInflater.from(getContext()).inflate(R.layout.db_base_header_view, this);
            mHeaderImage = (GifImageView) header.findViewById(R.id.pull_to_refresh_image);
        } catch (Exception ignored) {
        }
    }

    private GifDrawable mIdelDrawble;
    private GifDrawable mGifPullDrawable;
    private GifDrawable mGifRefresingDrawble;

    private void buildAnimation() throws Exception {
        mIdelDrawble = new GifDrawable(getResources(), R.drawable.ptr_refresh_idle);
        mGifPullDrawable = new GifDrawable(getResources(), R.drawable.ptr_refresh_pulling);
        mGifRefresingDrawble = new GifDrawable(getResources(), R.drawable.ptr_refresh_refreshing);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        Log.d("DdHeaderView", "onUIReset");
        pauseGif();
    }

    private void pauseGif() {
        if (mGifRefresingDrawble != null && mGifRefresingDrawble.isPlaying()) {
            mGifRefresingDrawble.pause();
        }
        if (mGifPullDrawable != null && mGifPullDrawable.isPlaying()) {
            mGifPullDrawable.pause();
        }
        if (mIdelDrawble != null && mIdelDrawble.isPlaying()) {
            mIdelDrawble.pause();
        }
        mHeaderImage.setImageDrawable(mIdelDrawble);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        Log.d("DdHeaderView", "onUIRefreshPrepare");
        mHeaderImage.setImageDrawable(mIdelDrawble);
        if (!mIdelDrawble.isPlaying()) {
            mIdelDrawble.start();
        }
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        Log.d("DdHeaderView", "onUIRefreshBegin");
        mHeaderImage.setImageDrawable(mGifRefresingDrawble);
        if (!mGifRefresingDrawble.isPlaying()) {
            mGifRefresingDrawble.start();
        }
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        Log.d("DdHeaderView", "onUIRefreshComplete");
        pauseGif();
    }


    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();
        Log.d("DdHeaderView", "onUIPositionChange " + mOffsetToRefresh + " " + currentPos + " " + lastPos);
        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromBottomUnderTouch(frame);
            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromTopUnderTouch(frame);
            }
        }
    }

    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        Log.d("DdHeaderView", "crossRotateLineFromTopUnderTouch");
        mHeaderImage.setImageDrawable(mGifPullDrawable);
        if (!mGifPullDrawable.isPlaying()) {
            mGifPullDrawable.start();
        }
    }

    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
        Log.d("DdHeaderView", "crossRotateLineFromBottomUnderTouch");
        mHeaderImage.setImageDrawable(mIdelDrawble);
        if (!mIdelDrawble.isPlaying()) {
            mIdelDrawble.start();
        }
    }
}
