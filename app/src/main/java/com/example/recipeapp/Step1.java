package com.example.recipeapp;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.myhexaville.smartimagepicker.ImagePicker;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * @author utsav
 * @date 22-Feb-2019
 *
 * This is fragment class is the class which would show view and process information for Step 1 of
 * inside Create Your Own Recipe form.
 *
 * This fragment class also implements Step Library, this library is used to create step-by-step
 * form.
 *
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Step1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Step1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Step1 extends Fragment implements Step
{
    //Declaring variables
    ImageView yourRecipeImage;
    EditText yourRecipeName, yourRecipeCookTime;
    private ImagePicker imagePicker;
    Bitmap bitmap;
    String encoded_string;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Step1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Step1.
     */
    // TODO: Rename and change types and number of parameters
    public static Step1 newInstance(String param1, String param2) {
        Step1 fragment = new Step1();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step1, container, false);

        //This line would ensure that the editText on the screen won't open keyboard as soon as the
        //screen launches.
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Getting the EditTexts and ImageView by its ID
        yourRecipeName = view.findViewById(R.id.yourRecipeName);
        yourRecipeCookTime = view.findViewById(R.id.yourRecipeCookTime);
        yourRecipeImage = view.findViewById(R.id.yourRecipeImage);

        /**
         * Setting up a onClickListener on CookTime EditText
         * This method would open up a TimePickerDialog on clicking CookTime EditText
         */
        yourRecipeCookTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                /**
                 * To get calendar's getInstance method
                 * This method would give us the current time and date
                 */
                Calendar currentTime = Calendar.getInstance();

                /**
                 * Getting hour and min from instance of Calendar and assigning it to variables
                 */
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                final int min = currentTime.get(Calendar.MINUTE);

                /**
                 * Initializing the TimePickerDialog and implementing onTimeSet method
                 * Results would be showed in CookTime EditText after the Time has been selected
                 */
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                        yourRecipeCookTime.setText(hourOfDay + ":" + minute);
                    }
                },hour,min,false);

                timePickerDialog.show();
            }
        });

        /**
         * Setting up onClickListener on yourRecipe ImageView
         * This onClickListener uses ImagePicker Library to get pictures URI and requesting appropriate
         * permissions.
         *
         * This Library also handles the onActivityResult method and onRequestPermission method
         */
        yourRecipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This line ensures that Camera is included when the imagePicker opens up
                imagePicker.choosePicture(true);
            }
        });


        /**
         * This would call the ImagePicker constructor which takes in Activity, onImagePickedListner
         * This would return the URI of the Image returned and image can be set using setImageURI method
         * of yourRecipe ImageView.
         */
        imagePicker = new ImagePicker(getActivity(),null,
                imageUri->
                {
                    yourRecipeImage.setImageURI(imageUri);
//                    try
//                    {
//                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imageUri);
//                        encoded_string = imageToString(bitmap);
//                    }
//                    catch (IOException e)
//                    {
//                        e.printStackTrace();
//                    }
                });

        return view;
    }

    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,bytes);
        byte[] imageBytes = bytes.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode,grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode,requestCode,data);
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
     * Here inside this method first it is verified that the Recipe Name is entered before moving on to
     * next step.
     *
     * Also, inside this method it is verified that the user uploads a image of their recipe before moving
     * on to next step.
     *
     * If all the requirements are met it would allow the user to move onto next step.
     *
     * @return VerificationError
     */
    @Nullable
    @Override
    public VerificationError verifyStep()
    {
//        if(yourRecipeName.getText().toString().isEmpty())
//        {
//            return new VerificationError("Enter Recipe Name");
//        }
//        else if(yourRecipeImage.getDrawable().toString().equals("android.graphics.drawable.BitmapDrawable@d5f4bba"))
//        {
//            return new VerificationError("Upload Recipe Image");
//        }
//        else
//        {
//            return null;
//        }

        return  null;
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