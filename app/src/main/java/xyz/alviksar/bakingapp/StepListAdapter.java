package xyz.alviksar.bakingapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xyz.alviksar.bakingapp.StepListFragment.OnStepClickListener;
import xyz.alviksar.bakingapp.model.Step;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Step} and makes a call to the
 * specified {@link OnStepClickListener}.
 */
public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.ViewHolder> {

    private Step mItem;
    private List<Step> mSteps;
    private final OnStepClickListener mListener;

    public StepListAdapter(List<Step> items, OnStepClickListener listener) {
        mSteps = items;
        mListener = listener;
    }

    public void swapData(List<Step> data) {
        mSteps = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_step_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        mItem = mSteps.get(position);
        if (mItem.getId() > 0)
            holder.mIdView.setText(String.format("%d.", mItem.getId()));
        else
            holder.mIdView.setText("");
        holder.mContentView.setText(mSteps.get(position).getShortDescription());

  //      holder.itemView.setOnClickListener(this);
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    int position = getAdapterPosition();
//                    if (mSteps != null) {
//                        Step step = mSteps.get(position);
//                        if (step != null) {
//                            mListener.onStepClick(step);
//                        }
//                    }
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mIdView;
        public final TextView mContentView;

        public ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.tv_description);
            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            if (null != mListener) {
                int position = getAdapterPosition();
                if (mSteps != null) {
                    Step step = mSteps.get(position);
                    if (step != null) {
                        mListener.onStepClick(step);
                    }
                }
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
            }
        }
    }
}

