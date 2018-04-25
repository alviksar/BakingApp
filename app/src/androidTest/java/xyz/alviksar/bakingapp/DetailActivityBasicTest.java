package xyz.alviksar.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DetailActivityBasicTest {

    @Rule public ActivityTestRule<StepDetailActivity> myActivityTestRule
            = new ActivityTestRule<>(StepDetailActivity.class);

    @Test public void clickNextButton_ShowNextStep() {
        onView((withId(R.id.btn_next_step))).perform(click());

        onView((withId(R.id.tv_step_description))).check(matches(withText("1")));

    }
}
