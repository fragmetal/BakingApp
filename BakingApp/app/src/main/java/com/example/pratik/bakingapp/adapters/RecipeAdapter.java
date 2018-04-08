package com.example.pratik.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.pratik.bakingapp.interfaces.OnItemClickListener;
import com.example.pratik.bakingapp.R;
import com.example.pratik.bakingapp.data.Recipe;

/**
 * Created by Pratik
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private ArrayList<Recipe> mRecipesList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public RecipeAdapter(ArrayList<Recipe> recipes, Context context, OnItemClickListener onItemClickListener) {
        this.mRecipesList = recipes;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToRoot = false;

        View recipeView = inflater.inflate(R.layout.recipes_item, parent, shouldAttachToRoot);

        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(recipeView);
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        String recipeName = mRecipesList.get(position).getName();
        holder.recipeNameTV.setText(recipeName);
    }

    @Override
    public int getItemCount() {
        if (mRecipesList != null && !mRecipesList.isEmpty()) return mRecipesList.size();
        return 0;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView recipeNameTV;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeNameTV = (TextView) itemView.findViewById(R.id.tv_recipe_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            onItemClickListener.onClick(position);
        }
    }
}
