package xyz.alviksar.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xyz.alviksar.bakingapp.model.Recipe;

/**
 * A fragment representing a list of steps.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnStepClickListener}
 * interface.
 */
public class StepListFragment extends Fragment {

    private Recipe mRecipe;
    private RecyclerView mRecyclerView;
    private StepListAdapter mAdapter;
    private OnStepClickListener mListener;
    private Parcelable mSavedRecyclerLayoutState = null;
    private int mSelectedStep = -1;
    private final static String BUNDLE_RECYCLER_LAYOUT = "StepListFragment.mRecyclerView.layout";
    private final static String BUNDLE_SELECTED_STEP = "StepListFragment.mRecyclerView.selectedStep";

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
        outState.putInt(BUNDLE_SELECTED_STEP, mAdapter.getSelectedStep());
        super.onSaveInstanceState(outState);
    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepListFragment() {
    }

    public static StepListFragment newInstance(Recipe recipe) {
        StepListFragment fragment = new StepListFragment();
        Bundle args = new Bundle();
        args.putParcelable(Recipe.PARCEBLE_NAME, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(BUNDLE_RECYCLER_LAYOUT))
                mSavedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(mSavedRecyclerLayoutState);
            mSelectedStep = savedInstanceState.getInt(BUNDLE_SELECTED_STEP, -1);

        }
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(Recipe.PARCEBLE_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_list, container, false);

        TextView mIngredientsTextView = rootView.findViewById(R.id.tv_ingredients);
        mIngredientsTextView.setText(mRecipe.getIngredientsString());

        mRecyclerView = rootView.findViewById(R.id.rv_step_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        if (mAdapter == null)
            mAdapter = new StepListAdapter(getContext(), mRecipe.getSteps(), mListener);
        else
            mAdapter.swapData(mRecipe.getSteps());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        // Restore state
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(BUNDLE_RECYCLER_LAYOUT)) {
                mSavedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
                mRecyclerView.getLayoutManager().onRestoreInstanceState(mSavedRecyclerLayoutState);
            }
            mSelectedStep = savedInstanceState.getInt(BUNDLE_SELECTED_STEP, -1);
            mAdapter.setSelectedStep(mSelectedStep);
            mAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(mSelectedStep);

        }

        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepClickListener) {
            mListener = (OnStepClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
//        mAdapter.swapData(null);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnStepClickListener {
        void onStepClick(int step);
    }
}
