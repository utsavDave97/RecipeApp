package com.example.recipeapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.recipeapp.Model.MyRecipe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * @author utsav
 * @date 20-March-2019
 *
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EachRecipe.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EachRecipe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EachRecipe extends Fragment
{
    //Declare ImageView, TextView for the layout
    ImageView eachRecipeImage;
    TextView eachRecipeName,eachRecipeTime,
            eachRecipeIngredients, eachRecipeIngredientQuantity,
            eachRecipeDescription, eachRecipeUnit;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private MyRecipe mParam2;

    private OnFragmentInteractionListener mListener;

    public EachRecipe() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param2 Parameter 2.
     * @return A new instance of fragment EachRecipe.
     */
    // TODO: Rename and change types and number of parameters
    public static EachRecipe newInstance(Parcelable param2) {
        EachRecipe fragment = new EachRecipe();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam2 = getArguments().getParcelable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_each_recipe, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle(mParam2.getName());

        //initializing each widget with its ID
        eachRecipeImage = view.findViewById(R.id.eachRecipeImage);
        eachRecipeName = view.findViewById(R.id.eachRecipeName);
        eachRecipeTime = view.findViewById(R.id.eachRecipeTime);
        eachRecipeIngredients = view.findViewById(R.id.eachRecipeIngredient);
        eachRecipeIngredientQuantity = view.findViewById(R.id.eachRecipeIngredientQuantity);
        eachRecipeDescription = view.findViewById(R.id.eachRecipeDescription);
        eachRecipeUnit = view.findViewById(R.id.eachRecipeUnit);

        //Get the imageBytes from db and decode it
        byte[] imageBytes = Base64.decode(mParam2.getImagePath(),Base64.DEFAULT);
        //Convert the byteArray into Bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
        //set the Bitmap to imageView
        eachRecipeImage.setImageBitmap(bitmap);

        //Setting the name and time of Recipe from Parcelable object
        eachRecipeName.setText(mParam2.getName());
        eachRecipeTime.setText(mParam2.getTime());

        //Setting the ingredients, quantity and unit of Recipe from Parcelable object
        try {
            //Get the JSON object from local db
            JSONObject jsonObject = new JSONObject(mParam2.getIngredients());

            //Get the JSONArray from the JSONObject declared before this
            JSONArray ingredients = jsonObject.optJSONArray("ingredients");
            //Create an empty String array list
            ArrayList<String> arrayList = new ArrayList<String>();
            //loop through the JSONArray and store it in ArrayList of Strings declared earlier
            for (int i =0; i < ingredients.length(); i++)
            {
                arrayList.add(ingredients.getString(i));
            }

            //loop through ArrayList of String and attach it to TextView which would be seen to user
            for(String s: arrayList)
            {
                eachRecipeIngredients.append(s+"\n");
            }

            //Get the JSON object from local db
            JSONObject jsonObject2 = new JSONObject(mParam2.getQuantites());
            //Get the JSONArray from the JSONObject declared before this
            JSONArray quantities = jsonObject2.optJSONArray("quantites");
            //Create an empty String array list
            ArrayList<String> arrayList2 = new ArrayList<String>();
            //loop through the JSONArray and store it in ArrayList of Strings declared earlier
            for (int i =0; i < quantities.length(); i++)
            {
                arrayList2.add(quantities.getString(i));
            }
            //loop through ArrayList of String and attach it to TextView which would be seen to user
            for(String s: arrayList2)
            {
                eachRecipeIngredientQuantity.append(s+"\n");
            }

            //Get the JSON object from local db
            JSONObject jsonObject3 = new JSONObject(mParam2.getUnit());
            //Get the JSONArray from the JSONObject declared before this
            JSONArray units = jsonObject3.optJSONArray("units");
            //Create an empty String array list
            ArrayList<String> arrayList3 = new ArrayList<String>();
            //loop through the JSONArray and store it in ArrayList of Strings declared earlier
            for (int i =0; i < units.length(); i++)
            {
                arrayList3.add(units.getString(i));
            }
            //loop through ArrayList of String and attach it to TextView which would be seen to user
            for(String s: arrayList3)
            {
                eachRecipeUnit.append(s+"\n");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Setting description of Recipe from Parcelable object
        eachRecipeDescription.setText(mParam2.getDescription());

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
