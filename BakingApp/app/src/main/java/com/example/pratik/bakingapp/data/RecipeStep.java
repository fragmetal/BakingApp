package com.example.pratik.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pratik
 */

public class RecipeStep implements Parcelable {

    private String mShortStepDescription;
    private String mStepDescription;
    private String mVideoUrl;
    private String mThumbnailUrl;

    public RecipeStep(String shortStepDescription, String stepDescription, String videoUrl, String thumbnailUrl) {
        this.mShortStepDescription = shortStepDescription;
        this.mStepDescription = stepDescription;
        this.mVideoUrl = videoUrl;
        this.mThumbnailUrl = thumbnailUrl;
    }

    protected RecipeStep(Parcel in) {
        mShortStepDescription = in.readString();
        mStepDescription = in.readString();
        mVideoUrl = in.readString();
        mThumbnailUrl = in.readString();
    }

    public static final Creator<RecipeStep> CREATOR = new Creator<RecipeStep>() {
        @Override
        public RecipeStep createFromParcel(Parcel in) {
            return new RecipeStep(in);
        }

        @Override
        public RecipeStep[] newArray(int size) {
            return new RecipeStep[size];
        }
    };

    public String getShortStepDescription() {
        return mShortStepDescription;
    }

    public String getStepDescription() {
        return mStepDescription;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mShortStepDescription);
        parcel.writeString(mStepDescription);
        parcel.writeString(mVideoUrl);
        parcel.writeString(mThumbnailUrl);
    }
}
