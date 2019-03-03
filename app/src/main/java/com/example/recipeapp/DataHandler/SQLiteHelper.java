package com.example.recipeapp.DataHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
     * Create statements for our tables
     */

    public static final String CREATE_FAVORITES_TABLE = "CREATE TABLE " +
            TABLE_FAVORITES + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT, " + KEY_IMAGE + " TEXT,"
            + KEY_URL + " TEXT)";

    public SQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_FAVORITES_TABLE);
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
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)));
            }while(cursor.moveToNext());
        }
        db.close();
        return favoriteList;
    }

    /**
     * DELETE OPERATIONS
     */

    public void deleteLocation(int location){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, KEY_ID + "=?",
                new String[]{String.valueOf(location)});
        db.close();
    }
}
