package xyz.alviksar.bakingapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import xyz.alviksar.bakingapp.model.Recipe;
import xyz.alviksar.bakingapp.model.Step;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.StringContains.containsString;
import static xyz.alviksar.bakingapp.util.TestUtils.getTestRecipe;

@RunWith(AndroidJUnit4.class)
public class StepDetailActivityTest {

    @Rule
    // third parameter is set to false which means the activity is not started automatically
    public ActivityTestRule<StepDetailActivity> mStepDetailActivityTestRule
            = new ActivityTestRule<>(StepDetailActivity.class, true, false);


    @Before
    public void setUp() {
        Recipe recipe = getTestRecipe();
        Intent intent = new Intent();
        intent.putExtra(Recipe.PARCEBLE_NAME, recipe);
        intent.putExtra(Step.PARCEBLE_NAME, 3);
        mStepDetailActivityTestRule.launchActivity(intent);
    }

    @Test
    public void startActivity_ShowStepDetail() {

        // Check step #3
        onView(withId(R.id.tv_step_description))
                .check(matches(withText(containsString("3."))));

        // Check if ExoPlayer is not visible for this step
        onView(withId(R.id.pv_video))
                .check(matches(not(isDisplayed())));

        if (mStepDetailActivityTestRule.getActivity().getApplicationContext()
                .getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {

            //  Check move over step
            onView((withId(R.id.btn_next_step))).perform(click());
            onView((withId(R.id.btn_next_step))).perform(click());
            onView(withId(R.id.tv_step_description))
                    .check(matches(withText(containsString("5."))));

            // Check if ExoPlayer is visible for this step
            onView(withId(R.id.pv_video))
                    .check(matches(isDisplayed()));

            // Check the Previous button
            onView((withId(R.id.btn_prev_step))).perform(click());
            onView(withId(R.id.tv_step_description))
                    .check(matches(withText(containsString("4."))));
            onView(withId(R.id.pv_video))
                    .check(matches(not(isDisplayed())));
        }

    }



}
