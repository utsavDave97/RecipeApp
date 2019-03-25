package com.example.recipeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipeapp.DataHandler.SQLiteHelper;
import com.example.recipeapp.Model.MyRecipe;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class Step3 extends Fragment implements BlockingStep
{
    //Creating an Array of VerticalStepper
    //Here the number 3 represents the number of the steps present
    private VerticalStepperItemView items[] = new VerticalStepperItemView[3];

    String description;
    SQLiteHelper db;
    FragmentManager fm;

    //Declaring Buttons & Edittext
    private Button nextButtonStep1, nextButtonStep2,  previousButtonStep2, previousButtonStep3, finishButtonStep3;
    private EditText step1EditText, step2EditText, step3EditText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private MyRecipe mParam1;

    private OnFragmentInteractionListener mListener;

    public Step3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Step3.
     */
    // TODO: Rename and change types and number of parameters
    public static Step3 newInstance(Parcelable param1) {
        Step3 fragment = new Step3();
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
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step3, container, false);

        //initialize the SQLite
        db= new SQLiteHelper(getContext());

        //Initialize the fragment manager
        fm = getActivity().getSupportFragmentManager();

        //Get the instance of MyRecipe object
        mParam1 = MyRecipe.getInstance();

        //Find the widgets by its ID
        step1EditText = view.findViewById(R.id.step1EditText);
        step2EditText = view.findViewById(R.id.step2EditText);
        step3EditText = view.findViewById(R.id.step3EditText);

        //return the view
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
            public void onClick(View v)
            {
                //Check if the text is empty or not
                if(step1EditText.getText().toString().isEmpty())
                {
                    step1EditText.setError("Please enter Step");
                    step1EditText.requestFocus();
                    return;
                }
                else
                {
                    description = step1EditText.getText().toString()+"\n";
                    items[0].nextStep();
                }
            }
        });

        nextButtonStep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if text is empty or not
                if(step2EditText.getText().toString().isEmpty())
                {
                    step2EditText.setError("Please enter Step");
                    step2EditText.requestFocus();
                    return;
                }
                else
                {
                    description += step2EditText.getText().toString()+"\n";
                    items[1].nextStep();
                }
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

        finishButtonStep3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if step is empty or not
                if(step3EditText.getText().toString().isEmpty())
                {
                    step3EditText.setError("Please enter Step");
                    step3EditText.requestFocus();
                    return;
                }
                else
                {
                    description += step3EditText.getText().toString()+"\n";

                    //Get the description and set it to MyRecipe Object
                    mParam1.setDescription(description);

                    //Add the Recipe to SQLite Database
                    db.addMyRecipe(mParam1);

                    //Display a Toast Message showing that recipe has been added
                    Toast.makeText(getContext(),"Recipe Added",Toast.LENGTH_SHORT).show();

                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.content,new YourRecipe());
                    transaction.setCustomAnimations(R.anim.slide_left,R.anim.slide_right);
                    transaction.commit();

                    step3EditText.setText("");
                    step2EditText.setText("");
                    step1EditText.setText("");
                }
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

    /***
     * This method is implemented if we want to make sure the user fills out all the required information
     * before the user moves onto next step.
     *
     * Here inside this method it is verified that the user enters all three steps
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
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

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
