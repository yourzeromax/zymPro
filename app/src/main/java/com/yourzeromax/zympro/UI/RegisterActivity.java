package com.yourzeromax.zympro.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yourzeromax.zympro.LoginUtils.LoginUtils;
import com.yourzeromax.zympro.R;

import org.json.JSONException;
import org.json.JSONObject;

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

    LoginUtils loginUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // SMSSDK.setAskPermisionOnReadContact(boolShowInDialog);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        btnRegister.setOnClickListener(this);
        btnVerification.setOnClickListener(this);
        setSMSSDK();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_verification:
                sendVerification();
                break;
            case R.id.btn_zc:
                checkVerification();
        }
    }

    public void setSMSSDK() {
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable) data;
                    final String msg = throwable.getMessage();
                    try {
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");//错误描述
                        int status = object.optInt("status");//错误代码
                        Log.d(TAG, "afterEvent: " + status);
                        if (status == 468) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "验证码错误！", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (result == SMSSDK.RESULT_COMPLETE) {

                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "success", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }

    public void sendVerification() {
        phoneNumber = etCount.getText().toString().trim();
        if (phoneNumber == "") {
            Toast.makeText(this, "不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            SMSSDK.getVerificationCode("86", phoneNumber);
            Toast.makeText(this, "已发送验证码", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkVerification() {
        phoneNumber = etCount.getText().toString().trim();
        String verification = etVerification.getText().toString().trim();
        SMSSDK.submitVerificationCode("+86", phoneNumber, verification);
    }

}
