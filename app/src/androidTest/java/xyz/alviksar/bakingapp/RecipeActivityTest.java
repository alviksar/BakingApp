package xyz.alviksar.bakingapp;

import android.support.annotation.NonNull;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.v4.util.Preconditions.checkNotNull;

@RunWith(AndroidJUnit4.class)

public class RecipeActivityTest {

    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class, true, true);
/*
    @Test
    public void scrollToItemBelowFold_checkItsText() {
        // First, scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_BELOW_THE_FOLD,
                        click()));

        // Match the text in an item below the fold and check that it's displayed.
        String itemElementText = mActivityRule.getActivity().getResources()
                .getString(R.string.item_element_text)
                + String.valueOf(ITEM_BELOW_THE_FOLD);
        onView(withText(itemElementText)).check(matches(isDisplayed()));
    }
    @Test
public void itemInMiddleOfList_hasSpecialText() {
    // First, scroll to the view holder using the isInTheMiddle() matcher.
    onView(ViewMatchers.withId(R.id.recyclerView))
            .perform(RecyclerViewActions.scrollToHolder(isInTheMiddle()));

    // Check that the item has the special text.
    String middleElementText =
            mActivityRule.getActivity().getResources()
            .getString(R.string.middle);
    onView(withText(middleElementText)).check(matches(isDisplayed()));
}
*/

    //    @Rule
//    public IntentsTestRule<MainActivity> mIntentsRule = new IntentsTestRule<>(
//            MainActivity.class);
//
//    @Rule
//    public TaskExecutorWithIdlingResourceRule executorRule =
//            new TaskExecutorWithIdlingResourceRule();
// Registers any resource that needs to be synchronized with Espresso before the test is run.
    @Before
    public void registerIdlingResource() {
        mIdlingResource = activityRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void startActivity_ShowRecipeList() {

        // Recipe list displayed
        onView(withId(R.id.rv_recipe_list))
                .perform(scrollToPosition(3))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickOnRecipe_ShowIngredients() {

        // Scroll to the position 1 and click on it.
        onView(ViewMatchers.withId(R.id.rv_recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1,
                        click()));

        // Check if next activity displayed
        onView(ViewMatchers.withId(R.id.tv_ingredients)).check(matches(isDisplayed()));
    }

    // Remember to unregister resources when not needed to avoid malfunction.
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().register(mIdlingResource);
        }
    }


/*
https://stackoverflow.com/questions/31394569/how-to-assert-inside-a-recyclerview-in-espresso
https://spin.atomicobject.com/2016/04/15/espresso-testing-recyclerviews/
https://github.com/dannyroa/espresso-samples/blob/master/RecyclerView/app/src/androidTest/java/com/dannyroa/espresso_samples/recyclerview/RecyclerViewMatcher.java
 */
//        onView(withId(R.id.my_recycler_view))
//                .perform(scrollToPosition(3))
//                .check(matches(atPosition(3, hasDescendant(withText("Test Text")))));

//        onView(withId(R.id.my_recycler_view))
//                .perform(RecyclerViewActions.actionOnItem(
//                        hasDescendant(withText("Text of item you want to scroll to")),
//                        click()));

/*

onView(withId(R.id.recyclerView))
      .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

onView(withId(R.id.recyclerView))
      .perform(RecyclerViewActions.actionOnItem(
                hasDescendant(withText(BOOK_TITLE)), click()));
 */
//    @Test
//    public void launchSteps() {
//        onView(listMatcher().atPosition(0)).perform(click());
//        intended(hasExtra(EXTRA_RECIPE_ID, 0));
//    }

    //    @NonNull
//    private RecyclerViewMatcher listMatcher() {
//        return new RecyclerViewMatcher(R.id.recipes_rv);
//    }
    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}