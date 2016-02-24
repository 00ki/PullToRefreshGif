package com.pulltorefresh.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


import org.androidannotations.annotations.EActivity;

/**
 * Created by xjz on 2016/2/24
 */
@EActivity
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goListView(View view) {
        ListViewActivity_.intent(this).start();
    }
}
