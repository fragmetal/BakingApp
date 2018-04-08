package com.example.pratik.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Pratik
 */

public class Recipe implements Parcelable {

    private String mRecipeName;
    private ArrayList<Ingredient> mIngredientsList;
    private ArrayList<RecipeStep> mRecipeStepsList;


    public Recipe(String name, ArrayList<Ingredient> ingredients, ArrayList<RecipeStep> recipeSteps) {
        this.mRecipeName = name;
        this.mIngredientsList = ingredients;
        this.mRecipeStepsList = recipeSteps;
    }

    protected Recipe(Parcel in) {
        mRecipeName = in.readString();
        mIngredientsList = in.createTypedArrayList(Ingredient.CREATOR);
        mRecipeStepsList = in.createTypedArrayList(RecipeStep.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getName() {
        return mRecipeName;
    }

    public ArrayList<Ingredient> getIngredients() {
        return mIngredientsList;
    }

    public ArrayList<RecipeStep> getRecipeSteps() {
        return mRecipeStepsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mRecipeName);
        parcel.writeList(mIngredientsList);
        parcel.writeList(mRecipeStepsList);
    }
}
