package com.example.recipeapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipeapp.DataHandler.SQLiteHelper;
import com.example.recipeapp.FavoritesFragment;
import com.example.recipeapp.MainActivity;
import com.example.recipeapp.Model.Recipe;
import com.example.recipeapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author utsav
 * @date 21-Feb-2019
 * This class would act as Adapter for RecyclerView on HomeScreen
 */
public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.MyViewHolder>
{
    //Initializing Context, List and Boolean
    private Context context;
    private List<Recipe> recipeList;
    Boolean clicked = true;

    /**
     * Constructor which would take in the context and dataset
     * @param context
     * @param recipeList
     */
    public RecipeRecyclerViewAdapter(Context context, List<Recipe> recipeList)
    {
        this.context = context;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.each_recipe_item,viewGroup,false);

        return new MyViewHolder(view);
    }

    //Replace the contents of view with the Dataset values
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i)
    {
        //Getting first Item and storing it in Model object
        final Recipe recipe = recipeList.get(i);

        /**
         * Setting Name, Ratings, Image, URL of Recipe with the appropriate data.
         * This data would be accessed with the help of getters method of Recipe Object.
         *
         */
        myViewHolder.recipeNameTextView.setText(recipe.getRecipeName());
        myViewHolder.recipeRatingsTextView.setText(String.format("Ratings: %.2f", recipe.getRatings()));
        Picasso.get().load(recipe.getRecipeImage()).fit().into(myViewHolder.recipeImage);
        myViewHolder.webButton.setOnClickListener(new View.OnClickListener() {
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

        /**
         * First: Getting access to favoriteButton with the help of view holder
         * Second: Setting up onClickListener on that favorite button
         * Third: If clicked it would change its state from unclicked to clicked
         */
        myViewHolder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(clicked)
                {
                    MainActivity mainActivity = (MainActivity) context;
                    clicked = false;
                    myViewHolder.favoriteButton.setImageResource(R.drawable.ic_favorite_red);

                    SQLiteHelper db = new SQLiteHelper(context);

                    db.addFavorite(recipeList.get(i));

                    Toast.makeText(context,"Added to Favorites",Toast.LENGTH_SHORT);
                }
                else
                {
                    clicked  = true;
                    myViewHolder.favoriteButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }
            }
        });

        /**
         * First: Getting access to shareButton with the help of view holder
         * Second: Setting up onClickListener on that share button
         * Third: If clicked it would pop-up a bottom sheet and the user can share recipe's url to others
         */
        myViewHolder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,"Check this Recipe out " + recipeList.get(i).getRecipeUrl());
                intent.setType("text/plain");
                context.startActivity(Intent.createChooser(intent,"Send To"));
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
        private TextView recipeNameTextView;
        private TextView recipeRatingsTextView;
        private ImageView recipeImage;
        private ImageButton favoriteButton;
        private ImageButton shareButton;
        private Button webButton;

        /**
         * This Constructor if called would assign the variables declared before to its ID in view.
         * @param itemView
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeNameTextView = itemView.findViewById(R.id.recipeNameTextView);
            recipeRatingsTextView = itemView.findViewById(R.id.recipeRatingsTextView);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
            shareButton = itemView.findViewById(R.id.shareButton);
            webButton = itemView.findViewById(R.id.webButton);
        }
    }
}
