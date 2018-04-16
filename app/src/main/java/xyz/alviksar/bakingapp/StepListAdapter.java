package xyz.alviksar.bakingapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xyz.alviksar.bakingapp.StepListFragment.OnListFragmentInteractionListener;
import xyz.alviksar.bakingapp.model.Step;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Step} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.ViewHolder> {

    private List<Step> mSteps;
    private final OnListFragmentInteractionListener mListener;

    public StepListAdapter(List<Step> items, OnListFragmentInteractionListener listener) {
        mSteps = items;
        mListener = listener;
    }

    public void swapData(List<Step> data) {
        mSteps = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_step_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Step step = mSteps.get(position);
        if (step.getId() > 0)
            holder.mIdView.setText(String.format("%d.", step.getId()));
        else
            holder.mIdView.setText("");
        holder.mContentView.setText(mSteps.get(position).getShortDescription());

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (mSteps != null)
            return mSteps.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;

        public ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.tv_description);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
