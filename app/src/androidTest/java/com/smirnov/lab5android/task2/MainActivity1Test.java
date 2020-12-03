package com.smirnov.lab5android.task2;

import android.content.pm.ActivityInfo;
import android.view.Gravity;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smirnov.lab5android.MainActivity;
import com.smirnov.lab5android.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.PendingIntent.getActivity;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.contrib.DrawerMatchers.isOpen;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;


public class MainActivity1Test {

    @Rule
    public ActivityScenarioRule<MainActivity1> mainActivityRule = new ActivityScenarioRule<>(MainActivity1.class);

    @Test
    public void checkButtonsInFirstActivity() {
        onView(withId(R.id.to_second)).check(matches(isDisplayed()));
        onView(withId(R.id.for_task_4)).check(matches(isDisplayed()));
        onView(withId(R.id.to_first)).check((doesNotExist()));
        onView(withId(R.id.to_third)).check((doesNotExist()));
        checkNavigationDrawer();
    }

    @Test
    public void checkButtonsInSecondActivity() {
        onView(withId(R.id.to_second)).perform(click());
        onView(withId(R.id.to_first)).check(matches(isDisplayed()));
        onView(withId(R.id.to_third)).check(matches(isDisplayed()));
        onView(withId(R.id.to_second)).check((doesNotExist()));
        onView(withId(R.id.for_task_4)).check((doesNotExist()));
        checkNavigationDrawer();
    }

    @Test
    public void checkButtonsInThirdActivity() {
        onView(withId(R.id.to_second)).perform(click());
        onView(withId(R.id.to_third)).perform(click());
        onView(withId(R.id.to_second)).check(matches(isDisplayed()));
        onView(withId(R.id.for_task_4)).check((doesNotExist()));
        onView(withId(R.id.to_first)).check(matches(isDisplayed()));
        onView(withId(R.id.to_third)).check((doesNotExist()));
        checkNavigationDrawer();
    }

    @Test
    public void checkNavigationDrawer() {
        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.LEFT))).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen(Gravity.LEFT))).perform(DrawerActions.close());
    }

    @Test
    public void checkButtonBackInSecondActivity() {
        onView(withId(R.id.to_second)).perform(click());
        onView(withId(R.id.drawer_layout)).perform(pressBack());
        checkButtonsInFirstActivity();
    }

    @Test
    public void checkButtonBackInThirdActivity() {
        onView(withId(R.id.to_second)).perform(click());
        onView(withId(R.id.to_third)).perform(click());
        onView(withId(R.id.drawer_layout)).perform(pressBack());

        onView(withId(R.id.to_first)).check(matches(isDisplayed()));
        onView(withId(R.id.to_third)).check(matches(isDisplayed()));
        onView(withId(R.id.to_second)).check((doesNotExist()));
        onView(withId(R.id.for_task_4)).check((doesNotExist()));
    }

    @Test
    public void checkScreenRotationForFirstActivity() {
        checkButtonsInFirstActivity();
        mainActivityRule.getScenario().onActivity(new ActivityScenario.ActivityAction<MainActivity1>() {
            @Override
            public void perform(MainActivity1 activity) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            }
        });
        checkButtonsInFirstActivity();
    }

    @Test
    public void checkScreenRotationForSecondActivity() {
        mainActivityRule.getScenario().onActivity(new ActivityScenario.ActivityAction<MainActivity1>() {
            @Override
            public void perform(MainActivity1 activity) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            }
        });
        onView(withId(R.id.to_second)).perform(click());
        onView(withId(R.id.to_first)).check(matches(isDisplayed()));
        onView(withId(R.id.to_third)).check(matches(isDisplayed()));
        onView(withId(R.id.to_second)).check((doesNotExist()));
        onView(withId(R.id.for_task_4)).check((doesNotExist()));
        checkNavigationDrawer();
    }

    @Test
    public void checkScreenRotationForThirdActivity() {
        onView(withId(R.id.to_second)).perform(click());
        onView(withId(R.id.to_third)).perform(click());
        mainActivityRule.getScenario().onActivity(new ActivityScenario.ActivityAction<MainActivity1>() {
            @Override
            public void perform(MainActivity1 activity) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            }
        });
        onView(withId(R.id.to_second)).check(matches(isDisplayed()));
        onView(withId(R.id.to_first)).check(matches(isDisplayed()));
        onView(withId(R.id.to_third)).check((doesNotExist()));
        onView(withId(R.id.for_task_4)).check((doesNotExist()));
        checkNavigationDrawer();
    }
}