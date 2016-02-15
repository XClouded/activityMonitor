package com.ssmiech.activitymonitor;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.IntentFilter;
import android.util.Log;

import com.ssmiech.activitymonitor.internal.ActivityObserver;

import java.util.Observable;
import java.util.Observer;

public class Monitor implements Observer {

    private final Thread monitorThread;
    private ActivityObserver activityObserver;
    private Activity currentActivity;


    public Monitor(Instrumentation instrumentation) {
        activityObserver = new ActivityObserver(instrumentation.addMonitor((IntentFilter) null, null, false));
        monitorThread = new Thread(activityObserver, "activityListenerThread");
    }

    public void startIfNeeded() {

        if (!monitorThread.isAlive()) {
            monitorThread.start();
        }
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void stop() {
        //noop
    }

    @Override
    public void update(Observable observable, Object data) {
        if(Activity.class.isAssignableFrom(data.getClass())) {
            update((Activity) data);
        }
    }

    private void update(Activity activity) {
        Log.i("asdf", activity.toString());
    }
}