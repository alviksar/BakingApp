package xyz.alviksar.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import xyz.alviksar.bakingapp.model.Recipe;
import xyz.alviksar.bakingapp.model.Step;


public class StepDetailActivity extends AppCompatActivity {
    private Recipe mRecipe;
    private int mStepNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        if (savedInstanceState == null || !savedInstanceState.containsKey(Recipe.PARCEBLE_NAME)) {
            mRecipe = getIntent().getParcelableExtra(Recipe.PARCEBLE_NAME);
            mStepNum = getIntent().getIntExtra(Step.PARCEBLE_NAME, 0);
        } else {
            mRecipe = savedInstanceState.getParcelable(Recipe.PARCEBLE_NAME);
            mStepNum = savedInstanceState.getInt(Step.PARCEBLE_NAME);

        }

        if (mRecipe != null) {
            setTitle(mRecipe.getName());
            moveToStep(mStepNum);
//
//            Step step = mRecipe.getSteps().get(mStepNum);
//            String description = step.getDescription();
//            String videoUrl = step.getVideoURL();
//            StepDetailFragment stepDetailFragment = StepDetailFragment.newInstance(videoUrl, description);
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            Fragment fragment = fragmentManager.findFragmentById(R.id.step_detail_fragment);
//            if (fragment == null) {
//                fragmentManager.beginTransaction()
//                        .add(R.id.step_detail_fragment, stepDetailFragment)
//                        .commit();
//            } else {
//                fragmentManager.beginTransaction()
//                        .replace(R.id.step_detail_fragment, stepDetailFragment)
//                        .commit();
//            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Recipe.PARCEBLE_NAME, mRecipe);
        outState.putInt(Step.PARCEBLE_NAME, mStepNum);
        super.onSaveInstanceState(outState);
    }

    public void btnPrevClick(View view) {
        if (mStepNum > 0) {
            mStepNum--;
            moveToStep(mStepNum);
        }
    }

    public void btnNextClick(View view) {
        if (mStepNum < mRecipe.getSteps().size()-1) {
            mStepNum++;
            moveToStep(mStepNum);
        }
    }

    private void moveToStep(int stepNum) {
        Step step = mRecipe.getSteps().get(stepNum);
        String description = step.getDescription();
        String videoUrl = step.getVideoURL();
        StepDetailFragment stepDetailFragment = StepDetailFragment.newInstance(videoUrl, description);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.step_detail_fragment);
        if (fragment == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.step_detail_fragment, stepDetailFragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.step_detail_fragment, stepDetailFragment)
                    .commit();
        }
    }
}
