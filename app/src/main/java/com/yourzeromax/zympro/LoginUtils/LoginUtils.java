package com.yourzeromax.zympro.LoginUtils;

import android.content.Context;

/**
 * Created by yourzeromax on 2017/12/4.
 */

public class LoginUtils implements LoginListener {
    Context context;
    public String phoneNumber;

    public LoginUtils(String phoneNumber,Context context) {
        this.phoneNumber = phoneNumber;
        this.context = context;
    }

    public void login() {

    }

    @Override
    public void checkUserInfo() {

    }

    @Override
    public void sendVerification() {

    }

    @Override
    public void checkVerification() {

    }

    @Override
    public void signUp() {

    }
}
