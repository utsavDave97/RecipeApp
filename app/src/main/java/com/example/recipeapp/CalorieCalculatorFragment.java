package com.example.recipeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalorieCalculatorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalorieCalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author utsav
 * @date 3rd-Mar-2019
 */
public class CalorieCalculatorFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Declaring EditTexts, Button, TextViews, RadioGroups & Spinner
    Spinner exerciseSpinner;
    Button calculateButton, resetButton;
    TextView maintainTextView, lossTextView, extremeLossTextView;
    RadioGroup genderGroup;
    TextInputEditText weightFieldText, heightFieldText, ageFieldText;

    //Declaring double variable for Basal Metabolic Rate (BMR)
    double bmr, weight, height, age, genderValue, exercise,maintain,fatLossTemp, fatLoss, extremeLossTemp, extremeLoss;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CalorieCalculatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalorieCalculatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalorieCalculatorFragment newInstance(String param1, String param2) {
        CalorieCalculatorFragment fragment = new CalorieCalculatorFragment();
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
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calorie_calculator, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().show();
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Calorie Calculator");

        //initializing each widget with its ID
        exerciseSpinner = view.findViewById(R.id.exerciseSpinner);
        calculateButton = view.findViewById(R.id.calculateButton);
        resetButton = view.findViewById(R.id.resetButton);
        weightFieldText = view.findViewById(R.id.weightFieldText);
        heightFieldText = view.findViewById(R.id.heightFieldText);
        ageFieldText = view.findViewById(R.id.ageFieldText);
        maintainTextView = view.findViewById(R.id.maintainTextView);
        lossTextView = view.findViewById(R.id.lossTextView);
        extremeLossTextView = view.findViewById(R.id.extremeLossTextView);
        genderGroup = view.findViewById(R.id.genderGroup);

        //Creating a list of values which would be used in Spinner Adapter
        List<String> spinnerList = new ArrayList<String>();
        spinnerList.add("Little/No Exercise");
        spinnerList.add("Light Exercise");
        spinnerList.add("Moderate Exercise");
        spinnerList.add("Hard Exercise");
        spinnerList.add("Very Hard Exercise");

        //Initializing the adapter
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,spinnerList);

        //Setting the adapter with Spinner
        exerciseSpinner.setAdapter(spinnerAdapter);

        /***
         * This method would give us the value of item selected inside the Spinner
         * @param parent
         * @param view
         * @param position
         * @param id
         */
        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position)
                {
                    case 0:
                        exercise = 1.2;
                        break;
                    case 1:
                        exercise = 1.375;
                        break;
                    case 2:
                        exercise = 1.55;
                        break;
                    case 3:
                        exercise = 1.725;
                        break;
                    case 4:
                        exercise = 1.9;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /***
         * Setting up listener on gender RadioGroup, through which we can know if the user selected
         * male or female
         */
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                View radioButton = genderGroup.findViewById(checkedId);

                int index = genderGroup.indexOfChild(radioButton);

                switch (index)
                {
                    case 0:
                        genderValue = 5;
                        break;
                    case 1:
                        genderValue = -161;
                        break;
                }
            }
        });

        //Setting onClickListener on Calculate Button
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Conditions to check if age, weight, height or gender is empty
                if(TextUtils.isEmpty(ageFieldText.getText()))
                {
                    ageFieldText.setError("Please enter age.");
                    ageFieldText.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(weightFieldText.getText()))
                {
                    weightFieldText.setError("Please enter weight.");
                    weightFieldText.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(heightFieldText.getText()))
                {
                    heightFieldText.setError("Please enter height.");
                    heightFieldText.requestFocus();
                    return;
                }

                if(genderValue == 0)
                {
                    Toast.makeText(getContext(),"Please select gender",Toast.LENGTH_SHORT).show();
                    return;
                }

                //Assigning weight, height and age their respective values
                weight = Double.parseDouble(weightFieldText.getText().toString());
                height = Double.parseDouble(heightFieldText.getText().toString());
                age = Double.parseDouble(ageFieldText.getText().toString());

                //Calculating BMR by using Mifflin St.Jeor formula
                bmr = (10 * weight) + (6.25 * height) - (5 * age) + genderValue;

                //Calculating maintain
                maintain = bmr * exercise;

                //Calculating fat loss by reducing 20% from maintain value
                fatLossTemp = maintain * 0.20;
                fatLoss = maintain - fatLossTemp;

                //Calculating extreme fat loss by reducing 40% from maintain value
                extremeLossTemp = maintain * 0.40;
                extremeLoss = maintain - extremeLossTemp;

                //Assigning values to respective TextViews to  show results
                maintainTextView.setText(String.valueOf(String.format("%.2f",maintain))+" per/day");
                lossTextView.setText(String.valueOf(String.format("%.2f",fatLoss))+" per/day");
                extremeLossTextView.setText(String.valueOf(String.format("%.2f",extremeLoss))+" per/day");
            }
        });

        //Setting onClickListener on Reset Button
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ageFieldText.setText("");
                weightFieldText.setText("");
                heightFieldText.setText("");
                maintainTextView.setText("N/A");
                lossTextView.setText("N/A");
                extremeLossTextView.setText("N/A");
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
