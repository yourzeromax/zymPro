package com.yourzeromax.zympro.LoginUtils;

/**
 * Created by yourzeromax on 2017/12/4.
 */

public interface LoginListener {
    public void login();

    public void checkUserInfo();

    public void sendVerification();

    public void checkVerification();

    public void signUp();
}
