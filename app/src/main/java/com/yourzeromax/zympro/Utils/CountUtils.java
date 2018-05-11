package com.yourzeromax.zympro.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yourzeromax on 2018/4/4.
 */

public class CountUtils {
    Context context;
    private static String COUNT = "COUNT";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public CountUtils(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(CountUtils.COUNT, Context.MODE_PRIVATE);
        editor = context.getSharedPreferences(CountUtils.COUNT, Context.MODE_PRIVATE).edit();
    }

    public boolean hasAcount(String account) {
        return sharedPreferences.getBoolean(account+"hasacount", false);
    }

    public boolean isPasswordRight(String account, String password) {
        String p = sharedPreferences.getString(account, "0");
        if (p.equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public void addNewAcount(String account, String password) {
        editor.putString(account, password);
        editor.putBoolean(account+"hasacount", true);
        editor.commit();
    }
}
