package com.yourzeromax.zympro.UI;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.yourzeromax.zympro.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.et_count)
    EditText etCount;
    @Bind(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        btnLogin.setOnClickListener(this);
        editTextInit();
    }


    private void editTextInit() {
        etCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 1) {
                    etCount.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.mipmap.error), null);
                } else {
                    etCount.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 1) {
                    etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.mipmap.error), null);
                } else {
                    etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etCount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    int eventX = (int) motionEvent.getRawX();
                    int eventY = (int) motionEvent.getRawY();
                    Rect rect = new Rect();
                    etCount.getGlobalVisibleRect(rect);
                    rect.left = rect.right - 50;
                    if (rect.contains(eventX, eventY))
                        etCount.setText("");
                }
                return false;
            }
        });

        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    int eventX = (int) motionEvent.getRawX();
                    int eventY = (int) motionEvent.getRawY();
                    Rect rect = new Rect();
                    etPassword.getGlobalVisibleRect(rect);
                    rect.left = rect.right - 50;
                    if (rect.contains(eventX, eventY))
                        etPassword.setText("");
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

}
