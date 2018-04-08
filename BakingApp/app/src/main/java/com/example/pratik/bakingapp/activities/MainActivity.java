package com.example.pratik.bakingapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.example.pratik.bakingapp.R;
import com.example.pratik.bakingapp.adapters.RecipeAdapter;
import com.example.pratik.bakingapp.data.Ingredient;
import com.example.pratik.bakingapp.data.Recipe;
import com.example.pratik.bakingapp.data.RecipeStep;
import com.example.pratik.bakingapp.databinding.ActivityMainBinding;
import com.example.pratik.bakingapp.interfaces.OnItemClickListener;
import com.example.pratik.bakingapp.utils.LayoutUtils;
import com.example.pratik.bakingapp.utils.NetworkUtils;
import com.example.pratik.bakingapp.utils.StorageUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>,
        OnItemClickListener {

    private static final String RECIPES_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final int RECIPES_GET_LOADER_ID = 23;

    private ArrayList<Recipe> mRecipesList = new ArrayList<>();
    private ArrayList<Ingredient> mIngredientsList = new ArrayList<>();
    private ArrayList<RecipeStep> mRecipeStepsList = new ArrayList<>();

    private ActivityMainBinding mMainActivityBinding;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainActivityBinding.rvRecipes.setHasFixedSize(true);

        isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet) {
            GridLayoutManager layoutManager = new GridLayoutManager(this, LayoutUtils.calculateNoOfColumns(this));
            mMainActivityBinding.rvRecipes.setLayoutManager(layoutManager);

        } else {

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mMainActivityBinding.rvRecipes.setLayoutManager(layoutManager);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mMainActivityBinding.rvRecipes.getContext(),
                    layoutManager.getOrientation());
            mMainActivityBinding.rvRecipes.addItemDecoration(dividerItemDecoration);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (NetworkUtils.checkInternetConnection(this)) {
            getRecipesData();
        } else {
            showErrorMessage();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    public void showErrorMessage() {

        mMainActivityBinding.rvRecipes.setVisibility(View.INVISIBLE);
        mMainActivityBinding.tvErrorNoInternetConnection.setVisibility(View.VISIBLE);
    }

    public void showRecipesView() {
        mMainActivityBinding.rvRecipes.setVisibility(View.VISIBLE);
        mMainActivityBinding.tvErrorNoInternetConnection.setVisibility(View.INVISIBLE);

    }

    Loader<ArrayList<Recipe>> getRecipesLoader = getSupportLoaderManager().getLoader(RECIPES_GET_LOADER_ID);

    private void getRecipesData() {


        if (getRecipesLoader == null) {
            getSupportLoaderManager().initLoader(RECIPES_GET_LOADER_ID, null, this);
        } else {
            getSupportLoaderManager().restartLoader(RECIPES_GET_LOADER_ID, null, this);
        }

    }

    public ArrayList<RecipeStep> getRecipeSteps(JSONObject parentObject) {

        ArrayList<RecipeStep> localRecipeStepsList = new ArrayList<>();
        JSONArray recipeStepsArray;

        try {
            recipeStepsArray = parentObject.getJSONArray("steps");

            for (int j = 0; j < recipeStepsArray.length(); j++) {
                JSONObject childObject = recipeStepsArray.getJSONObject(j);

                String shortDescription = childObject.optString("shortDescription", "");
                String description = childObject.optString("description", "");
                String videoUrl = childObject.optString("videoURL", "");
                String thumbnailURL = childObject.optString("thumbnailURL", "");

                localRecipeStepsList.add(new RecipeStep(shortDescription, description, videoUrl, thumbnailURL));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return localRecipeStepsList;
    }

    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<Recipe>>(this) {

            private ArrayList<Recipe> recipesLocalList = new ArrayList<>();
            private ArrayList<Ingredient> ingredients = new ArrayList<>();

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                mMainActivityBinding.progressBar.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public ArrayList<Recipe> loadInBackground() {

                URL recipesUrl = null;
                try {
                    recipesUrl = new URL(RECIPES_URL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                String returnedString = NetworkUtils.getResponseFromHttp(recipesUrl);

                if (!returnedString.isEmpty()) {
                    try {
                        JSONArray jsonArray = new JSONArray(returnedString);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject childObject = jsonArray.getJSONObject(i);
                            String recipeName = childObject.getString("name");

                            ingredients = getIngredients(childObject);
                            mRecipeStepsList = getRecipeSteps(childObject);

                            recipesLocalList.add(new Recipe(recipeName, ingredients, mRecipeStepsList));


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                return recipesLocalList;
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> data) {
        mMainActivityBinding.progressBar.setVisibility(View.INVISIBLE);
        if (data != null) {
            showRecipesView();
            mRecipesList = data;
            RecipeAdapter recipeAdapter = new RecipeAdapter(data, this, this);
            mMainActivityBinding.rvRecipes.setAdapter(recipeAdapter);
        } else
            showErrorMessage();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Recipe>> loader) {
        //  Toast.makeText(getApplicationContext(),"loadreset called", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mRecipesList != null && !mRecipesList.isEmpty())
            mRecipesList.clear();


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private SharedPreferences preferences;

    @Override
    public void onClick(int position) {

        Intent intent = new Intent(this, RecipeDetailActivity.class);
        mIngredientsList = mRecipesList.get(position).getIngredients();

        intent.putParcelableArrayListExtra(getString(R.string.key_intent_ingredients), mIngredientsList);
        intent.putParcelableArrayListExtra(getString(R.string.key_intent_recipeSteps), mRecipesList.get(position).getRecipeSteps());
        intent.putExtra(getString(R.string.key_intent_recipeName), mRecipesList.get(position).getName());

        StorageUtils storageUtils = new StorageUtils(getApplicationContext());
        storageUtils.storeIngredients(mIngredientsList, this);
        startActivity(intent);

    }


    public ArrayList<Ingredient> getIngredients(JSONObject parentObject) {

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        JSONArray ingredientsArray;

        try {
            ingredientsArray = parentObject.getJSONArray("ingredients");

            for (int j = 0; j < ingredientsArray.length(); j++) {
                JSONObject childObject = ingredientsArray.getJSONObject(j);

                int quantity = childObject.getInt("quantity");
                String measure = childObject.getString("measure");
                String ingredient = childObject.getString("ingredient");

                ingredients.add(new Ingredient(quantity, measure, ingredient));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ingredients;
    }

}
