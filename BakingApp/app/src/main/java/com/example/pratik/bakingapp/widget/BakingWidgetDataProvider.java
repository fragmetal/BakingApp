package com.example.pratik.bakingapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import com.example.pratik.bakingapp.R;
import com.example.pratik.bakingapp.data.Ingredient;
import com.example.pratik.bakingapp.utils.StorageUtils;
/**
 * Created by Pratik
 */

public class BakingWidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context context;


    private ArrayList<Ingredient> ingredients = new ArrayList<>();


    public BakingWidgetDataProvider(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

        StorageUtils storageUtils = new StorageUtils(context);
        ingredients = storageUtils.loadIngredients();
    }


    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }


    @Override
    public int getCount() {
        if (ingredients != null) return ingredients.size();
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(context.getPackageName(),
                R.layout.ingredient_item);

        int quantity = ingredients.get(position).getQuantity();
        String measure = ingredients.get(position).getMeasure();
        String ingredient = ingredients.get(position).getIngredient();

        view.setTextViewText(R.id.tv_quantity, String.valueOf(quantity));
        view.setTextViewText(R.id.tv_quantity_measure, measure);
        view.setTextViewText(R.id.tv_ingredient, ingredient);

        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
