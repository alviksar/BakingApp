package xyz.alviksar.bakingapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;

import xyz.alviksar.bakingapp.model.Recipe;
import xyz.alviksar.bakingapp.model.Step;


public class StepDetailActivity extends AppCompatActivity {
    private Recipe mRecipe;
    private int mStepNum;

    private ImageButton mPrevButton;
    private ImageButton mNextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        mPrevButton = findViewById(R.id.btn_prev_step);
        mNextButton = findViewById(R.id.btn_next_step);
        if (savedInstanceState == null || !savedInstanceState.containsKey(Recipe.PARCEBLE_NAME)) {
            mRecipe = getIntent().getParcelableExtra(Recipe.PARCEBLE_NAME);
            mStepNum = getIntent().getIntExtra(Step.PARCEBLE_NAME, 0);
        } else {
            mRecipe = savedInstanceState.getParcelable(Recipe.PARCEBLE_NAME);
            mStepNum = savedInstanceState.getInt(Step.PARCEBLE_NAME);
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
        }

        if (mRecipe != null) {
            setTitle(mRecipe.getName());
            moveToStep(mStepNum);
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
            mStepNum = mStepNum - 1;
            moveToStep(mStepNum);
        }
    }

    public void btnNextClick(View view) {
        if (mStepNum < mRecipe.getSteps().size() - 1) {
            mStepNum = mStepNum + 1;
            moveToStep(mStepNum);
        }
    }

    /**
     * Replaces fragment with new one after taping button.
     *
     * @param stepNum Current number of steps
     */
    private void moveToStep(int stepNum) {
        Step step = mRecipe.getSteps().get(stepNum);
        String description = step.getDescription();
        String videoUrl = step.getVideoURL();
        StepDetailFragment stepDetailFragment = StepDetailFragment.newInstance(videoUrl, description);

        FragmentManager fragmentManager = getSupportFragmentManager();
        StepDetailFragment fragment = (StepDetailFragment) fragmentManager.findFragmentById(R.id.step_detail_fragment);

        if (fragment == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.step_detail_fragment, stepDetailFragment)
                    .commit();
        } else if (!TextUtils.equals(fragment.getmDescription(), description)) {
            fragmentManager.beginTransaction()
                    .replace(R.id.step_detail_fragment, stepDetailFragment)
                    .commit();
        }

        if (mNextButton != null && mPrevButton != null) {
            if (stepNum == 0) mPrevButton.setVisibility(View.INVISIBLE);
            else mPrevButton.setVisibility(View.VISIBLE);
            if (stepNum == mRecipe.getSteps().size() - 1) mNextButton.setVisibility(View.INVISIBLE);
            else mNextButton.setVisibility(View.VISIBLE);
        }
    }
}
