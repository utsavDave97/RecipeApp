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
import android.widget.Button;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import moe.feng.common.stepperview.VerticalStepperItemView;


/**
 * @author utsav
 * @date 26-Feb-2019
 *
 * This is fragment class is the class which would show view and process information for Step 3 of
 * inside Create Your Own Recipe form.
 *
 * This fragment class also implements Step Library, this library is used to create step-by-step
 * form.
 *
 * This fragment class implements various methods from Vertical Stepper Library, this library is used
 * to create Vertical Step-by-Step implementation for the user to enter directions on how their recipe
 * is made.
 *
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Step3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Step3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Step3 extends Fragment implements Step
{
    //Creating an Array of VerticalStepper
    //Here the number 3 represents the number of the steps present
    private VerticalStepperItemView items[] = new VerticalStepperItemView[3];

    //Declaring Buttons
    private Button nextButtonStep1, nextButtonStep2,  previousButtonStep2, previousButtonStep3, finishButtonStep3;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Step3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Step3.
     */
    // TODO: Rename and change types and number of parameters
    public static Step3 newInstance(String param1, String param2) {
        Step3 fragment = new Step3();
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
        View view = inflater.inflate(R.layout.fragment_step3, container, false);

        return view;
    }


    /***
     * This method is override and called when the VerticalStepperItemView is initiated
     *
     * Here in this method, first it assigns values to items[].
     * Than, bindSteppers() method would bind this items to Layout.
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        items[0] = view.findViewById(R.id.stepper1);
        items[1] = view.findViewById(R.id.stepper2);
        items[2] = view.findViewById(R.id.stepper3);

        VerticalStepperItemView.bindSteppers(items);

        //Getting Buttons by its ID
        nextButtonStep1 = view.findViewById(R.id.nextButtonStep1);
        nextButtonStep2 = view.findViewById(R.id.nextButtonStep2);
        previousButtonStep2 = view.findViewById(R.id.previousButtonStep2);
        previousButtonStep3 = view.findViewById(R.id.previousButtonStep3);
        finishButtonStep3 = view.findViewById(R.id.finishButtonStep3);

        //Setting onClickListeners on above declared buttons
        nextButtonStep1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items[0].nextStep();
            }
        });

        nextButtonStep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items[1].nextStep();
            }
        });

        previousButtonStep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items[1].prevStep();
            }
        });

        previousButtonStep3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items[2].prevStep();
            }
        });


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
