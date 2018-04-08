package com.example.pratik.bakingapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

import com.example.pratik.bakingapp.R;
import com.example.pratik.bakingapp.fragments.RecipeDetailFragment;
import com.example.pratik.bakingapp.fragments.RecipeStepInstructionFragment;
import com.example.pratik.bakingapp.fragments.RecipeStepMediaFragment;
import com.example.pratik.bakingapp.data.RecipeStep;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnFragmentInteractionListener {

    private ArrayList<RecipeStep> mRecipeStepsList = new ArrayList<>();
    private String mRecipeName;

    private boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.key_intent_recipeSteps)) && intent.hasExtra(getString(R.string.key_intent_recipeName))) {
            mRecipeStepsList = intent.getParcelableArrayListExtra(getString(R.string.key_intent_recipeSteps));
            mRecipeName = intent.getStringExtra(getString(R.string.key_intent_recipeName));

            setTitle(mRecipeName);
        }

        if (findViewById(R.id.rl_recipe_step_instruction) != null) {
            isTwoPane = true;

            if (savedInstanceState == null) {

                RecipeStepInstructionFragment recipeStepInstructionFragment = new RecipeStepInstructionFragment();
                recipeStepInstructionFragment.setRecipeSteps(mRecipeStepsList);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_step_instruction_container, recipeStepInstructionFragment)
                        .commit();


                RecipeStepMediaFragment recipeStepMediaFragment = new RecipeStepMediaFragment();
                recipeStepMediaFragment.setRecipeSteps(mRecipeStepsList);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_step_media_container, recipeStepMediaFragment)
                        .commit();
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(int position) {

        if (isTwoPane) {
            RecipeStepInstructionFragment newRecipeStepInstructionFragment = new RecipeStepInstructionFragment();
            newRecipeStepInstructionFragment.setRecipeSteps(mRecipeStepsList);
            newRecipeStepInstructionFragment.setCurrentRecipeStepsIndex(position);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_step_instruction_container, newRecipeStepInstructionFragment)
                    .commit();


            RecipeStepMediaFragment newRecipeStepMediaFragment = new RecipeStepMediaFragment();
            newRecipeStepMediaFragment.setRecipeSteps(mRecipeStepsList);
            newRecipeStepMediaFragment.setCurrentRecipeStepsIndex(position);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_step_media_container, newRecipeStepMediaFragment)
                    .commit();

        } else {
            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            intent.putExtra(getString(R.string.key_intent_recipeSteps), mRecipeStepsList);
            intent.putExtra(getString(R.string.key_intent_recipeStepsIndex), position);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
