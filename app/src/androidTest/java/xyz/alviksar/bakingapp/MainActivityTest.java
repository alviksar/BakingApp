package xyz.alviksar.bakingapp;

import android.content.Intent;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule
            = new ActivityTestRule<>(MainActivity.class, true, false);


    /*
    Registers resource that needs to be synchronized with Espresso before the test is run.
     */
    @Before
    public void registerIdlingResource() {
        mIdlingResource
                = new CountingIdlingResource(MainActivity.MAIN_ACTIVITY_IDLING_RESOURCE_NAME);
        // To prove that the test fails, omit this call:
        IdlingRegistry.getInstance().register(mIdlingResource);
        Intent intent = new Intent();
        intent.putExtra(MainActivity.MAIN_ACTIVITY_IDLING_RESOURCE_NAME,
                MainActivity.MAIN_ACTIVITY_IDLING_RESOURCE_NAME);

        mMainActivityTestRule.launchActivity(intent);
    }

    /*
    Start the activity and check if a recipe list displayed
//     */
//    @Test
//    public void startActivity_ShowRecipeList() {
//
//        onView(withId(R.id.rv_recipe_list))
////                .perform(scrollToPosition(0))
//                .check(matches(isDisplayed()));
//    }

    /*
     Check that an ingredients view is displayed after click on the recipe list
     */
    @Test
    public void clickOnRecipe_ShowIngredientsTextView() {

//        onView(withId(R.id.rv_recipe_list))
//                .check(matches(isDisplayed()));

        // Scroll to the position 1 and click on it.
        onView(withId(R.id.rv_recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1,
                        click()));

        // Check if next activity displayed
        onView(withId(R.id.tv_ingredients)).check(matches(isDisplayed()));

    }

    /*
     Unregister resources when not needed to avoid malfunction.
     */
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().register(mIdlingResource);
        }
    }


}
