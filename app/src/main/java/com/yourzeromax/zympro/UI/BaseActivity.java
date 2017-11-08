package com.yourzeromax.zympro.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.yourzeromax.zympro.Application.ActivityCollector;


/**
 * Created by yourzeromax on 2017/11/8.
 */

public class BaseActivity extends AppCompatActivity {

    public BaseActivity() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }


    long firstTime = 0;

    @Override
    protected void onDestroy() {
        ActivityCollector.removeActivity(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime < 1000) {
            ActivityCollector.finishAll();
        } else {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = System.currentTimeMillis();

        }
    }
}
