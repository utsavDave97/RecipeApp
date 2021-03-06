package com.example.recipeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipeapp.Adapters.YourRecipeRecyclerViewAdapter;
import com.example.recipeapp.DataHandler.SQLiteHelper;
import com.example.recipeapp.Model.MyRecipe;

import java.util.ArrayList;

import static com.example.recipeapp.MainActivity.toolbar;

/**
 * @author utsav
 * @date 12 March 2019
 *
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link YourRecipe.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link YourRecipe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YourRecipe extends Fragment
{
    //Declare RecyclerView, SQLite, ArrayList and Adapter
    RecyclerView yourRecipeRecyclerView;
    SQLiteHelper db;
    ArrayList<MyRecipe> myRecipeList;
    YourRecipeRecyclerViewAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public YourRecipe() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YourRecipe.
     */
    // TODO: Rename and change types and number of parameters
    public static YourRecipe newInstance(String param1, String param2) {
        YourRecipe fragment = new YourRecipe();
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
        View view = inflater.inflate(R.layout.fragment_your_recipe, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().show();
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Your Recipes");

        //Find the recyclerview by its ID
        yourRecipeRecyclerView = view.findViewById(R.id.yourRecipeRecyclerView);

        //Initialize the SQLite
        db = new SQLiteHelper(getContext());

        //populate the ArrayList of MyRecipe by calling getAllRecipes method of db.
        myRecipeList = db.getAllMyRecipes();

        //Initialize the adapter
        adapter = new YourRecipeRecyclerViewAdapter(getContext(),myRecipeList,getActivity().getSupportFragmentManager());

        //Set the LayoutManager and Adapter to the recyclerView
        yourRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        yourRecipeRecyclerView.setAdapter(adapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
