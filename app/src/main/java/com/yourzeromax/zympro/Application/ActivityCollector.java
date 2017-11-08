package com.yourzeromax.zympro.Application;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yourzeromax on 2017/11/8.
 */

public class ActivityCollector {                //管理所有的Activity
    private static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }
}