package com.yourzeromax.zympro.Application;

import android.content.Context;

import org.litepal.LitePalApplication;
import org.litepal.exceptions.GlobalException;

/**
 * Created by yourzeromax on 2017/11/3.
 */

public class MyApplication extends LitePalApplication {

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
