package com.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;

/**
 * by xjz
 */
public class PtrGifFrameLayout extends PtrFrameLayout {

    private PtrGifHeaderView mDbHeaderView;

    public PtrGifFrameLayout(Context context) {
        super(context);
        initViews();
    }

    public PtrGifFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrGifFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        mDbHeaderView = new PtrGifHeaderView(getContext());
        setHeaderView(mDbHeaderView);
        addPtrUIHandler(mDbHeaderView);
    }
}
