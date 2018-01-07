package com.yourzeromax.zympro.Application;

import android.app.Activity;
import android.app.Application;
import org.litepal.exceptions.GlobalException;

import android.app.Application;
import android.content.Context;

import com.mob.MobApplication;

import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.List;

import cn.smssdk.SMSSDK;

/**
 * Created by yourzeromax on 2017/11/3.
 */

public class MyApplication extends MobApplication {


    /**
     * Global application context.
     */
    static Context sContext;

    /**
     * Construct of LitePalApplication. Initialize application context.
     */
    public MyApplication() {
        sContext = this;
    }


    @Deprecated
    public static void initialize(Context context) {
        sContext = context;
    }

    /**
     * Get the global application context.
     *
     * @return Application context.
     * @throws org.litepal.exceptions.GlobalException
     */
    public static Context getContext() {
        if (sContext == null) {
            throw new GlobalException(GlobalException.APPLICATION_CONTEXT_IS_NULL);
        }
        return sContext;
    }
}
