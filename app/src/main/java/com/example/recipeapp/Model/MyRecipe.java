package com.example.recipeapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author utsav
 * @date 10-Mar-2019
 *
 * This Singleton class is the Model class for the Recipes created by the user inside
 * Create Your Own steps.
 *
 * This class implements Parcelable class, so that data of this object could be passed from one
 * fragment to another.
 */
public class MyRecipe implements Parcelable
{
    //Declare all the properties
    private int id;
    private String imagePath;
    private String name;
    private String time;
    private String ingredients;
    private String quantites;
    private String description;
    private String unit;

    /***
     * Create a Constructor matching the properties
     * @param id
     * @param name
     * @param time
     * @param imagePath
     * @param ingredients
     * @param quantites
     * @param unit
     * @param description
     */
    public MyRecipe(int id,String name, String time, String imagePath, String ingredients, String quantites,String unit, String description) {
        this.id = id;
        this.imagePath = imagePath;
        this.name = name;
        this.time = time;
        this.ingredients = ingredients;
        this.quantites = quantites;
        this.unit = unit;
        this.description = description;
    }

    private static MyRecipe instance;

    /**
     * Providing Getters & Setters for each property
     */
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getQuantites() {
        return quantites;
    }

    public void setQuantites(String quantites) {
        this.quantites = quantites;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //Empty Constructor
    private MyRecipe()
    {
    }

    public static MyRecipe getInstance()
    {
        if(instance == null)
        {
            instance = new MyRecipe();
        }
        return instance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.imagePath);
        dest.writeString(this.name);
        dest.writeString(this.time);
        dest.writeString(this.ingredients);
        dest.writeString(this.quantites);
        dest.writeString(this.description);
        dest.writeString(this.unit);
    }

    protected MyRecipe(Parcel in) {
        this.id = in.readInt();
        this.imagePath = in.readString();
        this.name = in.readString();
        this.time = in.readString();
        this.ingredients = in.readString();
        this.quantites = in.readString();
        this.description = in.readString();
        this.unit = in.readString();
    }

    public static final Creator<MyRecipe> CREATOR = new Creator<MyRecipe>() {
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
