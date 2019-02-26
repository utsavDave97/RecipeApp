package com.example.recipeapp.Model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class MyRecipe implements Parcelable
{
    private String imagePath;
    private String name;
    private String time;
    private ArrayList<String> ingredients;
    private ArrayList<String> quantites;
    private String description;
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imagePath);
        dest.writeString(this.name);
        dest.writeString(this.time);
        dest.writeStringList(this.ingredients);
        dest.writeStringList(this.quantites);
        dest.writeString(this.description);
    }

    public MyRecipe() {
    }

    protected MyRecipe(Parcel in) {
        this.imagePath = in.readString();
        this.name = in.readString();
        this.time = in.readString();
        this.ingredients = in.createStringArrayList();
        this.quantites = in.createStringArrayList();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<MyRecipe> CREATOR = new Parcelable.Creator<MyRecipe>() {
        @Override
        public MyRecipe createFromParcel(Parcel source) {
            return new MyRecipe(source);
        }

        @Override
        public MyRecipe[] newArray(int size) {
            return new MyRecipe[size];
        }
    };
}
