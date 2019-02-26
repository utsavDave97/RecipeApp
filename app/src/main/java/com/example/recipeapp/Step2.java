package com.example.recipeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.recipeapp.Adapters.ListViewAdapter;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;


/**
 * @author utsav
 * @date 22-Feb-2019
 *
 *  This is fragment class is the class which would show view and process information for Step 2 of
 *  inside Create Your Own Recipe form.
 *
 *  This fragment class also implements Step Library, this library is used to create step-by-step
 *  form.
 *
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Step2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Step2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Step2 extends Fragment implements Step
{
    //Declaring EditTexts and ImageButton
    EditText yourRecipeIngredientName, yourRecipeIngredientQty;
    ImageButton addIngredientButton;

    //Declaring Listview and ArrayList of ingredients & quantities
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> ingredients = new ArrayList<String>();
    ArrayList<String> quantities = new ArrayList<String>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Step2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Step2.
     */
    // TODO: Rename and change types and number of parameters
    public static Step2 newInstance(String param1, String param2) {
        Step2 fragment = new Step2();
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
        View view = inflater.inflate(R.layout.fragment_step2, container, false);

        //This line would ensure that the editText on the screen won't open keyboard as soon as the
        //screen launches.
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Getting the EditTexts, ImageView & ListView by its ID
        addIngredientButton = view.findViewById(R.id.addIngredientButton);
        yourRecipeIngredientName = view.findViewById(R.id.yourRecipeIngredientName);
        yourRecipeIngredientQty = view.findViewById(R.id.yourRecipeIngredientQty);
        listView = view.findViewById(R.id.listView);

        //Setting up the Adapter which takes context, and two other ArrayList as parameters
        adapter = new ListViewAdapter(getContext(),ingredients,quantities);

        /**
         * Implementing onClickListener on addIngredient ImageButton
         *
         * Before adding Ingredient it would check whether the user has entered Ingredient Name
         * Also, it would check whether the user has entered Ingredient Quantity
         *
         * If all these conditions are met it would add the ingredient and its respective quantity
         * to ArrayLists and reset the fields again
         */
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(yourRecipeIngredientName.getText().toString().isEmpty())
                {
                    yourRecipeIngredientName.setError("Please enter ingredient.");
                    yourRecipeIngredientName.requestFocus();
                }
                else if(yourRecipeIngredientQty.getText().toString().isEmpty())
                {
                    yourRecipeIngredientQty.setError("Please enter Qty.");
                    yourRecipeIngredientQty.requestFocus();
                }
                else
                {
                    ingredients.add(yourRecipeIngredientName.getText().toString());
                    quantities.add(yourRecipeIngredientQty.getText().toString());
                    adapter.notifyDataSetChanged();
                    yourRecipeIngredientName.setText("");
                    yourRecipeIngredientQty.setText("");
                }
            }
        });

        //Setting up the adapter with the ListView
        listView.setAdapter(adapter);

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

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

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
