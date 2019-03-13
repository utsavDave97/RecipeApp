package com.example.recipeapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class MyRecipe implements Parcelable
{

    private int id;
    private String imagePath;
    private String name;
    private String time;
    private String ingredients;
    private String quantites;
    private String description;

    public MyRecipe(int id,String name, String time, String imagePath, String ingredients, String quantites, String description) {
        this.id = id;
        this.imagePath = imagePath;
        this.name = name;
        this.time = time;
        this.ingredients = ingredients;
        this.quantites = quantites;
        this.description = description;
    }

    private static MyRecipe instance;
    
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
    }

    protected MyRecipe(Parcel in) {
        this.id = in.readInt();
        this.imagePath = in.readString();
        this.name = in.readString();
        this.time = in.readString();
        this.ingredients = in.readString();
        this.quantites = in.readString();
        this.description = in.readString();
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
