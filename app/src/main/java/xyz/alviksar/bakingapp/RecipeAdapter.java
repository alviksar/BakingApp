package xyz.alviksar.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xyz.alviksar.bakingapp.model.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context mContext;
    private List<Recipe> mDataset;


    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mNameTextView;

        public RecipeViewHolder(View v) {
            super(v);
            mNameTextView = itemView.findViewById(R.id.tv_recipe_name);
        }
    }

    public RecipeAdapter(Context context, List<Recipe> data) {
        mContext = context;
        mDataset = data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_recipe, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        return new RecipeViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mNameTextView.setText(mDataset.get(position).getName());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}


