package com.yourzeromax.zympro.Utils;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yourzeromax.zympro.Application.C;
import com.yourzeromax.zympro.JavaBeans.Community;
import com.yourzeromax.zympro.JavaBeans.Version;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by yourzeromax on 2017/11/11.
 */

public class NetUtils {
    public static void getSchoolsFromNet() {
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

    public static void getCommunityFromNet(String url) {
        OkHttp3Utils.getInstance().sendOkHttp3Request(url, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray array = jsonObject.getJSONArray("list");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        Community community = new Community();
                        community.setSort(object.getString("sort"));
                        community.setDescription(object.getString("description"));
                        community.setPic_1(object.getString("pic_1"));
                        community.setPic_2(object.getString("pic_2"));
                        community.setPic_3(object.getString("pic_3"));
                        community.save();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static void getVersion(final VersionListener listener) {
        OkHttp3Utils.getInstance().sendOkHttp3Request(C.URL_VERSION, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                try{
                JSONObject jsonObject = new JSONObject(s);
                String v = jsonObject.getString("志愿谋");
                float version = Float.parseFloat(v);
                listener.saveVersion(version);
                listener.isNeedUpdate();}catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

   public  interface VersionListener {
        public void saveVersion(float version);
        public void isNeedUpdate();
    }
}
