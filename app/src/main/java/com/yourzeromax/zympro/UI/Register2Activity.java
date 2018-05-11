package com.yourzeromax.zympro.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yourzeromax.zympro.R;
import com.yourzeromax.zympro.Utils.CountUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Register2Activity extends AppCompatActivity implements View.OnClickListener {
    String count;
    @Bind(R.id.et_count2)
    EditText etCount2;
    @Bind(R.id.et_password2)
    EditText etPassword2;
    @Bind(R.id.btn_zc)
    Button btZc;

    CountUtils countUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        ButterKnife.bind(this);
        countUtils = new CountUtils(this);
        Intent intent = getIntent();
        count = intent.getStringExtra("count");
        etCount2.setText(count);
        btZc.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String count = etCount2.getText().toString().trim();
        String password = etPassword2.getText().toString().trim();
        countUtils.addNewAcount(count, password);
        Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Register2Activity.this, MainActivity.class));
    }
}
