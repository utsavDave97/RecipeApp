package com.example.recipeapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import java.util.ArrayList;
import java.util.List;

public class AutocompleteAdapter extends ArrayAdapter<String> implements Filterable
{
    private ArrayList<String> data;

    public AutocompleteAdapter(@NonNull Context context, int resource)
    {
        super(context, resource);
        this.data = new ArrayList<>();
    }

    public void setData(List<String> list)
    {
        data.clear();
        data.addAll(list);
    }

    public String getObject(int position)
    {
        return data.get(position);
    }

    @Override
    public int getCount()
    {
        return data.size();
    }

    @Nullable
    @Override
    public String getItem(int position)
    {
        return data.get(position);
    }

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
