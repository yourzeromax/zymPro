package com.yourzeromax.zympro.Utils;

import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.yourzeromax.zympro.Application.C;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by yourzeromax on 2017/12/4.
 */

public class LoginUtils {
    private static final String TAG = "LoginUtils";

    public static void signUp(String appKey, String username, String passwordCode, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        //body默认采用UTF-8，但是编码方式会导致解码失败，需采用ISO；
        String str = "appKey=" + appKey + "&username=" + username + "&passwordCode=" + passwordCode;//组装请求post
        //   Log.d(TAG, "signUp: "+str);
        Request request = new Request.Builder().url(C.URL_SIGNUP)
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=ISO-8859-1"), str))
                .build();
        client.newCall(request).enqueue(callback);
    }


    public static void login(String username, String password, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("username", username);
        builder.add("password", password);
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(C.URL_LOGIN)
                .post(body).build();
        client.newCall(request).enqueue(callback);
    }

    public static int hasCount(String username) {
        int status;
        OkHttpClient client = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("username", username);
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(C.URL_LOGIN)
                .post(body).build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject object = new JSONObject(response.body().string());
            status = object.getInt("statusNumber");
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
