package xyz.alviksar.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xyz.alviksar.bakingapp.model.Recipe;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnStepClickListener}
 * interface.
 */
public class StepListFragment extends Fragment {

    private Recipe mRecipe;
    private OnStepClickListener mListener;
    private StepListAdapter mAdapter;

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

        RecyclerView recyclerView = rootView.findViewById(R.id.rv_step_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        if (mAdapter == null)
            mAdapter = new StepListAdapter(getContext(), mRecipe.getSteps(), mListener);
        else
            mAdapter.swapData(mRecipe.getSteps());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
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
