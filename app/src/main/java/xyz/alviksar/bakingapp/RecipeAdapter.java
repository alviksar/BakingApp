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

import xyz.alviksar.bakingapp.model.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context mContext;
    private List<Recipe> mDataset;

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView mNameTextView;
        private ImageView mPhoto;

        RecipeViewHolder(View v) {
            super(v);
            mNameTextView = itemView.findViewById(R.id.tv_recipe_name);
            mPhoto = itemView.findViewById(R.id.iv_recipe);
        }
    }

    public RecipeAdapter(Context context, List<Recipe> data) {
        mContext = context;
        mDataset = data;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
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
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Recipe recipe = mDataset.get(position);
        holder.mNameTextView.setText(recipe.getName());
        String img = recipe.getImage();
        if (TextUtils.isEmpty(img)) {
            holder.mPhoto.setImageResource(R.drawable.no_image);
        } else {
            Picasso.with(mContext)
                    .load(img)
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.error_image)
                    .into(holder.mPhoto);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}


