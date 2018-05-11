package com.yourzeromax.zympro.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yourzeromax.zympro.R;
import com.yourzeromax.zympro.Utils.CountUtils;
import com.yourzeromax.zympro.Utils.Encrypt;
import com.yourzeromax.zympro.Utils.LoginUtils;
import com.yourzeromax.zympro.Utils.OtherUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";
    @Bind(R.id.btn_zc)
    Button btnRegister;
    EventHandler eventHandler;

    @Bind(R.id.btn_verification)
    Button btnVerification;

    @Bind(R.id.et_count)
    EditText etCount;

    @Bind(R.id.et_verification)
    EditText etVerification;

    String phoneNumber;
    boolean checkVerification = false;
    public static final int CLICKABLE = 0;
    public static final int UNCLICKABLE = 1;

    final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);

    private CountUtils countUtils;
    //容易内存泄漏
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CLICKABLE:
                    btnVerification.setEnabled(true);
                    btnVerification.setBackground(getResources().getDrawable(R.drawable.login_btn));
                    break;
                case UNCLICKABLE:
                    btnVerification.setEnabled(false);
                    btnVerification.setBackground(getResources().getDrawable(R.drawable.login_btn_unclick));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  SMSSDK.setAskPermisionOnReadContact(boolShowInDialog);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        countUtils = new CountUtils(this);
        listenerInit();
        setSMSSDK();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    private void listenerInit() {
        btnRegister.setOnClickListener(this);
        btnVerification.setOnClickListener(this);
        etCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 11) {//如果输入了11位,判断账号是否存在
                    Log.d(TAG, "onTextChanged: ");
                    final String number = new String(new StringBuilder(charSequence));
                    if (countUtils.hasAcount(number)) {
                        Toast.makeText(RegisterActivity.this, "用户已经存在", Toast.LENGTH_SHORT).show();
                        Message msg = Message.obtain();
                        msg.what = UNCLICKABLE;
                        handler.sendMessage(msg);
                        //处理按钮为不可点击的状态
                    } else {
                        Toast.makeText(RegisterActivity.this, "恭喜您，该账户可以注册！", Toast.LENGTH_SHORT).show();
                        Message msg = Message.obtain();
                        msg.what = CLICKABLE;
                        handler.sendMessage(msg);
                        //可点击状态
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_verification:
                myCountDownTimer.start();
                sendVerification();
                break;
            case R.id.btn_zc:
                String count = etCount.getText().toString().trim();
                signUp(count);
                break;
        }
    }

    public void setSMSSDK() {
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Log.d(TAG, "afterEvent: " + "提交验证码成功");
                        toNextActivity();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Log.d(TAG, "afterEvent: " + "获取验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }

    public void sendVerification() {
        phoneNumber = etCount.getText().toString().trim();
        if (!OtherUtils.isPhoneNumber(phoneNumber)) {
            Toast.makeText(this, "请输入正确的号码！", Toast.LENGTH_SHORT).show();
        } else {
            SMSSDK.getVerificationCode("86", phoneNumber);
            //  Toast.makeText(this, "已发送验证码", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkVerification() {
        phoneNumber = etCount.getText().toString().trim();
        String verification = etVerification.getText().toString().trim();
        SMSSDK.submitVerificationCode("+86", phoneNumber, verification);
    }

    public void signUp(String count) {
        checkVerification();
        if (!checkVerification) {
            //  Toast.makeText(this, "验证码不正确", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!OtherUtils.isPhoneNumber(count)) {//控制为正确的电话号码
            Toast.makeText(this, "请输入正确的电话号码！", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void toNextActivity() {
        String count = etCount.getText().toString().trim();
        Intent intent = new Intent(RegisterActivity.this, Register2Activity.class);
        intent.putExtra("count", count);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        finish();
    }

    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            btnVerification.setClickable(false);
            btnVerification.setBackground(getResources().getDrawable(R.drawable.login_btn_unclick));
            btnVerification.setText(l / 1000 + "s");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            btnVerification.setText("获取验证码");
            //设置可点击
            btnVerification.setClickable(true);
            btnVerification.setBackground(getResources().getDrawable(R.drawable.login_btn));
        }
    }
}
