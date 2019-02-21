package com.example.recipeapp;

/**
 * @author utsav
 * @date 21-Feb-2019
 * This is the Model class. Here Recipe is treated as an object.
 * Also, it has various properties and different methods to set value to those properties or access them.
 * Also, it has 2 constructors which would be helpful in Adapter and SQLiteDatabase
 */
public class Recipe
{
    //Declaring each property
    private String recipeName;
    private String ratings;
    private String recipeImage;
    private String recipeUrl;

    /**
     * Empty Constructor
     */
    public Recipe()
    {
    }

    /**
     * Construct which would take in recipeName, recipeRatings & recipeImage
     * @param recipeName
     * @param ratings
     * @param recipeImage
     */
    public Recipe(String recipeName, String ratings, String recipeImage) {
        this.recipeName = recipeName;
        this.ratings = ratings;
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
}
