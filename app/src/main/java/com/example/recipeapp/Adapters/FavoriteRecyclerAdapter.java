package com.example.recipeapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipeapp.DataHandler.SQLiteHelper;
import com.example.recipeapp.Model.Recipe;
import com.example.recipeapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author utsav
 * @date 3-Mar-2019
 * This class would act as Adapter for RecyclerView on Favorites Screen
 */
public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.MyViewHolder>
{
    //Initializing Context, List and Boolean
    private Context context;
    private List<Recipe> recipeList;

    /**
     * Constructor which would take in the context and dataset
     * @param context
     * @param recipeList
     */
    public FavoriteRecyclerAdapter(Context context, List<Recipe> recipeList)
    {
        this.context = context;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public FavoriteRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.each_favorite_item,viewGroup,false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        /**
         * Make the entire CardView Clickable
         */
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Grab the current location in the list
                                int recipe = myViewHolder.getAdapterPosition();

                                //Open the database
                                SQLiteHelper db = new SQLiteHelper(context);
                                /**
                                 * look in the locations list and grab the item at 'location'
                                 * Grab that items id
                                 * remove from the database the location at that items id
                                 */
                                db.deleteFavoriteRecipe(recipeList.get(recipe).getRecipeName());

                                //We also remove the location from the ArrayList
                                recipeList.remove(recipe);

                                //Refresh the ArrayList
                                notifyItemRemoved(recipe);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });

        return new FavoriteRecyclerAdapter.MyViewHolder(view);
    }

    //Replace the contents of view with the Dataset values
    @Override
    public void onBindViewHolder(@NonNull final FavoriteRecyclerAdapter.MyViewHolder myViewHolder, int i)
    {
        //Getting first Item and storing it in Model object
        final Recipe recipe = recipeList.get(i);

        /**
         * Setting Name, Image, URL of Recipe with the appropriate data.
         * This data would be accessed with the help of getters method of Recipe Object.
         *
         */
        myViewHolder.favoriteRecipeName.setText(recipe.getRecipeName());
        Picasso.get().load(recipe.getRecipeImage()).fit().into(myViewHolder.favoriteRecipeImage);
        myViewHolder.favoriteRecipeURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri website = Uri.parse(recipe.getRecipeUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(website);
                if(intent.resolveActivity(context.getPackageManager()) != null){
                    context.startActivity(intent);
                }
            }
        });
    }

    /**
     * This method would return the size of dataset
     * @return int
     */
    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    /**
     * This class would reference to each item inside the view.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        //Initializing each view items
        private TextView favoriteRecipeName;
        private ImageView favoriteRecipeImage;
        private Button favoriteRecipeURL;

        /**
         * This Constructor if called would assign the variables declared before to its ID in view.
         * @param itemView
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            favoriteRecipeName = itemView.findViewById(R.id.favoriteRecipeName);
            favoriteRecipeImage = itemView.findViewById(R.id.favoriteRecipeImage);
            favoriteRecipeURL = itemView.findViewById(R.id.favoriteRecipeURL);
        }
    }
}
