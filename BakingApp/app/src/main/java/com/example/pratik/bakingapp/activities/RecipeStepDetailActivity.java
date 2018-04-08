package com.example.pratik.bakingapp.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import com.example.pratik.bakingapp.R;
import com.example.pratik.bakingapp.fragments.RecipeStepInstructionFragment;
import com.example.pratik.bakingapp.fragments.RecipeStepMediaFragment;
import com.example.pratik.bakingapp.data.RecipeStep;

public class RecipeStepDetailActivity extends AppCompatActivity {

    private ArrayList<RecipeStep> mRecipeStepsList;
    private int currentRecipeStepsIndex;

    private FloatingActionButton mBtnBackward;
    private FloatingActionButton mBtnForward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        mBtnBackward = (FloatingActionButton) findViewById(R.id.fab_backwards);
        mBtnForward = (FloatingActionButton) findViewById(R.id.fab_forward);

        Intent intent = getIntent();

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {


            mBtnBackward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRecipeStepsList != null) {
                        if (currentRecipeStepsIndex > 0) {
                            currentRecipeStepsIndex--;

                        } else {
                            currentRecipeStepsIndex = mRecipeStepsList.size() - 1;
                        }
                        // Toast.makeText(getApplicationContext(),"Backwards currentRecipeStepsIndex :" + currentRecipeStepsIndex,Toast.LENGTH_SHORT).show();

                        RecipeStepInstructionFragment recipeStepInstructionFragment = new RecipeStepInstructionFragment();
                        recipeStepInstructionFragment.setRecipeSteps(mRecipeStepsList);
                        recipeStepInstructionFragment.setCurrentRecipeStepsIndex(currentRecipeStepsIndex);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.recipe_step_instruction_container, recipeStepInstructionFragment)
                                .commit();


                        RecipeStepMediaFragment recipeStepMediaFragment = new RecipeStepMediaFragment();
                        recipeStepMediaFragment.setRecipeSteps(mRecipeStepsList);
                        recipeStepMediaFragment.setCurrentRecipeStepsIndex(currentRecipeStepsIndex);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_step_media_container, recipeStepMediaFragment)
                                .commit();

                    }
                }
            });


            mBtnForward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRecipeStepsList != null) {

                        if (currentRecipeStepsIndex < mRecipeStepsList.size() - 1) {
                            currentRecipeStepsIndex++;
                        } else {
                            currentRecipeStepsIndex = 0;
                        }

                        RecipeStepInstructionFragment recipeStepInstructionFragment = new RecipeStepInstructionFragment();
                        recipeStepInstructionFragment.setRecipeSteps(mRecipeStepsList);
                        recipeStepInstructionFragment.setCurrentRecipeStepsIndex(currentRecipeStepsIndex);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.recipe_step_instruction_container, recipeStepInstructionFragment)
                                .commit();


                        RecipeStepMediaFragment recipeStepMediaFragment = new RecipeStepMediaFragment();
                        recipeStepMediaFragment.setRecipeSteps(mRecipeStepsList);
                        recipeStepMediaFragment.setCurrentRecipeStepsIndex(currentRecipeStepsIndex);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_step_media_container, recipeStepMediaFragment)
                                .commit();

                    }

                }
            });
        }

        if (intent.hasExtra(getString(R.string.key_intent_recipeSteps)) &&
                (intent.hasExtra(getString(R.string.key_intent_recipeStepsIndex)))) {
            mRecipeStepsList = intent.getParcelableArrayListExtra(getString(R.string.key_intent_recipeSteps));
            currentRecipeStepsIndex = intent.getIntExtra(getString(R.string.key_intent_recipeStepsIndex), 0);
        }

        if (savedInstanceState == null) {

            RecipeStepInstructionFragment recipeStepInstructionFragment = new RecipeStepInstructionFragment();
            recipeStepInstructionFragment.setRecipeSteps(mRecipeStepsList);
            recipeStepInstructionFragment.setCurrentRecipeStepsIndex(currentRecipeStepsIndex);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_step_instruction_container, recipeStepInstructionFragment)
                    .commit();


            RecipeStepMediaFragment recipeStepMediaFragment = new RecipeStepMediaFragment();
            recipeStepMediaFragment.setRecipeSteps(mRecipeStepsList);
            recipeStepMediaFragment.setCurrentRecipeStepsIndex(currentRecipeStepsIndex);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_step_media_container, recipeStepMediaFragment)
                    .commit();
        }
    }

}
