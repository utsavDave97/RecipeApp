package com.example.recipeapp.Model;

import java.util.ArrayList;

public class MyRecipe
{
    private String imagePath;
    private String name;
    private String time;
    private ArrayList<String> ingredients;
    private ArrayList<String> quantites;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getQuantites() {
        return quantites;
    }

    public void setQuantites(ArrayList<String> quantites) {
        this.quantites = quantites;
    }
}
