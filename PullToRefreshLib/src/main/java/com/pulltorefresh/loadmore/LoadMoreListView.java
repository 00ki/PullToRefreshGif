package com.pulltorefresh.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pulltorefresh.R;


public class LoadMoreListView extends ListView implements OnScrollListener {

    private static final String TAG = "LoadMoreListView";

    /**
     * Listener that will receive notifications every time the list scrolls.
     */
    private OnScrollListener mOnScrollListener;
    private LayoutInflater mInflater;

    // footer view
    private LinearLayout mFooterView;
    private ProgressBar footerBar;
    private TextView footerTv;

    // Listener to process load more items when user reaches the end of the list
    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadMoreListView(Context context) {
        super(context);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // footer
        mFooterView = (LinearLayout) mInflater.inflate(R.layout.loadmore_default_footer, this, false);
        footerBar = (ProgressBar) mFooterView.findViewById(R.id.loadmore_default_footer_progressbar);
        footerTv = (TextView) mFooterView.findViewById(R.id.loadmore_default_footer_tv);
        mFooterView.setVisibility(View.INVISIBLE);
        setFooterViewBackgroundColor(0xffffffff);
        addFooterView(mFooterView);
        super.setOnScrollListener(this);
    }

    public void setFooterViewBackgroundColor(int res_id) {
        if (mFooterView != null) {
            mFooterView.setBackgroundColor(res_id);
        }
    }

    public void setFooterViewVisibility(int visibile) {
        mFooterView.setVisibility(visibile);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    /**
     * Set the listener that will receive notifications every time the list
     * scrolls.
     *
     * @param l The scroll listener.
     */
    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mOnScrollListener = l;
    }

    /**
     * Register a callback to be invoked when this list reaches the end (last
     * item be visible)
     *
     * @param onLoadMoreListener The callback to run.
     */

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    private boolean isLoading = false;
    private boolean isLoadMoreEnable = true;

    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && view.getLastVisiblePosition() + 1 == view.getCount()) {// 如果滚动到最后一行
            if (isLoadMoreEnable && !isLoading) {
                // 此处可加入网络是否可用的判断
                onLoadMore();
            }
        }

        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(view, scrollState);
        }

    }

    private OnClickListener onClickLoadMoreListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            onLoadMore();
        }
    };

    private void onLoadMore() {
        Log.d(TAG, "onLoadMore");
        showLoading();
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.onLoadMore();
        }
    }

    /**
     * Notify the loading more operation has finished
     */
    public void onLoadMoreComplete(boolean has_more) {
        isLoading = false;
        if (has_more) {
            showNormal();
        } else {
            showNoMore();
        }
    }

    private void showLoading() {
        mFooterView.setVisibility(View.VISIBLE);
        isLoading = true;
        footerTv.setText("正在加载更多...");
        footerBar.setVisibility(View.VISIBLE);
        footerTv.setOnClickListener(null);
    }

    private void showNormal() {
        mFooterView.setVisibility(View.VISIBLE);
        isLoadMoreEnable = true;
        footerTv.setText("点击上拉加载更多...");
        footerBar.setVisibility(View.GONE);
        footerTv.setOnClickListener(onClickLoadMoreListener);
    }

    private void showNoMore() {
        mFooterView.setVisibility(View.VISIBLE);
        isLoadMoreEnable = false;
        footerTv.setText("已到达列表最底部~");
        footerBar.setVisibility(View.GONE);
        footerTv.setOnClickListener(null);
    }

    /**
     * Interface definition for a callback to be invoked when list reaches the
     * last item (the user load more items in the list)
     */
    public interface OnLoadMoreListener {
        /**
         * Called when the list reaches the last item (the last item is visible
         * to the user)
         */
        public void onLoadMore();
    }

}
