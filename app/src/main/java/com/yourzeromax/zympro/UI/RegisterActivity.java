package com.yourzeromax.zympro.UI;

import android.os.Bundle;
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
    @Bind(R.id.et_password)
    EditText etPassword;

    String phoneNumber;
boolean checkVerification = false;
    public static final int CLICKABLE = 0;
    public static final int UNCLICKABLE = 1;

    public static final int CHECKHASCOUNT=2;

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
                case CHECKHASCOUNT:
                    if(msg.arg1==4){            //如果未注册
                        Toast.makeText(RegisterActivity.this, "恭喜您，可以注册", Toast.LENGTH_SHORT).show();
                        btnVerification.setBackground(getResources().getDrawable(R.drawable.login_btn));
                        btnVerification.setEnabled(true);
                    }else {
                        Toast.makeText(RegisterActivity.this, "您的账号已被注册！", Toast.LENGTH_SHORT).show();
                        btnVerification.setBackground(getResources().getDrawable(R.drawable.login_btn_unclick));
                        btnVerification.setEnabled(false);
                    }
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
        listenerInit();
        setSMSSDK();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    private void listenerInit(){
        btnRegister.setOnClickListener(this);
        btnVerification.setOnClickListener(this);
        etCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(charSequence.length()==11){//如果输入了11位,判断账号是否存在
                        Log.d(TAG, "onTextChanged: ");
                        final String number = new String(new StringBuilder(charSequence));
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                int status = LoginUtils.hasCount(number);
                                Log.d(TAG, "run: "+String.valueOf(status));
                                Message message = Message.obtain();
                                message.what=CHECKHASCOUNT;
                                        message.arg1=status;
                                        handler.sendMessage(message);
                            }
                        }).start();

                    }else{
                        Message message = Message.obtain();
                        message.what=UNCLICKABLE;
                        handler.sendMessage(message);
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
                sendVerification();
                break;
            case R.id.btn_zc:
                String password = etPassword.getText().toString().trim();
                String count = etCount.getText().toString().trim();
                if(password.length()<6){
                    Toast.makeText(this, "请检查输入格式！", Toast.LENGTH_SHORT).show();
                    return;
                }
                // checkVerification();
                signUp(count,password);
                break;
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
                                    Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
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
                                    checkVerification=true;
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
        if (!OtherUtils.isPhoneNumber(phoneNumber)) {
            Toast.makeText(this, "请输入正确的号码！", Toast.LENGTH_SHORT).show();
        } else {
            SMSSDK.getVerificationCode("86", phoneNumber);
            Toast.makeText(this, "已发送验证码", Toast.LENGTH_SHORT).show();
            Message msg = Message.obtain();
            msg.what = UNCLICKABLE;
            handler.sendMessage(msg);
            Message msg2 = Message.obtain();
            msg2.what = CLICKABLE;
            handler.sendMessageDelayed(msg2, 5000);
        }
    }

    public void checkVerification() {
        phoneNumber = etCount.getText().toString().trim();
        String verification = etVerification.getText().toString().trim();
        SMSSDK.submitVerificationCode("+86", phoneNumber, verification);
    }

    public void signUp(String count,String password) {
        String send_appKey;
        String send_password;

        checkVerification();
        if(!checkVerification){
            Toast.makeText(this, "验证码不正确", Toast.LENGTH_SHORT).show();
            return ;
        }

        if(!OtherUtils.isPhoneNumber(count)){//控制为正确的电话号码
            Toast.makeText(this, "请输入正确的电话号码！", Toast.LENGTH_SHORT).show();
            return;
        }
       // count= count.substring(0,8);
        long countNum = Long.parseLong(count);
        long appkey = System.currentTimeMillis() + countNum;
        String appKey = String.valueOf(appkey);
        byte[] app_key = Encrypt.encrypt(appKey.getBytes(), count);
        byte[] pass_word = Encrypt.encrypt(password.getBytes(), count);
        try {
            send_appKey = new String(app_key, "ISO-8859-1");
            send_password = new String(pass_word, "ISO-8859-1");
            LoginUtils.signUp(send_appKey, count, send_password, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String string = response.body().string();
                    Log.d(TAG, "onResponse: " + string);
                    try {

                        JSONObject object = new JSONObject(string);

                        int status = object.getInt("statusNumber");
                        Log.d(TAG, "onResponse: " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        finish();
    }
}
