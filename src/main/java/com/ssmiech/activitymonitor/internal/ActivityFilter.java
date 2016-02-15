package com.ssmiech.activitymonitor.internal;

import android.app.Activity;

import java.util.List;
import java.util.Stack;

public class ActivityFilter {

    private Stack<Activity> activitiesStack = new Stack<Activity>();

    public void handleActivity(Activity activity) {
        if(activity.isFinishing()) {
            activitiesStack.remove(activity);
        } else {
            activitiesStack.push(activity);
        }
    }

    public Activity getCurrentlyVisibleActivity() {
        return activitiesStack.peek();
    }

    public List<Activity> getActivitiesInBackStack() {
        return activitiesStack;
    }
}
