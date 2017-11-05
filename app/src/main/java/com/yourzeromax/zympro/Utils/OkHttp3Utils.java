package com.yourzeromax.zympro.Utils;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

/**
 * Created by yourzeromax on 2017/11/3.
 */

public class OkHttp3Utils {
    private static OkHttp3Utils okHttp3Utils;

    private OkHttp3Utils() {
    }

    public static OkHttp3Utils getInstance() {
        if (okHttp3Utils == null) {
            synchronized (OkHttp3Utils.class) {
                if (okHttp3Utils == null) {
                    okHttp3Utils = new OkHttp3Utils();
                }
            }
        }
        return okHttp3Utils;
    }

    public  void sendOkHttp3Request(String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
