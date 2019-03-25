package com.example.recipeapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.recipeapp.R;

import java.util.ArrayList;

/**
 * This class is the Adapter class for ListView which would be inside the Step 2 of Create Your Own Recipe
 * Form.
 *
 * @author utsav
 * @date 22-Feb-2019
 */
public class ListViewAdapter extends ArrayAdapter
{
    //Declaring Context, ArrayLists
    private Context context;
    private ArrayList<String> ingredients;
    private ArrayList<String> quantities;
    private ArrayList<String> units;

    //Creating a constructor with context and arrayLists
    public ListViewAdapter(Context context, ArrayList<String> ingredients, ArrayList<String> quantities, ArrayList<String> units)
    {
        super(context,-1,ingredients);
        this.context = context;
        this.ingredients = ingredients;
        this.quantities = quantities;
        this.units = units;
    }


    /***
     * This method first gets layout of each List item from XML file.
     *
     * With the help of the view obtained, IDs are obtained of TextViews, ImageViews in that layout file.
     *
     *
     * @param position
     * @param convertView
     * @param parent
     * @return View
     */
    @NonNull
    @Override
    public View getView(int position,  @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Inflating the layout
        View view = inflater.inflate(R.layout.ingredient_item_view,parent,false);

        //Getting IDs and assigning them
        TextView ingredient = view.findViewById(R.id.ingredient);
        TextView qty = view.findViewById(R.id.qty);
        TextView unit = view.findViewById(R.id.unit);
        ImageButton removeButton = view.findViewById(R.id.removeButton);

        //Setting text to TextViews on the basis of data passed
        ingredient.setText(ingredients.get(position));
        qty.setText(quantities.get(position));
        unit.setText(units.get(position));

        /**
         * Setting onClickListener on RemoveButton
         *
         * This would remove the item from the ListView and notify the Adapter
         */
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ingredients.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
