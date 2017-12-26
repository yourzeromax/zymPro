package com.yourzeromax.zympro.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.EventLog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yourzeromax.zympro.Application.C;
import com.yourzeromax.zympro.R;
import com.yourzeromax.zympro.Utils.LoginUtils;
import com.yourzeromax.zympro.Utils.NetUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.et_count)
    EditText etCount;
    @Bind(R.id.et_password)
    EditText etPassword;
@Bind(R.id.tv_yhzc)
    TextView tvYhzc;
    SharedPreferences sharedPreferences;
    private static final String TAG = "LoginActivity";
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
        tvYhzc.setOnClickListener(this);
        editTextInit();
        checkIsNeedUpdate();
    }


    private void checkIsNeedUpdate() {
        sharedPreferences = getSharedPreferences(MainActivity.VERSION_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        NetUtils.getVersion(new NetUtils.VersionListener() {
            @Override
            public void saveVersion(float version) {
                editor.putFloat("newVersion", version);
                editor.apply();
            }

            @Override
            public void isNeedUpdate() {
                float nowVersion = sharedPreferences.getFloat("nowVersion", 0);
                float newVersion = sharedPreferences.getFloat("newVersion", 0);
                if (nowVersion < newVersion) {
                    NetUtils.getCommunityFromNet(C.URL_Community);
                    NetUtils.getSchoolsFromNet();
                    editor.putFloat("nowVersion", newVersion);
                    editor.apply();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void editTextInit() {
        etCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 1) {
                    etCount.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.error), null);
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
                    etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.error), null);
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
        switch (view.getId()){
            case R.id.tv_yhzc:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case  R.id.btn_login:
                login();
                break;
        }
       
      
        
    }

    private void login(){
String count = etCount.getText().toString().trim();
String password = etPassword.getText().toString().trim();
        LoginUtils.login(count, password, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String string = response.body().string();
                Log.d(TAG, "onResponse: " + string);
                try {
                    JSONObject object = new JSONObject(string);
                    final int status = object.getInt("statusNumber");
                    Log.d(TAG, "onResponse: " + status);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(status==0){
                                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                            }else if(status==1){
                                    Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                                }
                            else if(status==4){
                                    Toast.makeText(LoginActivity.this, "您的账号未注册哟～", Toast.LENGTH_SHORT).show();
                                }}
                        });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
