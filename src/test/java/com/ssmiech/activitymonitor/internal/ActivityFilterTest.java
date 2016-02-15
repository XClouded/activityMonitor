package com.ssmiech.activitymonitor.internal;

import android.app.Activity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActivityFilterTest {
    ActivityFilter activityFilter;

    private Activity newActivity;
    private Activity closingActivity;
    private Activity initialActivity;

    @Before
    public void setUp() {
        initialActivity = mock(Activity.class);
        newActivity = mock(Activity.class);
        closingActivity = mock(Activity.class);
        activityFilter = new ActivityFilter();

        when(closingActivity.isFinishing()).thenReturn(true);
    }

    @Test
    public void shouldReturnCurrentlyVisibleActivity() {
        activityFilter.handleActivity(initialActivity);
        activityFilter.handleActivity(newActivity);

        assertThat(activityFilter.getCurrentlyVisibleActivity(), is(newActivity));
    }

    @Test
    public void shouldIgnoreClosingActivites() {
        activityFilter.handleActivity(newActivity);
        activityFilter.handleActivity(closingActivity);

        assertThat(activityFilter.getCurrentlyVisibleActivity(), is(newActivity));
    }

    @Test
    public void shouldReturnAllActivities() {
        activityFilter.handleActivity(newActivity);
        activityFilter.handleActivity(newActivity);
        activityFilter.handleActivity(newActivity);

        assertThat(activityFilter.getActivitiesInBackStack().size(), is(3));
    }

    @Test
    public void shouldRemoveActivityIfFinishing() {
        activityFilter.handleActivity(initialActivity);
        activityFilter.handleActivity(newActivity);
        when(newActivity.isFinishing()).thenReturn(true);

        activityFilter.handleActivity(newActivity);

        List<Activity> activityList = new ArrayList<Activity>() {
            {
                add(initialActivity);
            }
        };

        assertThat(activityFilter.getActivitiesInBackStack(), is(activityList));
    }

}
