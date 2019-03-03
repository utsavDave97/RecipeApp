package com.example.recipeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
public class CalorieCalculatorFragment extends Fragment implements AdapterView.OnItemSelectedListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Declaring EditTexts, Button, TextViews, RadioGroups & Spinner
    Spinner exerciseSpinner;
    Button calculateButton;
    RadioGroup genderGroup, weightGroup, heightGroup;
    TextInputEditText weightFieldText, heightFieldText, ageFieldText;


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

        exerciseSpinner = view.findViewById(R.id.exerciseSpinner);

        List<String> spinnerList = new ArrayList<String>();
        spinnerList.add("Little/No Exercise");
        spinnerList.add("Light Exercise");
        spinnerList.add("Moderate Exercise");
        spinnerList.add("Hard Exercise");
        spinnerList.add("Very Hard Exercise");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,spinnerList);

        exerciseSpinner.setAdapter(spinnerAdapter);

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

    /***
     * This method would give us the value of item selected inside the Spinner
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String item = parent.getItemAtPosition(position).toString();

        Toast.makeText(getContext(),"Item Selected:"+item,Toast.LENGTH_SHORT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
