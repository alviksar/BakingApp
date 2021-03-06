package xyz.alviksar.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import xyz.alviksar.bakingapp.StepListFragment.OnStepClickListener;
import xyz.alviksar.bakingapp.model.Step;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Step} and makes a call to the
 * specified {@link OnStepClickListener}.
 */
public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.ViewHolder> {

    private final Context mContext;
    private List<Step> mSteps;
    private int selectedStep;
    private final OnStepClickListener mListener;

    StepListAdapter(Context context, List<Step> items, OnStepClickListener listener) {
        mContext = context;
        mSteps = items;
        setSelectedStep(-1);
        mListener = listener;
    }

    public void swapData(List<Step> data) {
        mSteps = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_step_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Step step = mSteps.get(position);
        String strStep = "";
        if (step.getId() > 0)
            strStep = String.format(Locale.getDefault(),
                    "%d. %s", step.getId(), mSteps.get(position).getShortDescription());
        else
            strStep = mSteps.get(position).getShortDescription();

        holder.mContentView.setText(strStep);

        String thumbnailURL = step.getThumbnailURL();
        if (!TextUtils.isEmpty(thumbnailURL)) {
            Picasso.with(mContext)
                    .load(thumbnailURL)
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.error_image)
                    .into(holder.mThumbnailImage);
        }

        if (position == selectedStep) {
            holder.mContentView.setTextAppearance(mContext, R.style.WhiteBoldText);
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));

        } else {
            holder.mContentView.setTextAppearance(mContext, R.style.GrayNormalText);
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public int getItemCount() {
        if (mSteps != null)
            return mSteps.size();
        else
            return 0;
    }

    public int getSelectedStep() {
        return selectedStep;
    }

    public void setSelectedStep(int selectedStep) {
        this.selectedStep = selectedStep;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mThumbnailImage;
        private final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mThumbnailImage = view.findViewById(R.id.iv_thumbnail);
            mContentView = view.findViewById(R.id.tv_short_step_description);
            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            selectedStep = getAdapterPosition();
            if (null != mListener) {
                mListener.onStepClick(selectedStep);
            }
            notifyDataSetChanged();
        }
    }
}

