package com.example.recipeapp.DataHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.recipeapp.Model.MyRecipe;
import com.example.recipeapp.Model.Recipe;
import java.util.ArrayList;

/***
 * @author utsav
 * @date 3rd March 2019
 */
public class SQLiteHelper extends SQLiteOpenHelper
{

    /**
     * Keep track of the database version
     */

    public static final int DATABASE_VERSION = 1;

    /**
     * Create the name of the Database
     */

    public static final String DATABASE_NAME = "recipeapp";

    /**
     * Create the name of our tables
     */

    public static final String TABLE_FAVORITES = "favorites";
    public static final String TABLE_MYRECIPE = "myrecipe";

    /**
     * CREATE column names
     */

    public static final String KEY_ID = "id";

    /**
     *  Favorites table column names
     */

    public static final String KEY_NAME = "name";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_URL = "url";

    /**
     *  MyRecipe table column names
     */

    public static final String KEY_RECIPE_NAME = "recipeName";
    public static final String KEY_RECIPE_TIME = "recipeTime";
    public static final String KEY_RECIPE_IMAGE = "recipeImage";
    public static final String KEY_RECIPE_INGREDIENTS = "ingredients";
    public static final String KEY_RECIPE_QUANTITIES = "quantites";
    public static final String KEY_RECIPE_DESCRIPTION = "description";

    /**
     * Create statements for our tables
     */

    public static final String CREATE_FAVORITES_TABLE = "CREATE TABLE " +
            TABLE_FAVORITES + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT UNIQUE, " + KEY_IMAGE + " TEXT,"
            + KEY_URL + " TEXT)";

    public static final String CREATE_MYRECIPE_TABLE = "CREATE TABLE " +
            TABLE_MYRECIPE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_RECIPE_NAME + " TEXT, " + KEY_RECIPE_TIME + " TEXT," + KEY_RECIPE_IMAGE + " TEXT,"
            + KEY_RECIPE_INGREDIENTS + " TEXT," + KEY_RECIPE_QUANTITIES + " TEXT," +KEY_RECIPE_DESCRIPTION + " TEXT)";

    public SQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_FAVORITES_TABLE);
        db.execSQL(CREATE_MYRECIPE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }

    /**
     * CRUD OPERATIONS FOR THE DATABASE AND TABLES
     * Create
     * Read
     * Update
     * Delete
     */

    /**
     * CREATE OPERATIONS
     */
    public void addFavorite(Recipe recipe){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, recipe.getRecipeName());
        values.put(KEY_IMAGE, recipe.getRecipeImage());
        values.put(KEY_URL, recipe.getRecipeUrl());
        db.insert(TABLE_FAVORITES, null, values);
        db.close();
    }


    public void addMyRecipe(MyRecipe myRecipe)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RECIPE_NAME, myRecipe.getName());
        values.put(KEY_RECIPE_TIME,myRecipe.getTime());
        values.put(KEY_RECIPE_IMAGE,myRecipe.getImagePath());
        values.put(KEY_RECIPE_INGREDIENTS,myRecipe.getIngredients());
        values.put(KEY_RECIPE_QUANTITIES,myRecipe.getQuantites());
        values.put(KEY_RECIPE_DESCRIPTION,myRecipe.getDescription());
        db.insert(TABLE_MYRECIPE, null, values);
        db.close();
    }

    /**
     * READ OPERATIONS
     */
    public ArrayList<Recipe> getAllFavorties(){
        ArrayList<Recipe> favoriteList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FAVORITES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                favoriteList.add(new Recipe(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)));
            }while(cursor.moveToNext());
        }
        db.close();
        return favoriteList;
    }

    public ArrayList<MyRecipe> getAllMyRecipes()
    {
        ArrayList<MyRecipe> myRecipeList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_MYRECIPE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                myRecipeList.add(new MyRecipe(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)));
            }while(cursor.moveToNext());
        }
        db.close();
        return myRecipeList;
    }

    /**
     * DELETE OPERATIONS
     */

    public void deleteFavoriteRecipe(String recipe){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, KEY_ID + "=?",
                new String[]{recipe});
        db.close();
    }

    public void deleteMyRecipe(int recipe){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MYRECIPE, KEY_ID + "=?",
                new String[]{String.valueOf(recipe)});
        db.close();
    }
}
