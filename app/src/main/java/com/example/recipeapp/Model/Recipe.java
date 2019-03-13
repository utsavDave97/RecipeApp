package com.example.recipeapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author utsav
 * @date 21-Feb-2019
 * This is the Model class. Here Recipe is treated as an object.
 * Also, it has various properties and different methods to set value to those properties or access them.
 * Also, it has 2 constructors which would be helpful in Adapter and SQLiteDatabase
 */
public class Recipe implements Parcelable
{
    //Declaring each property
    private String recipeName;
    private String ratings;
    private String recipeImage;
    private String recipeUrl;
    private int id;

    /**
     * Empty Constructor
     */
    public Recipe()
    {
    }

    /**
     * Construct which would take in recipeName, recipeImage & recipeUrl
     * @param recipeName
     * @param recipeUrl
     * @param recipeImage
     */
    public Recipe(int id,String recipeName, String recipeImage, String recipeUrl) {
        this.id = id;
        this.recipeName = recipeName;
        this.recipeUrl = recipeUrl;
        this.recipeImage = recipeImage;
    }


    /**
     * Construct which would take in recipeName, recipeRatings, recipeImage & recipeURL
     * @param recipeName
     * @param ratings
     * @param recipeImage
     * @param recipeUrl
     */
    public Recipe(String recipeName, String ratings, String recipeImage, String recipeUrl) {
        this.recipeName = recipeName;
        this.ratings = ratings;
        this.recipeImage = recipeImage;
        this.recipeUrl = recipeUrl;
    }


    /**
     * Providing Getters & Setters for each property
     * @return
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeUrl() {
        return recipeUrl;
    }

    public void setRecipeUrl(String recipeUrl) {
        this.recipeUrl = recipeUrl;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.recipeName);
        dest.writeString(this.ratings);
        dest.writeString(this.recipeImage);
        dest.writeString(this.recipeUrl);
    }

    protected Recipe(Parcel in) {
        this.recipeName = in.readString();
        this.ratings = in.readString();
        this.recipeImage = in.readString();
        this.recipeUrl = in.readString();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
