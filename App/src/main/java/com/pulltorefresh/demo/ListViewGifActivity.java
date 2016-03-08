package com.pulltorefresh.demo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pulltorefresh.PtrDefaultHandler;
import com.pulltorefresh.PtrFrameLayout;
import com.pulltorefresh.PtrGifFrameLayout;
import com.pulltorefresh.loadmore.LoadMoreListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xjz on 2016/2/24
 */
@EActivity(R.layout.listview_gif_activity)
public class ListViewGifActivity extends Activity {

    private ListViewAdapter mAdapter;
    private List<String> mData = new ArrayList<>();

    @AfterViews
    void afterViews() {

        mAdapter = new ListViewAdapter(this, mData);
        lvListView.setAdapter(mAdapter);
        setRefreshListener();
        loadData();
    }

    @ViewById
    PtrGifFrameLayout ptrGifFrameLayout;
    @ViewById
    LoadMoreListView lvListView;

    void setRefreshListener() {
        ptrGifFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadDataDelay();
            }
        });
        lvListView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
    }

    @UiThread(delay = 1000)
    void loadMoreData() {
        mData.add("  Item - " + (mData.size() + 1));
        mData.add("  Item - " + (mData.size() + 1));
        mData.add("  Item - " + (mData.size() + 1));
        if (mData.size() >= 20) { //到达列表底部
            lvListView.onLoadMoreComplete(false);
        } else {
            lvListView.onLoadMoreComplete(true);
        }
    }


    @Background
    void loadData() {
        mData.clear();
        for (int i = 0; i < 12; i++) {
            mData.add("  Item  - " + i);
        }
        loadFinish();
    }

    @Background(delay = 3000)
    void loadDataDelay() {
        mData.clear();
        for (int i = 0; i < 12; i++) {
            mData.add("  Item  - " + i);
        }
        loadFinish();
    }

    @UiThread
    void loadFinish() {
        ptrGifFrameLayout.refreshComplete();
        mAdapter.notifyDataSetChanged();
    }

    public class ListViewAdapter extends BaseAdapter {
        private List<String> datas;
        private LayoutInflater inflater;

        public ListViewAdapter(Context context, List<String> data) {
            super();
            inflater = LayoutInflater.from(context);
            datas = data;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(datas.get(position));
            textView.setTextColor(0xff4f4f4f);
            return convertView;
        }
    }
}
