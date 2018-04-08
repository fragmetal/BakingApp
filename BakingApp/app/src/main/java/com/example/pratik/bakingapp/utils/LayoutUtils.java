package com.example.pratik.bakingapp.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Pratik
 */

public class LayoutUtils {

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }
}
