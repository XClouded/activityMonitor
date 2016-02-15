package com.ssmiech.activitymonitor.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Instrumentation;
import android.os.Build;
import android.util.Log;

import java.util.Observable;


public final class ActivityObserver extends Observable implements Runnable {

    private final Instrumentation.ActivityMonitor activityMonitor;
    private Activity currentActivity;

    public ActivityObserver(Instrumentation.ActivityMonitor activityMonitor) {
        this.activityMonitor = activityMonitor;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void run() {
        while (true) {
            Activity activity = activityMonitor.waitForActivity();
            if(activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
                Log.i("ActivityMonitor:", activity.toString());
                currentActivity = activity;
            }
        }
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }
}
