package xyz.alviksar.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import xyz.alviksar.bakingapp.model.Recipe;
import xyz.alviksar.bakingapp.model.Step;


/**
 * Show ingredients and steps for one recipe.
 */

public class StepListActivity extends AppCompatActivity implements StepListFragment.OnStepClickListener {

    private Recipe mRecipe;
    private boolean mTwoPane;
    private int mStepNumber;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Recipe.PARCEBLE_NAME, mRecipe);
        outState.putInt(Step.PARCEBLE_NAME, mStepNumber);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        if (savedInstanceState == null || !savedInstanceState.containsKey(Recipe.PARCEBLE_NAME)) {
            mRecipe = getIntent().getParcelableExtra(Recipe.PARCEBLE_NAME);
        } else {
            mRecipe = savedInstanceState.getParcelable(Recipe.PARCEBLE_NAME);
            mStepNumber = savedInstanceState.getInt(Step.PARCEBLE_NAME, 0);
        }

        // Check if two pane mode
        mTwoPane = (findViewById(R.id.step_detail_fragment) != null);

        if (mRecipe != null) {
            setTitle(mRecipe.getName());
            FragmentManager fragmentManager = getSupportFragmentManager();

            StepListFragment newsStepListFragment = StepListFragment.newInstance(mRecipe);
            Fragment oldStepListFragment
                    = fragmentManager.findFragmentById(R.id.step_list_fragment);
            if (oldStepListFragment == null) {
                fragmentManager.beginTransaction()
                        .add(R.id.step_list_fragment, newsStepListFragment)
                        .commit();

                if (mTwoPane) {
                    Step step = mRecipe.getSteps().get(mStepNumber);
                    String description = step.getDescription();
                    String videoUrl = step.getVideoURL();
                    StepDetailFragment newStepDetailFragment
                            = StepDetailFragment.newInstance(videoUrl, description);
                    StepDetailFragment oldStepDetailFragment
                            = (StepDetailFragment) fragmentManager
                            .findFragmentById(R.id.step_detail_fragment);

                    if (oldStepDetailFragment == null) {
                        fragmentManager.beginTransaction()
                                .add(R.id.step_detail_fragment, newStepDetailFragment)
                                .commit();
                    } else {
                        fragmentManager.beginTransaction()
                                .replace(R.id.step_detail_fragment, newStepDetailFragment)
                                .commit();
                    }
                }
            }
//            else {
//                fragmentManager.beginTransaction()
//                        .replace(R.id.step_list_fragment, newsStepListFragment)
//                        .commit();
//            }
        }
    }

    @Override
    public void onStepClick(int stepNumber) {
        mStepNumber = stepNumber;
        if (mTwoPane) {
            Step step = mRecipe.getSteps().get(mStepNumber);
            StepDetailFragment newStepDetailFragment = StepDetailFragment.newInstance(step.getVideoURL(), step.getDescription());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_fragment, newStepDetailFragment)
                    .commit();
        } else {
            Intent stepDetailIntent = new Intent(StepListActivity.this, StepDetailActivity.class);
            stepDetailIntent.putExtra(Step.PARCEBLE_NAME, stepNumber);
            stepDetailIntent.putExtra(Recipe.PARCEBLE_NAME, mRecipe);
            startActivity(stepDetailIntent);
        }
    }
}
