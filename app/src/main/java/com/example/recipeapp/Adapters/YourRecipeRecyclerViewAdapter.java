package com.example.recipeapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.recipeapp.DataHandler.SQLiteHelper;
import com.example.recipeapp.EachRecipe;
import com.example.recipeapp.Model.MyRecipe;
import com.example.recipeapp.R;

import java.util.HashMap;
import java.util.List;

/**
 * @author utsav
 * @date 10-March-2019
 * This class would act as Adapter for RecyclerView on YourRecipeScreen
 */
public class YourRecipeRecyclerViewAdapter extends RecyclerView.Adapter<YourRecipeRecyclerViewAdapter.MyViewHolder>
{

    //Initializing Context, List
    private Context context;
    FragmentManager fm;
    private List<MyRecipe> myRecipeList;
    SQLiteHelper db;

    /**
     * Constructor which would take in the context and dataset
     * @param context
     * @param myRecipeList
     */
    public YourRecipeRecyclerViewAdapter(Context context, List<MyRecipe> myRecipeList, FragmentManager fm)
    {
        this.context = context;
        this.myRecipeList = myRecipeList;
        this.fm = fm;
    }

    @NonNull
    @Override
    public YourRecipeRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.each_your_recipe,viewGroup,false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int myRecipe = myViewHolder.getAdapterPosition();

                FragmentTransaction transaction = fm.beginTransaction();

                transaction.replace(R.id.content, EachRecipe.newInstance(myRecipeList.get(myRecipe))).addSharedElement(myViewHolder.yourMyRecipeImage,"imageTransition").addToBackStack(null).commit();
            }
        });

        return myViewHolder;
    }

    //Replace the contents of view with the Dataset values
    @Override
    public void onBindViewHolder(@NonNull YourRecipeRecyclerViewAdapter.MyViewHolder myViewHolder, int i)
    {
        MyRecipe myRecipe = myRecipeList.get(i);

        myViewHolder.yourMyRecipeName.setText(myRecipe.getName());

        byte[] imageBytes = Base64.decode(myRecipe.getImagePath(),Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);

        myViewHolder.yourMyRecipeImage.setImageBitmap(bitmap);

        myViewHolder.optionsMyRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,myViewHolder.optionsMyRecipe);

                popupMenu.inflate(R.menu.options_myrecipe);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.deleteMyRecipe:

                                db = new SQLiteHelper(context);

                                db.deleteMyRecipe(myRecipeList.get(i).getId());

                                myRecipeList.remove(i);
                                notifyItemRemoved(i);

                                break;
                            case R.id.editMyRecipe:
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });
    }

    /**
     * This method would return the size of dataset
     * @return int
     */
    @Override
    public int getItemCount() {
        return myRecipeList.size();
    }

    /**
     * This class would reference to each item inside the view.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        //Initializing each view items
        private TextView yourMyRecipeName;
        private ImageView yourMyRecipeImage;
        private ImageButton optionsMyRecipe;

        /**
         * This Constructor if called would assign the variables declared before to its ID in view.
         * @param itemView
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            yourMyRecipeName = itemView.findViewById(R.id.yourMyRecipeName);
            yourMyRecipeImage = itemView.findViewById(R.id.yourMyRecipeImage);
            optionsMyRecipe = itemView.findViewById(R.id.optionsMyRecipe);
        }
    }
}
