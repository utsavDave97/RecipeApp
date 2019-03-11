package com.example.recipeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.recipeapp.Adapters.AutocompleteAdapter;
import com.example.recipeapp.Adapters.ListViewAdapter;
import com.example.recipeapp.Adapters.RecipeRecyclerViewAdapter;
import com.example.recipeapp.DataHandler.AppController;
import com.example.recipeapp.Model.MyRecipe;
import com.example.recipeapp.Model.Recipe;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


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
public class Step2 extends Fragment implements BlockingStep
{
    //Declaring EditTexts and ImageButton
    AppCompatAutoCompleteTextView yourRecipeIngredientName;
    EditText yourRecipeIngredientQty;
    ImageButton addIngredientButton;

    boolean isSelected = false;

    //Declaring AutoCompleteTextViews, Handler and Url where the call would be made
    private String URL_GET_INGREDIENT = "https://php.scweb.ca/~udave/apiRecipe/getIngredient.php?ingredient=",result;
    private Handler handler;
    private AutocompleteAdapter autocompleteAdapter;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final int AUTO_COMPLETE_DELAY = 300;

    //Declaring Listview and ArrayList of ingredients & quantities
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> ingredients = new ArrayList<String>();
    ArrayList<String> quantities = new ArrayList<String>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "myrecipe";

    // TODO: Rename and change types of parameters
    private MyRecipe mParam1;

    private OnFragmentInteractionListener mListener;

    public Step2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Step2.
     */
    // TODO: Rename and change types and number of parameters
    public static Step2 newInstance(Parcelable param1) {
        Step2 fragment = new Step2();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step2, container, false);

        mParam1 = MyRecipe.getInstance();

        //This line would ensure that the editText on the screen won't open keyboard as soon as the
        //screen launches.
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Getting the EditTexts, ImageView & ListView by its ID
        addIngredientButton = view.findViewById(R.id.addIngredientButton);
        yourRecipeIngredientName = view.findViewById(R.id.yourRecipeIngredientName);
        yourRecipeIngredientQty = view.findViewById(R.id.yourRecipeIngredientQty);
        listView = view.findViewById(R.id.listView);

        autocompleteAdapter = new AutocompleteAdapter(getContext(),android.R.layout.simple_dropdown_item_1line);

        yourRecipeIngredientName.setThreshold(1);
        yourRecipeIngredientName.setAdapter(autocompleteAdapter);

        yourRecipeIngredientName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                isSelected = true;
                yourRecipeIngredientName.setText(autocompleteAdapter.getObject(position));
            }
        });

        yourRecipeIngredientName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                isSelected = false;
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what == TRIGGER_AUTO_COMPLETE)
                {
                    if(!TextUtils.isEmpty(yourRecipeIngredientName.getText()))
                    {
                        callApi(yourRecipeIngredientName.getText().toString());
                    }
                }
                return false;
            }
        });

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
                    yourRecipeIngredientName.setError("Please enter ingredient from drop down.");
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

    private void callApi(String text)
    {
        String tag_string_req = "req_get_ingredient";

        //Create a StringRequest with GET Method
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_INGREDIENT+text,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        List<String> stringList = new ArrayList<>();

                        //Declare a JSON object
                        JSONObject jsonObject;
                        try
                        {
                            //Store the response inside the JSON Object
                            jsonObject = new JSONObject(response);

                            //Get the array of response and store it inside JSONArray
                            JSONArray array = jsonObject.getJSONArray("results");

                            //Loop through the array
                            for (int i = 0; i < array.length(); i++)
                            {
                                //Get single object
                                JSONObject recipe = array.getJSONObject(i);

                                //Add that object inside dataSet withe the help of Recipe's constructor
                                stringList.add(recipe.getString("ingredient"));
                            }

                            //Initialize the Recycler View adapter with context and dataset
                            autocompleteAdapter.setData(stringList);
                            autocompleteAdapter.notifyDataSetChanged();
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

    /***
     * This method is implemented if we want to make sure the user fills out all the required information
     * before the user moves onto next step.
     *
     * Here inside this method it is verified that the user enters atleast one ingredient
     * before moving on to next step.
     *
     * If all the requirements are met it would allow the user to move onto next step.
     *
     * @return VerificationError
     */
    @Nullable
    @Override
    public VerificationError verifyStep()
    {
        if(ingredients.isEmpty() == true)
        {
            return new VerificationError("Minimum 1 Ingredient");
        }
        else
        {
            return null;
        }
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback)
    {

        //First create a JSON object for ingredients
        JSONObject jsonIngredient = new JSONObject();
        //Surround it by try-catch
        try {
            //Inside that JSON object put an Array of ingredients
            jsonIngredient.put("ingredients",new JSONArray(ingredients));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //First create a JSON object for quantities
        JSONObject jsonQuantity = new JSONObject();
        //Surround it by try-catch
        try {
            //Inside that JSON object put an Array of quantities
            jsonQuantity.put("quantites",new JSONArray(quantities));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //get ingredients and quantites string and set it to MyRecipe Object
        String ingredients = jsonIngredient.toString();
        String quantities = jsonQuantity.toString();

        mParam1.setIngredients(ingredients);
        mParam1.setQuantites(quantities);

        //Go to next step
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback)
    {
        callback.goToPrevStep();
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
