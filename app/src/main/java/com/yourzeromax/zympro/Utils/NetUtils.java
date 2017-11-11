package com.yourzeromax.zympro.Utils;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yourzeromax.zympro.Application.C;

import java.io.IOException;

/**
 * Created by yourzeromax on 2017/11/11.
 */

public class NetUtils {
    public static void getSchoolsFromNet(){
         OkHttp3Utils.getInstance().sendOkHttp3Request(C.URL, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                    String s = response.body().string();
                //进行数据库的操作，存储。
            }
        });
    }
}
