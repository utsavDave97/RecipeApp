package com.example.recipeapp;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.recipeapp.Adapters.RecipeRecyclerViewAdapter;
import com.example.recipeapp.DataHandler.AppController;
import com.example.recipeapp.Model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/***
 * @author Utsav Dave
 * @date 19th Feb 2019
 *
 * This class file is for the Main Fragment. Here the data would be populated inside RecyclerView.
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment
{
    //Declaring all the required variables
    List<Recipe> recipeList = new ArrayList<>();
    RecipeRecyclerViewAdapter adapter;
    public static RecyclerView recipeRecyclerView;
    EditText selectRecipeEditText;
    Button searchButton;

    //Creating a URL which would be used when making Volley request
    private String URL_GET_RECIPE;
    private String URL_FILTER_RECIPE = "https://www.food2fork.com/api/search?key="+BuildConfig.API_KEY+"&q=";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //This line would ensure that the editText on the screen won't open keyboard as soon as the
        //screen launches.
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Getting RecyclerView by its ID
        recipeRecyclerView = view.findViewById(R.id.recipeRecyclerView);

        //Getting EditText & Button by its ID
        selectRecipeEditText = view.findViewById(R.id.selectRecipeEditText);
        searchButton = view.findViewById(R.id.searchButton);

        //Setting up LayoutManager for the RecyclerView
        if(getResources().getConfiguration().isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_XLARGE))
        {
            if(SettingsFragment.gridViewSwitch != null)
            {
                if(SettingsFragment.gridViewSwitch.isChecked())
                {
                    recipeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                }
                else
                {
                    recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }
        }
        else
        {
            recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        recipeList.clear();

        if(SettingsFragment.sp != null)
        {
            Log.w("Under SP",SettingsFragment.sp.getString(getString(R.string.sortbykey),"1"));
            Log.w("URL",URL_GET_RECIPE+"");

            if(SettingsFragment.sp.getString(getString(R.string.sortbykey),"1").equals(""))
            {
                URL_GET_RECIPE = "https://www.food2fork.com/api/search?key="+BuildConfig.API_KEY;
            }
            else if(SettingsFragment.sp.getString(getString(R.string.sortbykey),"1").equals("r"))
            {
                URL_GET_RECIPE = "https://www.food2fork.com/api/search?key="+BuildConfig.API_KEY+"&sort=r";
            }
            else if(SettingsFragment.sp.getString(getString(R.string.sortbykey),"1").equals("t"))
            {
                URL_GET_RECIPE = "https://www.food2fork.com/api/search?key="+BuildConfig.API_KEY+"&sort=t";
                Log.w("URL under if:",URL_GET_RECIPE+"");
            }
        }
        else
        {
            URL_GET_RECIPE = "https://www.food2fork.com/api/search?key="+BuildConfig.API_KEY;
        }


        //This would be populate data inside RecyclerView
        initializeData();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String searchText = selectRecipeEditText.getText().toString().trim();

                //Check if EditText is empty or not
                if(TextUtils.isEmpty(searchText))
                {
                    //If empty create a error
                    selectRecipeEditText.setError("Please enter recipe name");
                    selectRecipeEditText.requestFocus();
                    return;
                }

                filterData(searchText);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     *This method creates a Request to the URL specified and populates data based on the response
     */
    private void initializeData()
    {

        String tag_string_req = "req_get_recipe";

        //Create a StringRequest with GET Method
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_RECIPE,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        //Declare a JSON object
                        JSONObject jsonObject;
                        try
                        {
                            //Store the response inside the JSON Object
                            jsonObject = new JSONObject(response);

                            //Get the array of response and store it inside JSONArray
                            JSONArray array = jsonObject.getJSONArray("recipes");

                            //Loop through the array
                            for (int i = 0; i < array.length(); i++)
                            {
                                //Get single object
                                JSONObject recipe = array.getJSONObject(i);

                                //Add that object inside dataSet withe the help of Recipe's constructor
                                recipeList.add(new Recipe(
                                        recipe.getString("title"),
                                        recipe.getString("social_rank"),
                                        recipe.getString("image_url"),
                                        recipe.getString("source_url")
                                ));
                            }

                            //Initialize the Recycler View adapter with context and dataset
                            adapter = new RecipeRecyclerViewAdapter(getContext(),recipeList);

                            //Add adapter to the recyclerView
                            recipeRecyclerView.setAdapter(adapter);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });
        //Using getInstance method of Singleton class and adding the StringRequest to RequestQueue
        AppController.getInstance().addToRequestQueue(stringRequest,tag_string_req);
    }

    /**
     *
     * @param searchText
     */
    private void filterData(String searchText)
    {

        recipeList.clear();

        String tag_string_req = "req_get_filter_recipe";

        //Create a StringRequest with GET Method
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_FILTER_RECIPE+searchText,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        //Declare a JSON object
                        JSONObject jsonObject;
                        try
                        {
                            //Store the response inside the JSON Object
                            jsonObject = new JSONObject(response);

                            //Get the array of response and store it inside JSONArray
                            JSONArray array = jsonObject.getJSONArray("recipes");

                            //Loop through the array
                            for (int i = 0; i < array.length(); i++)
                            {
                                //Get single object
                                JSONObject recipe = array.getJSONObject(i);

                                //Add that object inside dataSet withe the help of Recipe's constructor
                                recipeList.add(new Recipe(
                                        recipe.getString("title"),
                                        recipe.getString("social_rank"),
                                        recipe.getString("image_url"),
                                        recipe.getString("source_url")
                                ));
                            }

                            //Initialize the Recycler View adapter with context and dataset
                            adapter = new RecipeRecyclerViewAdapter(getContext(),recipeList);

                            //Add adapter to the recyclerView
                            recipeRecyclerView.setAdapter(adapter);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });

        //Using getInstance method of Singleton class and adding the StringRequest to RequestQueue
        AppController.getInstance().addToRequestQueue(stringRequest,tag_string_req);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
