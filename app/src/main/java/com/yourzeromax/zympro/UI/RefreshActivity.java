package com.yourzeromax.zympro.UI;

import android.os.Bundle;

import com.yourzeromax.zympro.R;

public class RefreshActivity extends BaseActivity {
    android.support.v7.widget.Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.refresh_toolbar);
        setSupportActionBar(mToolbar);
    }
}
