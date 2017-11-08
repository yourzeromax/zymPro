package com.yourzeromax.zympro.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.yourzeromax.zympro.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btn_zc)
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(RegisterActivity.this, TestActivity.class);
        startActivity(intent);
        finish();
    }
}
