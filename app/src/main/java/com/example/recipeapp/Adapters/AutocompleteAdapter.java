package com.example.recipeapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is for adapter for Autocompletion of AutoCompleteTextView inside Step 2 of
 * Create Your Own Recipe
 *
 * @author utsav
 * @date 12-March-2019
 */
public class AutocompleteAdapter extends ArrayAdapter<String> implements Filterable
{
    //Create a ArrayList of String which would store data
    private ArrayList<String> data;

    //Initialize the constructor
    public AutocompleteAdapter(@NonNull Context context, int resource)
    {
        super(context, resource);
        this.data = new ArrayList<>();
    }

    //Create setters for data variable
    public void setData(List<String> list)
    {
        data.clear();
        data.addAll(list);
    }

    public String getObject(int position)
    {
        return data.get(position);
    }

    //Override the getCount method to get the size of data
    @Override
    public int getCount()
    {
        return data.size();
    }

    //Override the getItem method to get the item at specified position
    @Nullable
    @Override
    public String getItem(int position)
    {
        return data.get(position);
    }


    //Override the getFilter method, it would check if the character inserted inside the
    //AutocompleteTextView is empty or not
    @NonNull
    @Override
    public Filter getFilter()
    {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint)
            {
                FilterResults results = new FilterResults();

                if(constraint != null)
                {
                    results.values =data;
                    results.count = data.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results)
            {
                if (results != null && results.count > 0)
                {
                    notifyDataSetChanged();
                }
                else notifyDataSetInvalidated();
            }
        };

        return filter;
    }
}
