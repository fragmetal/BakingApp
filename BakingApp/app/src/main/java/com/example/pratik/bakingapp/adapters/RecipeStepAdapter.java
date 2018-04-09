package com.example.pratik.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.pratik.bakingapp.interfaces.OnItemClickListener;
import com.example.pratik.bakingapp.R;
import com.example.pratik.bakingapp.data.RecipeStep;
import com.squareup.picasso.Picasso;

/**
 * Created by Pratik
 */

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepViewHolder> {

    private ArrayList<RecipeStep> mRecipeStepsList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public RecipeStepAdapter(ArrayList<RecipeStep> recipeSteps, Context context, OnItemClickListener clickListener) {
        this.mRecipeStepsList = recipeSteps;
        this.context = context;
        this.onItemClickListener = clickListener;
    }

    @Override
    public RecipeStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToRoot = false;

        View ingredientsView = layoutInflater.inflate(R.layout.recipe_step_item, parent, shouldAttachToRoot);

        RecipeStepViewHolder recipeStepViewHolder = new RecipeStepViewHolder(ingredientsView);

        return recipeStepViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeStepViewHolder holder, int position) {

        String thumbnailUrl = mRecipeStepsList.get(position).getThumbnailUrl();
        if (thumbnailUrl.isEmpty()) {
            holder.recipeStepIVThumb.setImageResource(R.drawable.chef_placeholder_square);
        } else {
            Picasso.get()
                    .load(thumbnailUrl)
                    .placeholder(R.drawable.chef_placeholder_square)
                    .into(holder.recipeStepIVThumb);
        }

        String shortStepDescription = mRecipeStepsList.get(position).getShortStepDescription();
        holder.recipeStepTV.setText(shortStepDescription);


    }

    @Override
    public int getItemCount() {
        if (mRecipeStepsList != null) return mRecipeStepsList.size();
        return 0;
    }

    public class RecipeStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView recipeStepTV;
        private ImageView recipeStepIVThumb;

        public RecipeStepViewHolder(View itemView) {
            super(itemView);
            recipeStepTV = (TextView) itemView.findViewById(R.id.tv_recipestep_shortdescription);
            recipeStepIVThumb = (ImageView) itemView.findViewById(R.id.iv_recipe_item_thumbNail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            onItemClickListener.onClick(position);
        }
    }
}
