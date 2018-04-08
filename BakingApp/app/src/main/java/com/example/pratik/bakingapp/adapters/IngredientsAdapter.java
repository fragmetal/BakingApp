package com.example.pratik.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.pratik.bakingapp.R;
import com.example.pratik.bakingapp.data.Ingredient;
/**
 * Created by Pratik
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private ArrayList<Ingredient> mIngredientsList;
    private Context context;

    public IngredientsAdapter(ArrayList<Ingredient> ingredients, Context context) {
        this.mIngredientsList = ingredients;
        this.context = context;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToRoot = false;

        View ingredientsView = layoutInflater.inflate(R.layout.ingredient_item, parent, shouldAttachToRoot);

        IngredientsViewHolder ingredientsViewHolder = new IngredientsViewHolder(ingredientsView);

        return ingredientsViewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {

        int quantity = mIngredientsList.get(position).getQuantity();
        String measure = mIngredientsList.get(position).getMeasure();
        String ingredient = mIngredientsList.get(position).getIngredient();

        holder.quantityTV.setText(String.valueOf(quantity));
        holder.measureTV.setText(measure);
        holder.ingredientTV.setText(ingredient);

    }

    @Override
    public int getItemCount() {
        if (mIngredientsList != null) return mIngredientsList.size();
        return 0;
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        private TextView quantityTV;
        private TextView measureTV;
        private TextView ingredientTV;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            quantityTV = (TextView) itemView.findViewById(R.id.tv_quantity);
            measureTV = (TextView) itemView.findViewById(R.id.tv_quantity_measure);
            ingredientTV = (TextView) itemView.findViewById(R.id.tv_ingredient);
        }
    }
}
